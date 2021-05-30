package com.example.sanacosmetics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    ImageView Makeup,Skincare,Haircare,Suncare,Profile,Logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_dashboard);

        TextView t = (TextView) findViewById(R.id.dashlogotext);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Candyinc-9Gl2.otf");
        t.setTypeface(myCustomFont);//font style
        mAuth = FirebaseAuth.getInstance();

        Makeup=(ImageView)findViewById(R.id.makeup);
        Makeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, com.example.sanacosmetics.Makeup.class);
                startActivity(intent);
            }
        });

        Skincare=(ImageView)findViewById(R.id.skincare);
        Skincare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, com.example.sanacosmetics.Skincare.class);
                startActivity(intent);

            }
        });

        Haircare=(ImageView)findViewById(R.id.haircare);
        Haircare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, com.example.sanacosmetics.Haircare.class);
                startActivity(intent);
            }
        });

        Suncare=(ImageView)findViewById(R.id.suncare);
        Suncare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, com.example.sanacosmetics.Suncare.class);
                startActivity(intent);

            }
        });

        Profile=(ImageView)findViewById(R.id.profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, com.example.sanacosmetics.Profile.class);
                startActivity(intent);

            }
        });

        Logout=(ImageView)findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(Dashboard.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
