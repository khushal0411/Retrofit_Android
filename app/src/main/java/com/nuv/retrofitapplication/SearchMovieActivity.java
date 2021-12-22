package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
public class SearchMovieActivity extends AppCompatActivity {
 Toolbar toolbar;
    EditText searchQuery;
    ImageButton search;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
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
        setContentView(R.layout.activity_search_movie2);
        toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.SearchMovies);

        toolbar.setNavigationOnClickListener(v -> finish());
        searchQuery = findViewById(R.id.et_search);
        search = findViewById(R.id.btn_search);
        recyclerView = findViewById(R.id.search_recyclerview);
        if (isConnected()) {

            Gson gson = new Gson();
            String popular1 = sharedPreferences.getString(Constants.POPULAR_MOVIES, null);
            String toprated1 = sharedPreferences.getString(Constants.TOP_RATED, null);
            Type type = new TypeToken<ArrayList<MovieDetails>>() {
            }.getType();
            ArrayList<MovieDetails> list1 = gson.fromJson(popular1, type);
            ArrayList<MovieDetails> list2 = gson.fromJson(toprated1, type);

            list1.addAll(list2);
            search.setOnClickListener(v -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String data = sharedPreferences.getString(Constants.FINAL_ARRAY, null);
                if (data != null) {
                    editor.remove(Constants.FINAL_ARRAY);
                    editor.remove(Constants.INDEX);
                    editor.apply();
                }

                String query = searchQuery.getText().toString();
                int size = list1.size();
                for (int i = 0; i < size; i++) {
                    String titlename = list1.get(i).getTitle().toUpperCase();
                    if (titlename.contains(query.toUpperCase())) {

                        String json1 = sharedPreferences.getString(Constants.INDEX, null);
                        if (json1 == null) {
                            editor = sharedPreferences.edit();
                            editor.putString(Constants.INDEX, String.valueOf(i));
                            editor.apply();
                        } else {
                            json1 = sharedPreferences.getString(Constants.INDEX, null);
                            String combined = json1 + "," + i;
                            editor = sharedPreferences.edit();
                            editor.putString(Constants.INDEX, combined);
                            editor.apply();
                        }

                    }
                    else {

                    }

                }

                String index = sharedPreferences.getString(Constants.INDEX, null);
                if(index !=null) {
                    String[] split = index.split(",");
                    for (String s : split) {
                        MovieDetails searchresult = list1.get(Integer.parseInt(s));
                        ArrayList<MovieDetails> com = new ArrayList<>();
                        com.add(searchresult);
                        String finalarray = gson.toJson(com);
                        editor = sharedPreferences.edit();
                        String j = sharedPreferences.getString(Constants.FINAL_ARRAY, null);
                        if (j == null) {
                            editor.putString(Constants.FINAL_ARRAY, finalarray);
                            editor.apply();
                        } else {
                            ArrayList<MovieDetails> finala = gson.fromJson(j, type);
                            finala.addAll(com);
                            String f = gson.toJson(finala);
                            editor.putString(Constants.FINAL_ARRAY, f);
                            editor.apply();

                        }

                    }
                    String finalarray = sharedPreferences.getString(Constants.FINAL_ARRAY, null);
                    ArrayList<MovieDetails> finala = gson.fromJson(finalarray, type);
                    recyclerViewAdapter = new RecyclerViewAdapter(finala);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(), 3);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                }

                else
                {
                    Toast.makeText(getApplicationContext(),Constants.NOT_FOUND,Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            Toast.makeText(getApplicationContext(),Constants.NO_INTERNET,Toast.LENGTH_LONG).show();
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
        inflater.inflate(R.menu.toolbar_allpages, menu);
        MenuItem settings=menu.findItem(R.id.settings);
        settings.setOnMenuItemClickListener(item -> {
            Intent intent =new Intent(SearchMovieActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        });

        return true;

    }
}
