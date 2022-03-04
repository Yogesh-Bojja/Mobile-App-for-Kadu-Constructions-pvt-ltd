package com.example.admin.mainapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ImageView imgSPL = (ImageView) findViewById(R.id.imgSPL);
        TextView titleApp1 = (TextView)findViewById(R.id.txtSPL1);
        TextView titleApp2 = (TextView)findViewById(R.id.txtSPL2);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        imgSPL.startAnimation(animation);
        titleApp1.startAnimation(animation);
        titleApp2.startAnimation(animation);
        Thread time = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), LoginPag.class);
                    startActivity(intent);
                    finish();
                    super.run();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        time.start();


    }
}
