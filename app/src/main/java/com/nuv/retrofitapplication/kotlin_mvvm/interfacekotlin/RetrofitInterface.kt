package com.nuv.retrofitapplication.kotlin_mvvm.interfacekotlin

import com.nuv.retrofitapplication.constant.Constants
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("3/movie/popular?api_key=b7cd3340a794e5a2f35e3abb820b497f")
    fun getMovieResponse(): Call<MovieResponse>

    companion object{
        private var retrofitInterface:RetrofitInterface ? =null
        fun getInstance():RetrofitInterface{
            if(retrofitInterface==null){
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitInterface = retrofit.create(RetrofitInterface::class.java)
            }
            return retrofitInterface!!
        }
    }
}