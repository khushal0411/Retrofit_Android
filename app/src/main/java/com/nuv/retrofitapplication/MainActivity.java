package com.nuv.retrofitapplication;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

public class MainActivity extends BaseActivity {

    Call<MoviesResponse> call,call2;
    RecyclerView rvPopularMovies,rvTopRated;
    private ArrayList<MovieDetails> popularMovies=new ArrayList<>();
    private ArrayList<MovieDetails> topRated=new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    Toolbar toolbar;
    TextView seeMore,seeMore2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MOVIES, MODE_PRIVATE);
        String Theme = sharedPreferences.getString(Constants.THEME, null);
        checkLang();
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
        }
        }
        setContentView(R.layout.activity_main);

        seeMore =findViewById(R.id.tv_seemore);
        seeMore2=findViewById(R.id.tv_seemore2);


        rvPopularMovies=findViewById(R.id.rec_popularmovies);
        rvTopRated=findViewById(R.id.rec_toprated);
        toolbar=findViewById(R.id.tb_main);



        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.movie_recomendation_app);

         toolbar.setNavigationOnClickListener(v -> {
             Intent intent = new Intent(MainActivity.this, DrawerLayoutActivity.class);
             startActivity(intent);
         });

          if(isConnected()) {
              seeMore.setOnClickListener(v -> {
                  Intent intent = new Intent(MainActivity.this, MoviesGridViewActivity.class);
                  intent.putExtra(Constants.TYPE, Constants.POPULAR);
                  startActivity(intent);
              });
               ApiService apiService =apicall();

              call = apiService.getposts();
              call.enqueue(new Callback<MoviesResponse>() {
                  @Override
                  public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                      MoviesResponse moviesResponse = response.body();
                      assert moviesResponse != null;
                      popularMovies = (ArrayList<MovieDetails>) moviesResponse.getResults();
                      SharedPreferences sharedPreferences = getSharedPreferences(Constants.MOVIES, MODE_PRIVATE);
                      Gson gson = new Gson();
                      String popular = gson.toJson(popularMovies);
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.putString(Constants.POPULAR_MOVIES, popular);
                      editor.apply();
                      List<MovieDetails> list= popularMovies.subList(0,6);
                      ArrayList<MovieDetails> arrayList = new ArrayList<>(list);
                      recyclerViewAdapter = new RecyclerViewAdapter(arrayList);
                      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false);
                      rvPopularMovies.setLayoutManager(mLayoutManager);
                      rvPopularMovies.setAdapter(recyclerViewAdapter);

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
                      topRated = (ArrayList<MovieDetails>) mr.getResults();
                      SharedPreferences sharedPreferences = getSharedPreferences("Movies", MODE_PRIVATE);
                      Gson gson = new Gson();
                      String popular = gson.toJson(topRated);
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.putString(Constants.TOP_RATED, popular);
                      editor.apply();
                      List<MovieDetails> list= topRated.subList(0,6);
                      ArrayList<MovieDetails> arrayList = new ArrayList<>(list);
                      recyclerViewAdapter = new RecyclerViewAdapter(arrayList);
                      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false);
                      rvTopRated.setLayoutManager(mLayoutManager);
                      rvTopRated.setAdapter(recyclerViewAdapter);

                      recyclerViewAdapter.notifyDataSetChanged();

                  }

                  @Override
                  public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

                  }
              });


              seeMore2.setOnClickListener(v -> {
                  Intent intent = new Intent(MainActivity.this, MoviesGridViewActivity.class);
                  intent.putExtra(Constants.TYPE, Constants.TOP_RATED);
                  startActivity(intent);
              });
          }

          else
          {
              Toast.makeText(getApplicationContext(), Constants.NO_INTERNET, Toast.LENGTH_LONG).show();
          }


    }

    private void checkLang() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MOVIES, MODE_PRIVATE);
        String lan=sharedPreferences.getString(Constants.LANG_PREF,null);
        if(lan !=null) {
            Locale locate = new Locale(lan);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();

            Configuration conf = res.getConfiguration();
            conf.locale = locate;
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