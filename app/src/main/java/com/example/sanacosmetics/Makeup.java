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

public class Makeup extends AppCompatActivity {
    ImageView esteelauder,loreal,maybelline,guerlain,lauramercier,nars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_makeup);

        TextView t = (TextView) findViewById(R.id.categorytext);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Sony_Sketch_EF.ttf");
        t.setTypeface(myCustomFont);//font style

        esteelauder=(ImageView)findViewById(R.id.makeupesteelauder);
        esteelauder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Makeup.this,Makeupesteelauder.class);
                startActivity(intent);
            }
        });

        loreal=(ImageView)findViewById(R.id.makeuploreal);
        loreal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Makeup.this,Makeuploreal.class);
                startActivity(intent);
            }
        });

        maybelline=(ImageView)findViewById(R.id.makeupmaybelline);
        maybelline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Makeup.this,Makeupmaybelline.class);
                startActivity(intent);
            }
        });

        guerlain=(ImageView)findViewById(R.id.makeupguerlain);
        guerlain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Makeup.this,Makeupguerlain.class);
                startActivity(intent);
            }
        });

        lauramercier=(ImageView)findViewById(R.id.makeuplauramercier);
        lauramercier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Makeup.this,Makeuplauramercier.class);
                startActivity(intent);
            }
        });

        nars=(ImageView)findViewById(R.id.makeupnars);
        nars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Makeup.this,Makeupnars.class);
                startActivity(intent);
            }
        });

    }
}
