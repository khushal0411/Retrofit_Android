package com.nuv.retrofitapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.ActivityMovieDetailsBinding;
import com.nuv.retrofitapplication.model.MovieDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeCheck();
        ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Intent intent =getIntent();
        MovieDetails movieDetails= (MovieDetails) intent.getSerializableExtra(Constants.MOVIE_DETAILS);
        binding.setMovieDetails(movieDetails);
        String url= Constants.URL_IMAGE;
        Glide.with(getApplicationContext()).load(url+movieDetails.getBackdropPath()).into(binding.imgBackgroungImage);
        Glide.with(getApplicationContext()).load(url+movieDetails.getPosterPath()).into(binding.imgMoviePoster);


    }
}