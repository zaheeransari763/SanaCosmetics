package com.example.sanacosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText userName, userEmail, userGender, userPwd, userAddress,userContact;
    private Button userReg;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    TextView logintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_register);

        //TextView t = (TextView) findViewById(R.id.logotext);
        //Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Candyinc-9Gl2.otf");
        //t.setTypeface(myCustomFont);//font style

        logintext=(TextView)findViewById(R.id.signintext);
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        ProgressDialog loadingbar = new ProgressDialog(this);

        userName = findViewById(R.id.username);
        userEmail = findViewById(R.id.useremail);
        userAddress = findViewById(R.id.useraddress);
        userGender = findViewById(R.id.usergender);
        userPwd = findViewById(R.id.userpassword);
        userContact = findViewById(R.id.userphone);

        userReg = findViewById(R.id.registerbutton);
        mAuth = FirebaseAuth.getInstance();
        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });



    }

    private void CreateNewAccount() {

        final String name = userName.getText().toString();
        final String email = userEmail.getText().toString();
        final String address = userAddress.getText().toString();
        final String password = userPwd.getText().toString();
        final String gender = userGender.getText().toString();
        final String phone=userContact.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name is Mandatory...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "E-mail is empty...", Toast.LENGTH_SHORT).show();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(Register.this, "Incorrect E-mail", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Contact is empty...", Toast.LENGTH_SHORT).show();
        } else if (phone.length() != 10) {
            Toast.makeText(this, "Invalid Contact...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Address cannot be empty...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Gender required...", Toast.LENGTH_SHORT).show();
        }  else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Create Password...", Toast.LENGTH_SHORT).show();
        } else {

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String ClientID = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference("Clients").child(ClientID);

                        HashMap<String, String> ClientMap = new HashMap();
                        ClientMap.put("Fullname", name);
                        ClientMap.put("EMail",email);
                        ClientMap.put("Contact",phone);
                        ClientMap.put("Address", address);
                        ClientMap.put("Gender",gender);
                        ClientMap.put("Password",password);
                        ClientMap.put("image","default");


                        reference.setValue(ClientMap).addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Intent DashMainIntent = new Intent(Register.this,Dashboard.class);
                                    DashMainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(DashMainIntent);
                                    finish();
                                }
                            }
                        });

                        Toast.makeText(Register.this, "Authenticated Successfully...", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(Register.this, "Error Occurred ;" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
