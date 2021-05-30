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

public class Skincare extends AppCompatActivity {
    ImageView esteelauder,loreal,maybelline,guerlain,lauramercier,nars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_skincare);

        TextView t = (TextView) findViewById(R.id.categorytext);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Sony_Sketch_EF.ttf");
        t.setTypeface(myCustomFont);//font style


        esteelauder=(ImageView)findViewById(R.id.esteeskin);
        esteelauder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Skincare.this,Skincareesteelauder.class);
                startActivity(intent);
            }
        });

        loreal=(ImageView)findViewById(R.id.lorealskin);
        loreal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Skincare.this,Skincareloreal.class);
                startActivity(intent);
            }
        });

        maybelline=(ImageView)findViewById(R.id.maybellineskin);
        maybelline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Skincare.this,Skincaremaybelline.class);
                startActivity(intent);
            }
        });

        guerlain=(ImageView)findViewById(R.id.guerlainskin);
        guerlain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Skincare.this,Skincareguerlain.class);
                startActivity(intent);
            }
        });

        lauramercier=(ImageView)findViewById(R.id.lauraskin);
        lauramercier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Skincare.this,Skincarelauramercier.class);
                startActivity(intent);
            }
        });

        nars=(ImageView)findViewById(R.id.narsskin);
        nars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Skincare.this,Skincarenars.class);
                startActivity(intent);
            }
        });

    }
}

