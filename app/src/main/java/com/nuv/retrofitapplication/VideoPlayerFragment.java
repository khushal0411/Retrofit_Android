package com.nuv.retrofitapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.FragmentVideoPlayerBinding;
import com.nuv.retrofitapplication.model.VideoDetails;

import java.util.ArrayList;


public class VideoPlayerFragment extends Fragment {

ExoPlayer exoPlayer;
float x1,y1,x2,y2;
int index=0;
ArrayList<VideoDetails> videoDetails;
FragmentVideoPlayerBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_video_player,container,false);
        HomeScreenActivity homeScreenActivity= (HomeScreenActivity)getActivity();
        homeScreenActivity.binding.toolbar.tbMain.setTitle(Constants.VIDEO_PLAYER);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exoPlayer = new ExoPlayer.Builder(view.getContext()).build();
        assert getArguments() != null;
        String videoUrl= getArguments().getString(Constants.VIDEO_URL);
        String videoName= getArguments().getString(Constants.VIDEO_NAME);
        index= getArguments().getInt(Constants.VIDEO_INDEX);
        videoDetails= (ArrayList<VideoDetails>) getArguments().getSerializable(Constants.VIDEO_LIST);
        binding.tvDisplay.setText(videoDetails.get(index).getTitle());
        binding.exoVideoPlayer.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        exoPlayer.addMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
        binding.exoVideoPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch(touchEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if(x1 < x2){
                            if(index==0){
                                index=videoDetails.size();
                            }
                            else {
                                index= index-1;
                            }
                            if(index<videoDetails.size())
                            {
                                setVideo();
                            }
                            else {
                                index=0;
                                setVideo();
                            }
                        }else if(x1 > x2){
                            index= index+1;
                            if(index<videoDetails.size())
                            {
                                setVideo();
                            }
                            else {
                                index=0;
                                setVideo();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    public  void setVideo(){
        binding.tvDisplay.setText(videoDetails.get(index).getTitle());
        exoPlayer.stop();
        exoPlayer.clearMediaItems();
        MediaItem m = MediaItem.fromUri(videoDetails.get(index).getVideoUrl());
        exoPlayer.addMediaItem(m);
        exoPlayer.prepare();
        exoPlayer.play();
    }
    @Override
    public void onStop() {
        super.onStop();
        exoPlayer.stop();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        exoPlayer.stop();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayer.stop();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        String videoUrl= getArguments().getString(Constants.VIDEO_URL);
        binding.exoVideoPlayer.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        exoPlayer.addMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
    }

}