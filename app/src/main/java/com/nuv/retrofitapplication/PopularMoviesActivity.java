package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularMoviesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<Results> results=new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    public static String BASE_URL = "https://api.themoviedb.org/";
    Call<MoviesResponse> call2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Intent intent= getIntent();
        String type= intent.getStringExtra("type");

        if(type.equals("popular")){
            load();
        }
        else {
            loadtoprated();
        }





        recyclerView=findViewById(R.id.rv_recactivity);


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
            public void onResponse(Call<MoviesResponse> call2, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                ArrayList<Results> results = (ArrayList<Results>) moviesResponse.getResults();

                recyclerViewAdapter= new RecyclerViewAdapter(results);
                recyclerViewAdapter.notifyDataSetChanged();

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(),3);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

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
            public void onResponse(Call<MoviesResponse> call2, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                ArrayList<Results> results = (ArrayList<Results>) moviesResponse.getResults();

                recyclerViewAdapter= new RecyclerViewAdapter(results);
                recyclerViewAdapter.notifyDataSetChanged();

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(),3);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });


    }

}