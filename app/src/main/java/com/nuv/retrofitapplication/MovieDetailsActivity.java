package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView background, poster;
    TextView name,language,ratings,overview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        background=findViewById(R.id.img_backgroungimage);
        poster=findViewById(R.id.img_movieposter);
        name=findViewById(R.id.tv_movienamepage);
        language=findViewById(R.id.tv_language);
        ratings=findViewById(R.id.tv_ratings);
        overview=findViewById(R.id.tv_movieoverview);
        Intent intent =getIntent();
        String backgroundpath=intent.getStringExtra("backgroundimage");
        String posterpath=intent.getStringExtra("posterimg");
        String titlename=intent.getStringExtra("title");
        String lan= intent.getStringExtra("language");
        String rat= intent.getStringExtra("ratings");
        String overv= intent.getStringExtra("overview");

        String url= "https://image.tmdb.org/t/p/w500/";
        Glide.with(getApplicationContext()).load(url+backgroundpath).into(background);
        Glide.with(getApplicationContext()).load(url+posterpath).into(poster);
        name.setText(titlename);
        language.setText("Language: "+lan);
        ratings.setText("Ratings :"+rat);
        overview.setText(overv);

    }
}