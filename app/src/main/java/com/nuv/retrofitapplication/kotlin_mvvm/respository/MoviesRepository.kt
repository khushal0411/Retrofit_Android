package com.nuv.retrofitapplication.kotlin_mvvm.respository

import androidx.lifecycle.MutableLiveData
import com.nuv.retrofitapplication.kotlin_mvvm.interfacekotlin.RetrofitInterface
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieDetails
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository constructor(private var retrofitInterface: RetrofitInterface){




    fun getMovies(): MutableLiveData<List<MovieDetails>> {

        val movieList= MutableLiveData<List<MovieDetails>>()
        val errorMessage=MutableLiveData<String>()

        val res= retrofitInterface.getMovieResponse()
        res.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                var mresponse=MovieResponse()
                mresponse= response.body()!!
                movieList.postValue(mresponse.results)

                //movieList.value= response.body()?.results
                //movieList.postValue(response.body()?.results)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
        return movieList
    }


}