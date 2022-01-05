package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.img_backgroungimage) ImageView background;
    @BindView(R.id.img_movieposter) ImageView poster;
    @BindView(R.id.tv_movienamepage) TextView name;
    @BindView(R.id.tv_language) TextView language;
    @BindView(R.id.tv_ratings) TextView ratings;
    @BindView(R.id.tv_movieoverview) TextView overview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MOVIES, MODE_PRIVATE);
        String Theme = sharedPreferences.getString(Constants.THEME, null);
        if(Theme !=null){
            if(Theme.equals(Constants.CHRISTMAS)){
                setTheme(R.style.ChristmasTheme);
            }else {
                if(Theme.equals(Constants.DARK_MODE))
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }}
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Intent intent =getIntent();

        MovieDetails movieDetails= (MovieDetails) intent.getSerializableExtra(Constants.DETAILS);

        String url= Constants.URL_IMAGE;
        Glide.with(getApplicationContext()).load(url+movieDetails.getBackdropPath()).into(background);
        Glide.with(getApplicationContext()).load(url+movieDetails.getPosterPath()).into(poster);
        name.setText(movieDetails.getOriginalTitle());
        language.setText(Constants.LANGUAGE+movieDetails.getOriginalLanguage());
        ratings.setText(Constants.RATINGS+movieDetails.getVoteAverage());
        overview.setText(movieDetails.getOverview());

    }
}