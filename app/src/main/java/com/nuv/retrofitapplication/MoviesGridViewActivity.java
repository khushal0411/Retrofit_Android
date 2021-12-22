package com.nuv.retrofitapplication;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;


import java.util.ArrayList;

import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesGridViewActivity extends BaseActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    Call<MoviesResponse> call2;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<MovieDetails> results;
    Toolbar toolbar;

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
        setContentView(R.layout.activity_popular_movies);
        Intent intent= getIntent();
        String type= intent.getStringExtra(Constants.TYPE);
        toolbar=findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        if(type.equals(Constants.POPULAR)){
            toolbar.setTitle(R.string.PopularMovies);
        }
        else {
            toolbar.setTitle(R.string.Topratedmovies);
        }


        toolbar.setNavigationOnClickListener(v -> finish());

loadData();

swipeRefreshLayout=findViewById(R.id.swl_listdata);
        recyclerView=findViewById(R.id.rv_recactivity);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        loadData();

        });



    }


    private void loadData(){
        if(isConnected()) {
            Intent intent= getIntent();
            String type= intent.getStringExtra(Constants.TYPE);
            if(type.equals(Constants.POPULAR)){
                toolbar.setTitle(R.string.popular_movies);
                load();
            }
            else {
                toolbar.setTitle(R.string.top_rated_movies);
                loadTopRated();
            }

        }
        else {
            Toast.makeText(getApplicationContext(), Constants.NO_INTERNET, Toast.LENGTH_LONG).show();
        }

    }
    private boolean isConnected() {
        boolean connected= false;
        ConnectivityManager cm= (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        connected = info != null && info.isAvailable() && info.isConnected();
        return connected;
    }


    private void load() {

        ApiService apiService =apicall();

        call2 = apiService.getposts();

        call2.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull  Call<MoviesResponse> call2,@NonNull Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                assert moviesResponse != null;
                 results = (ArrayList<MovieDetails>) moviesResponse.getResults();

                recyclerViewAdapter= new RecyclerViewAdapter(results);
                recyclerViewAdapter.notifyDataSetChanged();

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(),3);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure (@NonNull Call<MoviesResponse> call,@NonNull Throwable t) {

            }
        });



    }
    private void loadTopRated() {
        ApiService apiService =apicall();
        call2 = apiService.getresponse();

        call2.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call2,@NonNull Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                assert moviesResponse != null;
                ArrayList<MovieDetails> results = (ArrayList<MovieDetails>) moviesResponse.getResults();

                recyclerViewAdapter= new RecyclerViewAdapter(results);
                recyclerViewAdapter.notifyDataSetChanged();

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(),3);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call,@NonNull Throwable t) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_allpages, menu);
        MenuItem settings=menu.findItem(R.id.settings);
        settings.setOnMenuItemClickListener(item -> {
            Intent intent =new Intent(MoviesGridViewActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        });

        return true;

    }

}