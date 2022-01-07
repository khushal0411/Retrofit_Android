package com.nuv.retrofitapplication;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.nuv.retrofitapplication.adapters.MoviesRecyclerViewAdapter;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.ActivityMoviesHomeBinding;
import com.nuv.retrofitapplication.model.MovieDetails;
import com.nuv.retrofitapplication.model.MoviesResponse;
import com.nuv.retrofitapplication.network.ApiService;

public class MoviesHomeScreenActivity extends BaseActivity implements MoviesRecyclerViewAdapter.onSelect{

    Call<MoviesResponse> popularMoviesCall, topRatedMoviesCall;
    private ArrayList<MovieDetails> popularMovies=new ArrayList<>();
    private ArrayList<MovieDetails> topRated=new ArrayList<>();
    MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          themeCheck();
        //setContentView(R.layout.activity_movies_home);
        ActivityMoviesHomeBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_movies_home);
        ButterKnife.bind(this);
        setSupportActionBar(binding.toolbar.tbMain);
        binding.toolbar.tbMain.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        binding.toolbar.tbMain.setTitle(R.string.movie_recomendation_app);

        binding.toolbar.tbMain.setNavigationOnClickListener(v -> {
             Intent intent = new Intent(MoviesHomeScreenActivity.this, HomeScreenActivity.class);
             startActivity(intent);
         });

          if(isConnected()) {
              binding.tvSeemore.setOnClickListener(v -> {
                  Intent intent = new Intent(MoviesHomeScreenActivity.this, MoviesGridViewActivity.class);
                  intent.putExtra(Constants.MOVIE_TYPE, Constants.POPULAR);
                  startActivity(intent);
              });
               ApiService apiService = apiCall();

              popularMoviesCall = apiService.getposts();
              popularMoviesCall.enqueue(new Callback<MoviesResponse>() {
                  @Override
                  public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                      MoviesResponse moviesResponse = response.body();
                      assert moviesResponse != null;
                      popularMovies = (ArrayList<MovieDetails>) moviesResponse.getResults();
                      SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
                      Gson gson = new Gson();
                      String popular = gson.toJson(popularMovies);
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.putString(Constants.POPULAR_MOVIES, popular);
                      editor.apply();
                      List<MovieDetails> list= popularMovies.subList(0,6);
                      ArrayList<MovieDetails> arrayList = new ArrayList<>(list);
                      moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(arrayList,MoviesHomeScreenActivity.this::onButtonClick);
                      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false);
                      binding.recPopularMovies.setLayoutManager(mLayoutManager);
                      binding.recPopularMovies.setAdapter(moviesRecyclerViewAdapter);

                      moviesRecyclerViewAdapter.notifyDataSetChanged();


                  }

                  @Override
                  public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

                  }
              });
              topRatedMoviesCall = apiService.getresponse();
              topRatedMoviesCall.enqueue(new Callback<MoviesResponse>() {
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
                      moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(arrayList,MoviesHomeScreenActivity.this::onButtonClick);
                      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false);
                      binding.recTopRated.setLayoutManager(mLayoutManager);
                      binding.recTopRated.setAdapter(moviesRecyclerViewAdapter);
                      moviesRecyclerViewAdapter.notifyDataSetChanged();

                  }

                  @Override
                  public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

                  }
              });


              binding.tvSeemore2.setOnClickListener(v -> {
                  Intent intent = new Intent(MoviesHomeScreenActivity.this, MoviesGridViewActivity.class);
                  intent.putExtra(Constants.MOVIE_TYPE, Constants.TOP_RATED);
                  startActivity(intent);
              });
          }

          else
          {
              Toast.makeText(getApplicationContext(), Constants.NO_INTERNET, Toast.LENGTH_LONG).show();
          }


    }

    private void checkLang() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
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
            Intent intent =new Intent(MoviesHomeScreenActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        });
        searchViewItem.setOnMenuItemClickListener(item -> {
            Intent intent =new Intent(MoviesHomeScreenActivity.this, SearchMovieActivity.class);
            startActivity(intent);
            return true;
        });
        return true;
    }


    @Override
    public void onButtonClick(Intent intent) {
        startActivity(intent);
    }
}