package com.nuv.retrofitapplication;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<VideoDetails> results;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img_videos);
            title=itemView.findViewById(R.id.tv_videname);
            relativeLayout=itemView.findViewById(R.id.rl_videos);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_items,parent,false);
        return new VideoRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VideoDetails videoDetails = results.get(position);
        holder.title.setText(videoDetails.getTitle());
        Glide.with(holder.itemView.getContext()).load(videoDetails.getThumbnailUrl()).transform(new CircleCrop()).into(holder.image);
        holder.relativeLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.VIDEO_URL,videoDetails.getVideoUrl());
            bundle.putString(Constants.VIDEO_NAME,videoDetails.getTitle());
            VideoPlayerFragment myFrag= new VideoPlayerFragment();
            myFrag.setArguments(bundle);
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, myFrag);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public VideoRecyclerViewAdapter(ArrayList<VideoDetails> results) {
        this.results = results;
    }

}
