package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
     public static final String BASE_URL = "https://api.themoviedb.org";
    Call<MovieDetails> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView display= findViewById(R.id.tv_display);
        Button sub= findViewById(R.id.btn_submit);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService apiService= retrofit.create(ApiService.class);


        call = apiService.getposts();
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call.enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        display.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {
                        display.setText(t.toString());
                    }
                });
            }
        });

    }
}