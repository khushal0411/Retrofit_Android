package com.nuv.retrofitapplication;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuv.retrofitapplication.adapters.MoviesRecyclerViewAdapter;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.ActivitySearchMovie2Binding;
import com.nuv.retrofitapplication.model.MovieDetails;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchMovieActivity extends BaseActivity implements MoviesRecyclerViewAdapter.onSelect {
    MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    SharedPreferences sharedPreferences;
    ActivitySearchMovie2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeCheck();
        //setContentView(R.layout.activity_search_movie2);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search_movie2);
        sharedPreferences = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
        ButterKnife.bind(this);
        setSupportActionBar(binding.toolbar.tbMain);
        binding.toolbar.tbMain.setNavigationIcon(R.drawable.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        binding.toolbar.tbMain.setTitle(R.string.SearchMovies);

        binding.toolbar.tbMain.setNavigationOnClickListener(v -> finish());
        if (isConnected()) {

            Gson gson = new Gson();
            String popular1 = sharedPreferences.getString(Constants.POPULAR_MOVIES, null);
            String toprated1 = sharedPreferences.getString(Constants.TOP_RATED, null);
            Type type = new TypeToken<ArrayList<MovieDetails>>() {
            }.getType();
            ArrayList<MovieDetails> list1 = gson.fromJson(popular1, type);
            ArrayList<MovieDetails> list2 = gson.fromJson(toprated1, type);

            list1.addAll(list2);
            binding.btnSearch.setOnClickListener(v -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String data = sharedPreferences.getString(Constants.FINAL_ARRAY, null);
                if (data != null) {
                    editor.remove(Constants.FINAL_ARRAY);
                    editor.remove(Constants.MOVIE_INDEX);
                    editor.apply();
                }

                String query = binding.etSearch.getText().toString();
                int size = list1.size();
                for (int i = 0; i < size; i++) {
                    String titlename = list1.get(i).getTitle().toUpperCase();
                    if (titlename.contains(query.toUpperCase())) {

                        String json1 = sharedPreferences.getString(Constants.MOVIE_INDEX, null);
                        if (json1 == null) {
                            editor = sharedPreferences.edit();
                            editor.putString(Constants.MOVIE_INDEX, String.valueOf(i));
                            editor.apply();
                        } else {
                            json1 = sharedPreferences.getString(Constants.MOVIE_INDEX, null);
                            String combined = json1 + "," + i;
                            editor = sharedPreferences.edit();
                            editor.putString(Constants.MOVIE_INDEX, combined);
                            editor.apply();
                        }

                    }
                    else {

                    }

                }

                String index = sharedPreferences.getString(Constants.MOVIE_INDEX, null);
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
                    moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(finala,SearchMovieActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(), 3);
                    binding.searchRecyclerView.setLayoutManager(mLayoutManager);
                    binding.searchRecyclerView.setAdapter(moviesRecyclerViewAdapter);
                    moviesRecyclerViewAdapter.notifyDataSetChanged();
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

    @Override
    public void onButtonClick(Intent intent) {
        startActivity(intent);
    }
}
