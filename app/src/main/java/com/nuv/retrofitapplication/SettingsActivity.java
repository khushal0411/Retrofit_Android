package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
@BindView(R.id.tb_main) Toolbar toolbar;
@BindView(R.id.btn_lanhindi) Button langHi;
@BindView(R.id.btn_LanEng)Button LangEn;
@BindView(R.id.btn_langujarti)Button langGuj;
@BindView(R.id.btn_darkmode)Button darkTheme;
@BindView(R.id.btn_lightmode)Button lightTheme;
@BindView(R.id.btn_christmasmode)Button christmasTheme;
String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MOVIES, MODE_PRIVATE);
        String Theme = sharedPreferences.getString(Constants.THEME, null);
        if(Theme !=null){
            if(Theme.equals(Constants.CHRISTMAS)){
                setTheme(R.style.ChristmasTheme);
            }
            else {
                if(Theme.equals(Constants.DARK_MODE))
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }}
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.SettingsPage);


        christmasTheme.setOnClickListener(v -> {
            setTheme(R.style.ChristmasTheme);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString(Constants.THEME,Constants.CHRISTMAS);
            editor.apply();
            resetApplication();
        });
         darkTheme.setOnClickListener(v -> {
             SharedPreferences.Editor editor =sharedPreferences.edit();
             editor.putString(Constants.THEME,Constants.DARK_MODE);
             editor.apply();
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

         });

         lightTheme.setOnClickListener(v -> {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
             SharedPreferences.Editor editor =sharedPreferences.edit();
             editor.putString(Constants.THEME,Constants.LIGHT_MODE);
             editor.apply();



         });


         LangEn.setOnClickListener(v -> {
             lang="en";
             changeLanguage(lang);
         });
         langHi.setOnClickListener(v -> {
             lang="hi";
             changeLanguage(lang);

         });

         langGuj.setOnClickListener(v -> {
             lang="gu";
             changeLanguage(lang);
         });

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void changeLanguage(String lang) {
        Locale locale=new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MOVIES, MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(Constants.LANG_PREF,lang);
        editor.apply();
        resetApplication();

    }
    public void resetApplication() {
        Intent resetApplicationIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        if (resetApplicationIntent != null) {
            resetApplicationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(resetApplicationIntent);

    }
}