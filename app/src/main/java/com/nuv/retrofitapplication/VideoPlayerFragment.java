package com.nuv.retrofitapplication;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoPlayerFragment extends Fragment {
@BindView(R.id.tv_display) TextView display;
@BindView(R.id.exo_videoplayer) PlayerView playerView;
@BindView(R.id.ll_swipeLayout) LinearLayout linearLayout;
@BindView(R.id.fl_swipe) FrameLayout frameLayout;
SimpleExoPlayer simpleExoPlayer;
float x1,y1,x2,y2;
int index=0;
ArrayList<VideoDetails> videoDetails;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                VideoListFragment videoListFragment= new VideoListFragment();
                FragmentTransaction transactions = getParentFragmentManager().beginTransaction();
                transactions.replace(R.id.frame, videoListFragment);
                transactions.commit();
            }
        };requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_video_player, container, false);
       ButterKnife.bind(this,view);
        simpleExoPlayer= (SimpleExoPlayer) new ExoPlayer.Builder(view.getContext()).build();
        assert getArguments() != null;
        String videoUrl= getArguments().getString(Constants.VIDEO_URL);
        String videoName= getArguments().getString(Constants.VIDEO_NAME);
        videoDetails= (ArrayList<VideoDetails>) getArguments().getSerializable(Constants.VIDEO_LIST);
        display.setText(videoName);
        index= getArguments().getInt(Constants.INDEX_VIDEO);
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();
        playerView.setOnTouchListener(new View.OnTouchListener() {
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
        return view;

    }
    public  void setVideo(){
        display.setText(videoDetails.get(index).getTitle());
        simpleExoPlayer.stop();
        simpleExoPlayer.clearMediaItems();
        MediaItem m = MediaItem.fromUri(videoDetails.get(index).getVideoUrl());
        simpleExoPlayer.addMediaItem(m);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();
    }
    @Override
    public void onStop() {
        super.onStop();
        simpleExoPlayer.stop();
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        simpleExoPlayer.stop();
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        simpleExoPlayer.stop();
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        String videoUrl= getArguments().getString(Constants.VIDEO_URL);
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();
    }


}