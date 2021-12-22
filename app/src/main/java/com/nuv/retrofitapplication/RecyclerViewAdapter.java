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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private final ArrayList<MovieDetails> results;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView name,rating,date;
        ImageView moviePoster;


        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
         relativeLayout=itemView.findViewById(R.id.rl_list);
         name=itemView.findViewById(R.id.tv_name);
         rating=itemView.findViewById(R.id.tv_movierating);
         moviePoster=itemView.findViewById(R.id.img_movie);
         date=itemView.findViewById(R.id.tv_movie_date);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull  RecyclerViewAdapter.MyViewHolder holder, int position) {
       MovieDetails r = results.get(position);
        holder.name.setText(r.getTitle());
        holder.rating.setText(r.getVoteAverage().toString());
        holder.date.setText(r.getReleaseDate().substring(0,4));
        String url= Constants.URL_IMAGE;
        Glide.with(holder.itemView).load(url+ r.getPosterPath()).into(holder.moviePoster);
        holder.relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(),MovieDetailsActivity.class);
            intent.putExtra(Constants.DETAILS,r);
            v.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {

            return results.size();
          }
    public RecyclerViewAdapter(ArrayList<MovieDetails> results)
    {
        this.results=results;
    }


}
