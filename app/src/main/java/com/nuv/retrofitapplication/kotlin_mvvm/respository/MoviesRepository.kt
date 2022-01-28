package com.nuv.retrofitapplication.kotlin_mvvm.respository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.nuv.retrofitapplication.kotlin_mvvm.dao.MoviesDao
import com.nuv.retrofitapplication.kotlin_mvvm.interfacekotlin.RetrofitInterface
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieDetails
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieResponse
import com.nuv.retrofitapplication.kotlin_mvvm.movies_database.MoviesDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Flow

class MoviesRepository constructor(private var retrofitInterface: RetrofitInterface,private val dao : MoviesDao){

    val getMovies =dao.getAllMovies()
    fun insert(movieDetails: List<MovieDetails>){
        dao.insertMovie(movieDetails)
    }

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