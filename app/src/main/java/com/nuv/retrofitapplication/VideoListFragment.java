package com.nuv.retrofitapplication;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoListFragment extends Fragment {
Call<VideoResponse> call;
ArrayList<VideoDetails> videoDetails = new ArrayList<>();
VideoRecyclerViewAdapter videoRecyclerViewAdapter;
@BindView(R.id.rec_videos) RecyclerView recyclerView;
@BindView(R.id.img_videolist1) ImageView thumbnail;
@BindView(R.id.img_videolist2) ImageView thumbnail2;
@BindView(R.id.tv_videolist1) TextView name;
@BindView(R.id.tv_videolist2) TextView name2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transactions = getParentFragmentManager().beginTransaction();
                transactions.replace(R.id.frame, homeFragment);
                transactions.commit();
            }
        };requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_video_list, container, false);
        ButterKnife.bind(this,view);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.VIDEO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        call=apiService.getVideoResponse();
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoResponse> call, @NonNull Response<VideoResponse> response) {
                VideoResponse videoResponse =response.body();
                assert videoResponse != null;
                videoDetails = (ArrayList<VideoDetails>) videoResponse.getVideos();
                videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(videoDetails);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(videoRecyclerViewAdapter);
                videoRecyclerViewAdapter.notifyDataSetChanged();
                name.setText(videoDetails.get(0).getTitle());
                Glide.with(view.getContext()).load(videoDetails.get(0).getThumbnailUrl()).fitCenter().into(thumbnail);
                name2.setText(videoDetails.get(1).getTitle());
                Glide.with(view.getContext()).load(videoDetails.get(1).getThumbnailUrl()).fitCenter().into(thumbnail2);
            }

            @Override
            public void onFailure(@NonNull Call<VideoResponse> call, @NonNull Throwable t) {
                Toast.makeText(view.getContext(),Constants.ERROR,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}