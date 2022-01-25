package com.nuv.retrofitapplication.kotlin_mvvm.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieDetails
import com.nuv.retrofitapplication.kotlin_mvvm.respository.MoviesRepository


class MoviesViewModel constructor(private val repository: MoviesRepository): ViewModel() {

    fun getMovies(): MutableLiveData<List<MovieDetails>> {
        return repository.getMovies()
    }

}

