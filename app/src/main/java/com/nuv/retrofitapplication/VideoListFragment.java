package com.nuv.retrofitapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nuv.retrofitapplication.adapters.VideoRecyclerViewAdapter;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.FragmentVideoListBinding;
import com.nuv.retrofitapplication.model.VideoDetails;
import com.nuv.retrofitapplication.model.VideoResponse;
import com.nuv.retrofitapplication.network.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoListFragment extends Fragment implements VideoRecyclerViewAdapter.Onclick {
Call<VideoResponse> videoResponseCall;
ArrayList<VideoDetails> videoDetails = new ArrayList<>();
VideoRecyclerViewAdapter videoRecyclerViewAdapter;
FragmentVideoListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_video_list,container,false);
        HomeScreenActivity homeScreenActivity= (HomeScreenActivity)getActivity();
        homeScreenActivity.binding.toolbar.tbMain.setTitle(Constants.VIDEO_APP_NAME);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.VIDEO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        videoResponseCall =apiService.getVideoResponse();
        videoResponseCall.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoResponse> call, @NonNull Response<VideoResponse> response) {
                VideoResponse videoResponse =response.body();
                assert videoResponse != null;
                videoDetails = (ArrayList<VideoDetails>) videoResponse.getVideos();
                videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(videoDetails,VideoListFragment.this::onButtonClick);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
                binding.recVideos.setLayoutManager(mLayoutManager);
                binding.recVideos.setAdapter(videoRecyclerViewAdapter);
                videoRecyclerViewAdapter.notifyDataSetChanged();
                binding.tvVideolist1.setText(videoDetails.get(0).getTitle());
                Glide.with(view.getContext()).load(videoDetails.get(0).getThumbnailUrl()).fitCenter().into(binding.imgVideolist1);
                binding.tvVideolist2.setText(videoDetails.get(1).getTitle());
                Glide.with(view.getContext()).load(videoDetails.get(1).getThumbnailUrl()).fitCenter().into(binding.imgVideolist2);
            }

            @Override
            public void onFailure(@NonNull Call<VideoResponse> call, @NonNull Throwable t) {
                Toast.makeText(view.getContext(),Constants.ERROR,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onButtonClick(Fragment frag) {
        FragmentTransaction transactions = getParentFragmentManager().beginTransaction();
        transactions.add(R.id.fl_home_screen,frag);
        transactions.hide(VideoListFragment.this);
        transactions.addToBackStack(VideoListFragment.class.getName());
        transactions.commit();
    }



}