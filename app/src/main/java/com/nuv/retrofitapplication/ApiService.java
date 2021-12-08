package com.nuv.retrofitapplication;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/3/movie/popular?api_key=b7cd3340a794e5a2f35e3abb820b497f")
    Call<MovieDetails> getposts();


}
