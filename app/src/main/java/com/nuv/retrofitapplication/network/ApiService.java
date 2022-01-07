package com.nuv.retrofitapplication.network;
import com.nuv.retrofitapplication.model.ColorApiResponse;
import com.nuv.retrofitapplication.model.MoviesResponse;
import com.nuv.retrofitapplication.model.VideoResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("3/movie/popular?api_key=b7cd3340a794e5a2f35e3abb820b497f")
    Call<MoviesResponse> getposts();


    @GET("3/movie/top_rated?api_key=b7cd3340a794e5a2f35e3abb820b497f")
    Call<MoviesResponse> getresponse();

    @GET("colours")
    Call<ArrayList<ColorApiResponse>> getColorResponse();

    @GET("videos")
    Call<VideoResponse> getVideoResponse();
}
