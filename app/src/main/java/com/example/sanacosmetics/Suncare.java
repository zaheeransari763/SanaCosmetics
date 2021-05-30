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

public class Suncare extends AppCompatActivity {
    ImageView esteelauder, loreal, maybelline, guerlain, lauramercier, nars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_suncare);

        TextView t = (TextView) findViewById(R.id.categorytext);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Sony_Sketch_EF.ttf");
        t.setTypeface(myCustomFont);//font style

        esteelauder = (ImageView) findViewById(R.id.esteesun);
        esteelauder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suncare.this, Suncareestee.class);
                startActivity(intent);
            }
        });

        loreal = (ImageView) findViewById(R.id.lorealsun);
        loreal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suncare.this, Suncareloreal.class);
                startActivity(intent);
            }
        });

        maybelline = (ImageView) findViewById(R.id.maybellinesun);
        maybelline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suncare.this, Suncaremaybelline.class);
                startActivity(intent);
            }
        });

        guerlain = (ImageView) findViewById(R.id.guerlainsun);
        guerlain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suncare.this, Suncareguerlain.class);
                startActivity(intent);
            }
        });

        lauramercier = (ImageView) findViewById(R.id.laurasun);
        lauramercier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suncare.this, Suncarelauramercier.class);
                startActivity(intent);
            }
        });

        nars = (ImageView) findViewById(R.id.narssun);
        nars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suncare.this, Suncarenars.class);
                startActivity(intent);
            }
        });
    }
}
