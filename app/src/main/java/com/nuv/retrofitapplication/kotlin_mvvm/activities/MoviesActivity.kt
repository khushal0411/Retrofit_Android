package com.nuv.retrofitapplication.kotlin_mvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nuv.retrofitapplication.R
import com.nuv.retrofitapplication.databinding.ActivityMoviesBinding
import com.nuv.retrofitapplication.kotlin_mvvm.adapters.MoviesViewAdapter
import com.nuv.retrofitapplication.kotlin_mvvm.interfacekotlin.RetrofitInterface
import com.nuv.retrofitapplication.kotlin_mvvm.respository.MoviesRepository
import com.nuv.retrofitapplication.kotlin_mvvm.viewModel.MoviesViewModel
import com.nuv.retrofitapplication.kotlin_mvvm.viewModel.ViewModelFactory

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMoviesBinding
    private lateinit var viewModel:MoviesViewModel
    private val retrofitService=RetrofitInterface.getInstance()
    private val adapter= MoviesViewAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_movies)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this,ViewModelFactory(MoviesRepository(retrofitService))).get(MoviesViewModel::class.java)
        binding.recPopularMoviesMvvm.adapter=adapter
        binding.recPopularMoviesMvvm.layoutManager=GridLayoutManager(this,3)
        viewModel.getMovies().observe(this, Observer {
            adapter.setMoviesList(it)
        })

    }
}