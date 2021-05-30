package com.example.sanacosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {
    private Button LoginButton;
    private EditText UserEmail,UserPassword;
    private FirebaseAuth mAuth;

    TextView registertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_login);

        TextView t = (TextView) findViewById(R.id.logotext);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Candyinc-9Gl2.otf");
        t.setTypeface(myCustomFont);//font style

        mAuth = FirebaseAuth.getInstance();

        UserPassword=(EditText)findViewById(R.id.userpassword);
        UserEmail=(EditText)findViewById(R.id.useremail);
        LoginButton=(Button)findViewById(R.id.loginbutton);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();

            }
        });

        registertext=(TextView) findViewById(R.id.registertext);
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            SendUserToDashboard();
        }
    }

    private void SendUserToDashboard() {

        Intent mainIntent = new Intent(LoginActivity.this,Dashboard.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }


    private void Login() {

        final String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "E-mail is Mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(LoginActivity.this, "Incorrect E-mail", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Pass is Mandatory...", Toast.LENGTH_SHORT).show();
        }
        else

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        SendUserToDashboard();
                        Toast.makeText(LoginActivity.this, "Logged In Successfully...", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(LoginActivity.this, "Error Occurred:" + message, Toast.LENGTH_SHORT).show();

                    }
                }
            });


    }
}
