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

public class Haircare extends AppCompatActivity {

    ImageView esteelauder,loreal,maybelline,guerlain,lauramercier,nars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_haircare);

        TextView t = (TextView) findViewById(R.id.categorytext);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Sony_Sketch_EF.ttf");
        t.setTypeface(myCustomFont);//font style

        esteelauder=(ImageView)findViewById(R.id.haircareestee);
        esteelauder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Haircare.this,Haircareesteelauder.class);
                startActivity(intent);
            }
        });

        loreal=(ImageView)findViewById(R.id.haircareloreal);
        loreal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Haircare.this,Haircareloreal.class);
                startActivity(intent);
            }
        });

        maybelline=(ImageView)findViewById(R.id.haircaremaybelline);
        maybelline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Haircare.this,Haircaremaybelline.class);
                startActivity(intent);
            }
        });

        guerlain=(ImageView)findViewById(R.id.haircareguerlain);
        guerlain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Haircare.this,Haircareguerlain.class);
                startActivity(intent);
            }
        });

        lauramercier=(ImageView)findViewById(R.id.haircarelauramercier);
        lauramercier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Haircare.this,Haircarelauramercier.class);
                startActivity(intent);
            }
        });

        nars=(ImageView)findViewById(R.id.haircarenars);
        nars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Haircare.this,Haircarenars.class);
                startActivity(intent);
            }
        });

    }
}

