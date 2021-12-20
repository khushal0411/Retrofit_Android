package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.Locale;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
Toolbar toolbar;
Button langhi,langen,langguj,darktheme,lighttheme;
String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar=findViewById(R.id.tb_main);
         langhi=findViewById(R.id.btn_lanhindi);
         langen=findViewById(R.id.btn_laneng);
         langguj=findViewById(R.id.btn_langujarti);
         darktheme=findViewById(R.id.btn_darkmode);
         lighttheme=findViewById(R.id.btn_lightmode);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.SettingsPage);


         darktheme.setOnClickListener(v -> {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

         });

         lighttheme.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

         });


         langen.setOnClickListener(v -> {
             lang="en";
             changeLanguage(lang);
         });
         langhi.setOnClickListener(v -> {
             lang="hi";
             changeLanguage(lang);

         });

         langguj.setOnClickListener(v -> {
             lang="gu";
             changeLanguage(lang);
         });

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void changeLanguage(String lang) {
        Locale mlocale=new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = mlocale;
        res.updateConfiguration(conf, dm);
        SharedPreferences sharedPreferences = getSharedPreferences("Movies", MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("langpref",lang);
        editor.apply();
        Intent refresh = new Intent(SettingsActivity.this,MainActivity.class);
        startActivity(refresh);

    }
}