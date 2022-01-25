package com.nuv.retrofitapplication.kotlin_mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nuv.retrofitapplication.kotlin_mvvm.respository.MoviesRepository

class ViewModelFactory constructor(private val repository: MoviesRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return if (modelClass.isAssignableFrom(MoviesViewModel::class.java))
        {
             MoviesViewModel(this.repository) as T
        }
        else{
                 throw IllegalArgumentException("View Model Not Found")
        }
    }
}