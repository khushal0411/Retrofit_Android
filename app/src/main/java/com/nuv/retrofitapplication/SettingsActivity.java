package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;

import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.ActivitySettingsBinding;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

String lang;
ActivitySettingsBinding binding;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
        themeCheck();
        //setContentView(R.layout.activity_settings);
         binding= DataBindingUtil.setContentView(this,R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(binding.toolbar.tbMain);
        binding.toolbar.tbMain.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        binding.toolbar.tbMain.setTitle(R.string.SettingsPage);


        binding.btnChristmasMode.setOnClickListener(v -> {
            setTheme(R.style.ChristmasTheme);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString(Constants.THEME,Constants.CHRISTMAS);
            editor.apply();
            resetApplication();
        });
         binding.btnDarkMode.setOnClickListener(v -> {
             SharedPreferences.Editor editor =sharedPreferences.edit();
             editor.putString(Constants.THEME,Constants.DARK_MODE);
             editor.apply();
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

         });

         binding.btnLightMode.setOnClickListener(v -> {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
             SharedPreferences.Editor editor =sharedPreferences.edit();
             editor.putString(Constants.THEME,Constants.LIGHT_MODE);
             editor.apply();



         });


         binding.btnLanEng.setOnClickListener(v -> {
             lang="en";
             changeLanguage(lang);
         });
         binding.btnLanHindi.setOnClickListener(v -> {
             lang="hi";
             changeLanguage(lang);

         });

         binding.btnLanGujarti.setOnClickListener(v -> {
             lang="gu";
             changeLanguage(lang);
         });

        binding.toolbar.tbMain.setNavigationOnClickListener(v -> finish());
    }

    private void changeLanguage(String lang) {
        Locale locale=new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
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