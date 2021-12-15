package com.nuv.retrofitapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<MovieDetails> results;
    private final int limit = 5;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView name,rating,date;
        ImageView movieposter;
        public MyViewHolder( View itemView) {

            super(itemView);
            relativeLayout=itemView.findViewById(R.id.rl_list);
            name=itemView.findViewById(R.id.tv_name);
            rating=itemView.findViewById(R.id.tv_movierating);
            movieposter=itemView.findViewById(R.id.img_movie);
            date=itemView.findViewById(R.id.tv_movie_date);
        }
    }


    @NonNull
    @Override
    public HomeRecyclerViewAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( HomeRecyclerViewAdapter.MyViewHolder holder, int position) {
        MovieDetails r = results.get(position);
        holder.name.setText(r.getTitle());
        holder.rating.setText(r.getVoteAverage().toString());
        holder.date.setText(r.getReleaseDate().substring(0,4));
        String url= "https://image.tmdb.org/t/p/w500/";
        Glide.with(holder.itemView).load(url+r.getPosterPath().toString()).into(holder.movieposter);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
                intent.putExtra("backgroundimage",r.getBackdropPath());
                intent.putExtra("posterimg",r.getPosterPath());
                intent.putExtra("title",r.getTitle());
                intent.putExtra("language",r.getOriginalLanguage());
                intent.putExtra("popularity",r.getPopularity().toString());
                intent.putExtra("ratings",r.getVoteAverage().toString());
                intent.putExtra("overview",r.getOverview());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(results.size()>limit)
        {
            return limit;
        }
        else {
            return results.size();
        }    }

    public HomeRecyclerViewAdapter(ArrayList<MovieDetails> results) {
        this.results = results;
    }
}
