package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
ImageView logo;
TextView title;
Animation t,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo=findViewById(R.id.img_applogo);
        title=findViewById(R.id.tv_splashscreentitle);
        t= AnimationUtils.loadAnimation(this,R.anim.translate);
        title.setAnimation(t);
        z=AnimationUtils.loadAnimation(this,R.anim.logorotate);
        logo.setAnimation(z);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);


    }
}