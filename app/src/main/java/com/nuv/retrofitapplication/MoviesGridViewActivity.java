package com.nuv.retrofitapplication;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
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

import android.widget.Toast;


import com.nuv.retrofitapplication.adapters.MoviesRecyclerViewAdapter;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.ActivityMoviesGridBinding;
import com.nuv.retrofitapplication.model.MovieDetails;
import com.nuv.retrofitapplication.model.MoviesResponse;
import com.nuv.retrofitapplication.network.ApiService;

import java.util.ArrayList;

import java.util.Objects;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesGridViewActivity extends BaseActivity implements MoviesRecyclerViewAdapter.onSelect {

    MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    Call<MoviesResponse> call2;
    ArrayList<MovieDetails> results;

    ActivityMoviesGridBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeCheck();
        binding= DataBindingUtil.setContentView(this,R.layout.activity_movies_grid);
        ButterKnife.bind(this);
        Intent intent= getIntent();
        String type= intent.getStringExtra(Constants.MOVIE_TYPE);
        setSupportActionBar(binding.toolbar.tbMain);
        binding.toolbar.tbMain.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        if(type.equals(Constants.POPULAR)){
            binding.toolbar.tbMain.setTitle(R.string.PopularMovies);
        }
        else {
            binding.toolbar.tbMain.setTitle(R.string.Topratedmovies);
        }


        binding.toolbar.tbMain.setNavigationOnClickListener(v -> finish());

loadData();

        binding.swlListData.setOnRefreshListener(() -> {
           binding.swlListData.setRefreshing(false);
        loadData();

        });



    }


    private void loadData(){
        if(isConnected()) {
            Intent intent= getIntent();
            String type= intent.getStringExtra(Constants.MOVIE_TYPE);
            if(type.equals(Constants.POPULAR)){
                binding.toolbar.tbMain.setTitle(R.string.popular_movies);
                load();
            }
            else {
                binding.toolbar.tbMain.setTitle(R.string.top_rated_movies);
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

        ApiService apiService = apiCall();

        call2 = apiService.getposts();

        call2.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull  Call<MoviesResponse> call2,@NonNull Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                assert moviesResponse != null;
                 results = (ArrayList<MovieDetails>) moviesResponse.getResults();

                moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(results,MoviesGridViewActivity.this::onButtonClick);
                moviesRecyclerViewAdapter.notifyDataSetChanged();

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(),3);
                binding.rvMoviesGrid.setLayoutManager(mLayoutManager);
                binding.rvMoviesGrid.setAdapter(moviesRecyclerViewAdapter);
            }

            @Override
            public void onFailure (@NonNull Call<MoviesResponse> call,@NonNull Throwable t) {

            }
        });



    }
    private void loadTopRated() {
        ApiService apiService = apiCall();
        call2 = apiService.getresponse();

        call2.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call2,@NonNull Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                assert moviesResponse != null;
                ArrayList<MovieDetails> results = (ArrayList<MovieDetails>) moviesResponse.getResults();

                moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(results,MoviesGridViewActivity.this::onButtonClick);
                moviesRecyclerViewAdapter.notifyDataSetChanged();

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(),3);
                binding.rvMoviesGrid.setLayoutManager(mLayoutManager);
                binding.rvMoviesGrid.setAdapter(moviesRecyclerViewAdapter);
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

    @Override
    public void onButtonClick(Intent intent) {
        startActivity(intent);
    }
}