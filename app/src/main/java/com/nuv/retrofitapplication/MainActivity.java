package com.nuv.retrofitapplication;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

public class MainActivity extends BaseActivity {
    public static final String BASE_URL = "https://api.themoviedb.org/";
    Call<MoviesResponse> call,call2;
    RecyclerView popularmovies_recyclerView;
    RecyclerView toprated_rec;
    private ArrayList<MovieDetails> popularmovies=new ArrayList<>();
    private ArrayList<MovieDetails> toprated=new ArrayList<>();
    HomeRecyclerViewAdapter recyclerViewAdapter;
    Toolbar toolbar;
    TextView seemore,seemore2,mainheading,popularheading,topratedheading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checklang();
        seemore=findViewById(R.id.tv_seemore);
        mainheading=findViewById(R.id.tv_headingmain);
        popularheading=findViewById(R.id.tv_popularmheading);
        topratedheading=findViewById(R.id.tv_topratedmheading);
        seemore2=findViewById(R.id.tv_seemore2);



        seemore.setText(R.string.see_more);
        seemore2.setText(R.string.see_more);
        mainheading.setText(R.string.movie_recomendation_app);
        popularheading.setText(R.string.popular_movies);
        topratedheading.setText(R.string.top_rated_movies);
        popularmovies_recyclerView=findViewById(R.id.rec_popularmovies);
        toprated_rec=findViewById(R.id.rec_toprated);
        toolbar=findViewById(R.id.tb_main);



        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.HomePage);

         toolbar.setNavigationOnClickListener(v -> finish());
          if(isConnected()) {
              seemore.setOnClickListener(v -> {
                  Intent intent = new Intent(MainActivity.this, MoviesGridViewActivity.class);
                  intent.putExtra("type", "popular");
                  startActivity(intent);
              });


              Retrofit retrofit = new Retrofit.Builder()
                      .baseUrl(BASE_URL)
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
              ApiService apiService = retrofit.create(ApiService.class);


              call = apiService.getposts();
              call.enqueue(new Callback<MoviesResponse>() {
                  @Override
                  public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                      MoviesResponse moviesResponse = response.body();
                      assert moviesResponse != null;
                      popularmovies = (ArrayList<MovieDetails>) moviesResponse.getResults();
                      SharedPreferences sharedPreferences = getSharedPreferences("Movies", MODE_PRIVATE);
                      Gson gson = new Gson();
                      String popular = gson.toJson(popularmovies);
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.putString("popularmovies", popular);
                      editor.apply();
                      recyclerViewAdapter = new HomeRecyclerViewAdapter(popularmovies);
                      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false);
                      popularmovies_recyclerView.setLayoutManager(mLayoutManager);
                      popularmovies_recyclerView.setAdapter(recyclerViewAdapter);

                      recyclerViewAdapter.notifyDataSetChanged();


                  }

                  @Override
                  public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

                  }
              });
              call2 = apiService.getresponse();
              call2.enqueue(new Callback<MoviesResponse>() {
                  @Override
                  public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                      MoviesResponse mr = response.body();
                      assert mr != null;
                      toprated = (ArrayList<MovieDetails>) mr.getResults();
                      SharedPreferences sharedPreferences = getSharedPreferences("Movies", MODE_PRIVATE);
                      Gson gson = new Gson();
                      String popular = gson.toJson(toprated);
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.putString("toprated", popular);
                      editor.apply();
                      recyclerViewAdapter = new HomeRecyclerViewAdapter(toprated);
                      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false);
                      toprated_rec.setLayoutManager(mLayoutManager);
                      toprated_rec.setAdapter(recyclerViewAdapter);

                      recyclerViewAdapter.notifyDataSetChanged();

                  }

                  @Override
                  public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

                  }
              });


              seemore2.setOnClickListener(v -> {
                  Intent intent = new Intent(MainActivity.this, MoviesGridViewActivity.class);
                  intent.putExtra("type", "toprated");
                  startActivity(intent);
              });
          }

          else
          {
              Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
          }


    }

    private void checklang() {
        SharedPreferences sharedPreferences = getSharedPreferences("Movies", MODE_PRIVATE);
        String lan=sharedPreferences.getString("langpref",null);
        if(lan !=null) {
            Locale mlocale = new Locale(lan);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = mlocale;
            res.updateConfiguration(conf, dm);
        }
    }


    private boolean isConnected() {
        boolean connected= false;

        ConnectivityManager cm= (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        connected = info != null && info.isAvailable() && info.isConnected();
        return connected;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_mainpage_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        MenuItem settings=menu.findItem(R.id.settings);

        settings.setOnMenuItemClickListener(item -> {
            Intent intent =new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        });
        searchViewItem.setOnMenuItemClickListener(item -> {
            Intent intent =new Intent(MainActivity.this, SearchMovieActivity.class);
            startActivity(intent);
            return true;
        });
        return true;
    }
}