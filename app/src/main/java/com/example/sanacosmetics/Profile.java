package com.example.sanacosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    TextView cname,cgender,cphone,cemail,caddress;

    CircleImageView profilePic;
    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    String currentUserId;
    private ProgressDialog loadingBar;
    private static final int GALLERY_PICK = 1;

    // Storage Firebase
    private StorageReference mImageStorage;
    private Uri imageUri;
    private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_profile);


        mAuth = FirebaseAuth.getInstance();
        profilePic=(CircleImageView) findViewById(R.id.userprofileimage);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

            }
        });

        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUserId);


        cname=(TextView)findViewById(R.id.clientname);
        cgender=(TextView)findViewById(R.id.clientgender);
        cphone=(TextView)findViewById(R.id.clientphone);
        cemail=(TextView)findViewById(R.id.clientemail);
        caddress=(TextView)findViewById(R.id.clientaddress);



        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    String userfullname = dataSnapshot.child("Fullname").getValue().toString();
                    String useremail = dataSnapshot.child("EMail").getValue().toString();
                    String usergender = dataSnapshot.child("Gender").getValue().toString();
                    String usercontact = dataSnapshot.child("Contact").getValue().toString();
                    String useraddress = dataSnapshot.child("Address").getValue().toString();

                    cname.setText(userfullname);
                    cemail.setText(useremail);
                    caddress.setText(useraddress);
                    cgender.setText(usergender);
                    cphone.setText(usercontact);

                    final String image = dataSnapshot.child("image").getValue().toString();
                    if(!image.equals("default"))
                    {
                        Picasso.with(Profile.this).load(image).placeholder(R.drawable.addprofileimage).into(profilePic);
                        Picasso.with(Profile.this).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.addprofileimage).into(profilePic, new Callback()
                        {
                            @Override
                            public void onSuccess()
                            {

                            }

                            @Override
                            public void onError()
                            {
                                Picasso.with(Profile.this).load(image).placeholder(R.drawable.addprofileimage).into(profilePic);
                            }
                        });
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .setMinCropWindowSize(500, 500)
                    .start(this);
            //Toast.makeText(SettingsActivity.this, imageUri, Toast.LENGTH_LONG).show();
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK && uploadTask!= null && uploadTask.isInProgress() && data != null && data.getData() != null)
            {
                Toast.makeText(this, "Upload In Progress", Toast.LENGTH_SHORT).show();
            }
            else
            {
                UploadImage();
            }
        }
    }

    private void UploadImage() {
        loadingBar = new ProgressDialog(Profile.this);
        loadingBar.setTitle("Uploading Image...");
        loadingBar.setMessage("Please wait while we upload and process the image.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        if(imageUri != null)
        {
            String current_user_id = mAuth.getUid();
            final StorageReference fileReference = mImageStorage.child("profile_images").child(current_user_id + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        String current_uid = mAuth.getUid();
                        profileUserRef = FirebaseDatabase.getInstance().getReference("Clients").child(current_uid);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("image", mUri);
                        profileUserRef.updateChildren(map);
                        loadingBar.dismiss();
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(Profile.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getMimeTypeFromExtension(contentResolver.getType(uri));
    }


}
