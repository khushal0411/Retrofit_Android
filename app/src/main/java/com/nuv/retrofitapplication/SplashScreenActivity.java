package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {
@BindView(R.id.img_applogo) ImageView logo;
@BindView(R.id.tv_splashscreentitle) TextView title;
Animation titleAnimation,logoAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
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