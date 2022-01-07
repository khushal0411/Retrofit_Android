package com.nuv.retrofitapplication.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.MovieItemsBinding;
import com.nuv.retrofitapplication.model.MovieDetails;
import com.nuv.retrofitapplication.MovieDetailsActivity;
import com.nuv.retrofitapplication.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MyViewHolder>{
    private final ArrayList<MovieDetails> results;
    onSelect onSelect;
    MovieItemsBinding binding;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.movie_items,parent,false);
        return new MyViewHolder(binding.getRoot());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull  MoviesRecyclerViewAdapter.MyViewHolder holder, int position) {
       MovieDetails r = results.get(position);
        binding.setMovieDetails(r);
        binding.tvMovieDate.setText(r.getReleaseDate().substring(0,4));
        String url= Constants.URL_IMAGE;
        Glide.with(holder.itemView).load(url+ r.getPosterPath()).into(binding.imgMovie);
        binding.rlList.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
            intent.putExtra(Constants.MOVIE_DETAILS,r);
            onSelect.onButtonClick(intent);

        });
    }

    @Override
    public int getItemCount() {

            return results.size();
          }
    public MoviesRecyclerViewAdapter(ArrayList<MovieDetails> results,onSelect onSelect)
    {
        this.results=results;
       this.onSelect=onSelect;
    }

    public interface onSelect{
        void onButtonClick(Intent intent);
    }

}
