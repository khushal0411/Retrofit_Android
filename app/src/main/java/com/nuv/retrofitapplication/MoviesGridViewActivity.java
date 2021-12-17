package com.nuv.retrofitapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesGridViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    public static String BASE_URL = "https://api.themoviedb.org/";
    Call<MoviesResponse> call2;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<MovieDetails> results;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Intent intent= getIntent();
        String type= intent.getStringExtra("type");
        toolbar=findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        if(type.equals("popular")){
            toolbar.setTitle(R.string.PopularMovies);
        }
        else {
            toolbar.setTitle(R.string.Topratedmovies);
        }


        toolbar.setNavigationOnClickListener(v -> finish());

loaddata();

swipeRefreshLayout=findViewById(R.id.swl_listdata);
        recyclerView=findViewById(R.id.rv_recactivity);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        loaddata();

        });



    }


    private void loaddata(){
        if(isConnected()) {
            Intent intent= getIntent();
            String type= intent.getStringExtra("type");
            if(type.equals("popular")){
                toolbar.setTitle(R.string.popular_movies);
                load();
            }
            else {
                toolbar.setTitle(R.string.top_rated_movies);
                loadtoprated();
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService= retrofit.create(ApiService.class);
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
    private void loadtoprated() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService= retrofit.create(ApiService.class);
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