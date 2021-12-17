package com.nuv.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
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
    EditText searchquery;
    ImageButton search;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie2);
        toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.SearchMovies);

        toolbar.setNavigationOnClickListener(v -> finish());
        searchquery = findViewById(R.id.et_search);
        search = findViewById(R.id.btn_search);
        recyclerView = findViewById(R.id.search_recyclerview);
        if (isConnected()) {
            SharedPreferences sharedPreferences = getSharedPreferences("Movies", MODE_PRIVATE);
            Gson gson = new Gson();
            String popular1 = sharedPreferences.getString("popularmovies", "");
            String toprated1 = sharedPreferences.getString("toprated", "");
            Type type = new TypeToken<ArrayList<MovieDetails>>() {
            }.getType();
            ArrayList<MovieDetails> list1 = gson.fromJson(popular1, type);
            ArrayList<MovieDetails> list2 = gson.fromJson(toprated1, type);

            list1.addAll(list2);
            search.setOnClickListener(v -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String data = sharedPreferences.getString("Finalarray", null);
                if (data != null) {
                    editor.remove("Finalarray");
                    editor.remove("index");
                    editor.apply();
                }

                String query = searchquery.getText().toString();
                int size = list1.size();
                for (int i = 0; i < size; i++) {
                    String titlename = list1.get(i).getTitle().toUpperCase();
                    if (titlename.contains(query.toUpperCase())) {

                        String json1 = sharedPreferences.getString("index", null);
                        if (json1 == null) {
                            editor = sharedPreferences.edit();
                            editor.putString("index", String.valueOf(i));
                            editor.apply();
                        } else {
                            json1 = sharedPreferences.getString("index", null);
                            String combined = json1 + "," + i;
                            editor = sharedPreferences.edit();
                            editor.putString("index", combined);
                            editor.apply();
                        }

                    }
                    else {

                    }

                }

                String index = sharedPreferences.getString("index", null);
                if(index !=null) {
                    String[] split = index.split(",");
                    for (String s : split) {
                        MovieDetails searchresult = list1.get(Integer.parseInt(s));
                        ArrayList<MovieDetails> com = new ArrayList<>();
                        com.add(searchresult);
                        String finalarray = gson.toJson(com);
                        editor = sharedPreferences.edit();
                        String j = sharedPreferences.getString("Finalarray", null);
                        if (j == null) {
                            editor.putString("Finalarray", finalarray);
                            editor.apply();
                        } else {
                            ArrayList<MovieDetails> finala = gson.fromJson(j, type);
                            finala.addAll(com);
                            String f = gson.toJson(finala);
                            editor.putString("Finalarray", f);
                            editor.apply();

                        }

                    }
                    String finalarray = sharedPreferences.getString("Finalarray", null);
                    ArrayList<MovieDetails> finala = gson.fromJson(finalarray, type);
                    recyclerViewAdapter = new RecyclerViewAdapter(finala);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(), 3);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                }

                else
                {
                    Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
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
