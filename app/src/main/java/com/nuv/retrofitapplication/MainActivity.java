package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
     public static final String BASE_URL = "https://api.themoviedb.org/";
    Call<MoviesResponse> call,call2;
    CardView c1,c2,c3,c4,c5,t1,t2,t3,t4;

TextView title1,title2,title3,title4,title5,r1,r2,r3,r4,r5,seemore,seemore2,trtitle1,trtitle2,trtitle3,trtitle4,trr1,trr2,trr3,trr4;
ImageView image1,image2,image3,image4,image5,trimage1,trimage2,trimage3,trimage4,trimage5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title1 =findViewById(R.id.tv_movie_name);
        title2 =findViewById(R.id.tv_movie_name2);
        title3 =findViewById(R.id.tv_movie_name3);
        title4 =findViewById(R.id.tv_movie_name4);
        title5 =findViewById(R.id.tv_movie_name5);
        r1=findViewById(R.id.tv_movie_rating);
        r2=findViewById(R.id.tv_movie_rating2);
        r3=findViewById(R.id.tv_movie_rating3);
        r4=findViewById(R.id.tv_movie_rating4);
        r5=findViewById(R.id.tv_movie_rating5);
         seemore=findViewById(R.id.tv_seemore);
        image1=findViewById(R.id.img_1);
        image2=findViewById(R.id.img_2);
        image3=findViewById(R.id.img_3);
        image4=findViewById(R.id.img_4);
        image5=findViewById(R.id.img_5);
        c1=findViewById(R.id.card_1);
        c2=findViewById(R.id.card_2);
        c3=findViewById(R.id.card_3);
        c4=findViewById(R.id.card_4);
        c5=findViewById(R.id.card_5);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);



        trtitle1=findViewById(R.id.tv_movie_nametr1);
seemore2=findViewById(R.id.tv_seemore2);
        trtitle1 =findViewById(R.id.tv_movie_nametr1);
        trtitle2 =findViewById(R.id.tv_movie_nametr2);
        trtitle3 =findViewById(R.id.tv_movie_nametr3);
        trtitle4 =findViewById(R.id.tv_movie_nametr4);
        trr1=findViewById(R.id.tv_movie_ratingtr1);
        trr2=findViewById(R.id.tv_movie_ratingtr2);
        trr3=findViewById(R.id.tv_movie_ratingtr3);
        trr4=findViewById(R.id.tv_movie_ratingtr4);
        trimage1=findViewById(R.id.img_tr1);
        trimage2=findViewById(R.id.img_tr2);
        trimage3=findViewById(R.id.img_tr3);
        trimage4=findViewById(R.id.img_tr4);

        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,PopularMoviesActivity.class);
                intent.putExtra("type","popular");
                startActivity(intent);
            }
        });




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService= retrofit.create(ApiService.class);


        call = apiService.getposts();
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                List<Results> results=moviesResponse.getResults();
                Results firstmovie= results.get(0);
                Results secondmovie = results.get(1);
                Results thirdmovie = results.get(2);
                Results fourthmovie = results.get(3);
                Results fifthmovie = results.get(4);

                title1.setText(firstmovie.getOriginalTitle());
                title2.setText(secondmovie.getOriginalTitle());
                title3.setText(thirdmovie.getOriginalTitle());
                title4.setText(fourthmovie.getOriginalTitle());
                title5.setText(fifthmovie.getOriginalTitle());
                r1.setText(firstmovie.getVoteAverage().toString());
                r2.setText(secondmovie.getVoteAverage().toString());
                r3.setText(thirdmovie.getVoteAverage().toString());
                r4.setText(fourthmovie.getVoteAverage().toString());
                r5.setText(fifthmovie.getVoteAverage().toString());


                String url= "https://image.tmdb.org/t/p/w500/";
                Glide.with(getApplication()).load(url+firstmovie.getPosterPath().toString()).into(image1);
                Glide.with(getApplication()).load(url+secondmovie.getPosterPath().toString()).into(image2);
                Glide.with(getApplication()).load(url+thirdmovie.getPosterPath().toString()).into(image3);
                Glide.with(getApplication()).load(url+fourthmovie.getPosterPath().toString()).into(image4);
                Glide.with(getApplication()).load(url+fifthmovie.getPosterPath().toString()).into(image5);

                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",firstmovie.getBackdropPath());
                        intent.putExtra("posterimg",firstmovie.getPosterPath());
                        intent.putExtra("title",firstmovie.getTitle());
                        intent.putExtra("language",firstmovie.getOriginalLanguage());
                        intent.putExtra("popularity",firstmovie.getPopularity().toString());
                        intent.putExtra("ratings",firstmovie.getVoteAverage().toString());
                        intent.putExtra("overview",firstmovie.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });
                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",secondmovie.getBackdropPath());
                        intent.putExtra("posterimg",secondmovie.getPosterPath());
                        intent.putExtra("title",secondmovie.getTitle());
                        intent.putExtra("language",secondmovie.getOriginalLanguage());
                        intent.putExtra("popularity",secondmovie.getPopularity().toString());
                        intent.putExtra("ratings",secondmovie.getVoteAverage().toString());
                        intent.putExtra("overview",secondmovie.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });
                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",thirdmovie.getBackdropPath());
                        intent.putExtra("posterimg",thirdmovie.getPosterPath());
                        intent.putExtra("title",thirdmovie.getTitle());
                        intent.putExtra("language",thirdmovie.getOriginalLanguage());
                        intent.putExtra("popularity",thirdmovie.getPopularity().toString());
                        intent.putExtra("ratings",thirdmovie.getVoteAverage().toString());
                        intent.putExtra("overview",thirdmovie.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });
                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",fourthmovie.getBackdropPath());
                        intent.putExtra("posterimg",fourthmovie.getPosterPath());
                        intent.putExtra("title",fourthmovie.getTitle());
                        intent.putExtra("language",fourthmovie.getOriginalLanguage());
                        intent.putExtra("popularity",fourthmovie.getPopularity().toString());
                        intent.putExtra("ratings",fourthmovie.getVoteAverage().toString());
                        intent.putExtra("overview",fourthmovie.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });
                c5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",fifthmovie.getBackdropPath());
                        intent.putExtra("posterimg",fifthmovie.getPosterPath());
                        intent.putExtra("title",fifthmovie.getTitle());
                        intent.putExtra("language",fifthmovie.getOriginalLanguage());
                        intent.putExtra("popularity",fifthmovie.getPopularity().toString());
                        intent.putExtra("ratings",fifthmovie.getVoteAverage().toString());
                        intent.putExtra("overview",fifthmovie.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });


            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
        call2=apiService.getresponse();
        call2.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse mr = response.body();
                List<Results> r = mr.getResults();
                Results fm= r.get(0);
                Results sm = r.get(1);
                Results tm = r.get(2);
                Results fourth = r.get(3);


                trtitle1.setText(fm.getTitle());
                trtitle2.setText(sm.getTitle());
                trtitle3.setText(tm.getTitle());
                trtitle4.setText(fourth.getTitle());

                trr1.setText(fm.getVoteAverage().toString());
                trr2.setText(sm.getVoteAverage().toString());
                trr3.setText(tm.getVoteAverage().toString());
                trr4.setText(fourth.getVoteAverage().toString());



                String url= "https://image.tmdb.org/t/p/w500/";
                Glide.with(getApplication()).load(url+fm.getPosterPath().toString()).into(trimage1);
                Glide.with(getApplication()).load(url+sm.getPosterPath().toString()).into(trimage2);
                Glide.with(getApplication()).load(url+tm.getPosterPath().toString()).into(trimage3);
                Glide.with(getApplication()).load(url+fourth.getPosterPath().toString()).into(trimage4);

                t1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",fm.getBackdropPath());
                        intent.putExtra("posterimg",fm.getPosterPath());
                        intent.putExtra("title",fm.getTitle());
                        intent.putExtra("language",fm.getOriginalLanguage());
                        intent.putExtra("popularity",fm.getPopularity().toString());
                        intent.putExtra("ratings",fm.getVoteAverage().toString());
                        intent.putExtra("overview",fm.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });

                t2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",sm.getBackdropPath());
                        intent.putExtra("posterimg",sm.getPosterPath());
                        intent.putExtra("title",sm.getTitle());
                        intent.putExtra("language",sm.getOriginalLanguage());
                        intent.putExtra("popularity",sm.getPopularity().toString());
                        intent.putExtra("ratings",sm.getVoteAverage().toString());
                        intent.putExtra("overview",sm.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });
                t3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",tm.getBackdropPath());
                        intent.putExtra("posterimg",tm.getPosterPath());
                        intent.putExtra("title",tm.getTitle());
                        intent.putExtra("language",tm.getOriginalLanguage());
                        intent.putExtra("popularity",tm.getPopularity().toString());
                        intent.putExtra("ratings",tm.getVoteAverage().toString());
                        intent.putExtra("overview",tm.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });
                t4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                        intent.putExtra("backgroundimage",fourth.getBackdropPath());
                        intent.putExtra("posterimg",fourth.getPosterPath());
                        intent.putExtra("title",fourth.getTitle());
                        intent.putExtra("language",fourth.getOriginalLanguage());
                        intent.putExtra("popularity",fourth.getPopularity().toString());
                        intent.putExtra("ratings",fourth.getVoteAverage().toString());
                        intent.putExtra("overview",fourth.getOverview());
                        v.getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });


seemore2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent =new Intent(MainActivity.this,PopularMoviesActivity.class);
        intent.putExtra("type","toprated");
        startActivity(intent);
    }
});


    }


}