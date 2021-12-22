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
Animation titleAnimation, logoAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo=findViewById(R.id.img_applogo);
        title=findViewById(R.id.tv_splashscreentitle);
        titleAnimation = AnimationUtils.loadAnimation(this,R.anim.translate);
        title.setAnimation(titleAnimation);
        logoAnimation =AnimationUtils.loadAnimation(this,R.anim.logorotate);
        logo.setAnimation(logoAnimation);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        },3000);


    }
}