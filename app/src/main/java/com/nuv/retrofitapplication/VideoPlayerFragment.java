package com.nuv.retrofitapplication;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;


public class VideoPlayerFragment extends Fragment {
TextView display;
PlayerView playerView;
SimpleExoPlayer simpleExoPlayer;

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
        display=view.findViewById(R.id.tv_display);
        simpleExoPlayer= (SimpleExoPlayer) new ExoPlayer.Builder(view.getContext()).build();
        playerView=view.findViewById(R.id.exo_videoplayer);
        assert getArguments() != null;
        String videoUrl= getArguments().getString(Constants.VIDEO_URL);
        String videoName= getArguments().getString(Constants.VIDEO_NAME);
        display.setText(videoName);
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();

        return view;

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