package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nuv.retrofitapplication.databinding.ActivitySplashScreenBinding;

public class MoviesSplashScreenActivity extends AppCompatActivity {

Animation titleAnimation,logoAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen);
        titleAnimation = AnimationUtils.loadAnimation(this,R.anim.translate);
        binding.tvSplashScreenTitle.setAnimation(titleAnimation);
        logoAnimation =AnimationUtils.loadAnimation(this,R.anim.logorotate);
        binding.imgAppLogo.setAnimation(logoAnimation);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MoviesSplashScreenActivity.this, MoviesHomeScreenActivity.class);
            startActivity(intent);
            finish();
        },3000);


    }
}