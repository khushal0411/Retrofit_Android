package com.nuv.retrofitapplication.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.R;
import com.nuv.retrofitapplication.databinding.VideoListItemsBinding;
import com.nuv.retrofitapplication.model.VideoDetails;
import com.nuv.retrofitapplication.VideoPlayerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<VideoDetails> results;
    Onclick onclick;
    VideoListItemsBinding binding;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.video_list_items,parent,false);
        return new VideoRecyclerViewAdapter.MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VideoDetails videoDetails = results.get(position);
        binding.tvVidename.setText(videoDetails.getTitle());
        Glide.with(holder.itemView.getContext()).load(videoDetails.getThumbnailUrl()).transform(new CircleCrop()).into(binding.imgVideos);
        binding.rlVideos.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.VIDEO_URL,videoDetails.getVideoUrl());
            bundle.putString(Constants.VIDEO_NAME,videoDetails.getTitle());
            bundle.putSerializable(Constants.VIDEO_LIST,results);
            bundle.putInt(Constants.VIDEO_INDEX,position);
            VideoPlayerFragment myFrag= new VideoPlayerFragment();
            myFrag.setArguments(bundle);
            onclick.onButtonClick(myFrag);

        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public VideoRecyclerViewAdapter(ArrayList<VideoDetails> results,Onclick onclick) {
        this.results = results;
        this.onclick=onclick;
    }

    public interface Onclick{

        void onButtonClick(Fragment farg);
    }

}
