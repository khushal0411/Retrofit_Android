package com.nuv.retrofitapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.network.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public ApiService apiCall(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }
  public void themeCheck(){
      SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
      String theme = sharedPreferences.getString(Constants.THEME, null);
      if(theme !=null){
          if(theme.equals(Constants.CHRISTMAS)){
              setTheme(R.style.ChristmasTheme);
          }else {
              if(theme.equals(Constants.DARK_MODE))
              {
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
              }
              else{
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
              }
          }}
  }



}
