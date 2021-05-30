package com.example.sanacosmetics;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    int SPLASH_TIME = 3000; //This is 3 seconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_main);


        TextView t = (TextView) findViewById(R.id.appname);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Candyinc-9Gl2.otf");
        t.setTypeface(myCustomFont);//font style

        TextView x = (TextView) findViewById(R.id.appquote);
        Typeface myCustomFon = Typeface.createFromAsset(getAssets(), "fonts/DiamondDust-m3a9.ttf");
        x.setTypeface(myCustomFon);//font style


        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mySuperIntent);

                //This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
                finish();

            }
        }, SPLASH_TIME);
    }

    }

