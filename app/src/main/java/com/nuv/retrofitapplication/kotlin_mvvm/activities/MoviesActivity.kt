package com.nuv.retrofitapplication.kotlin_mvvm.activities

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nuv.retrofitapplication.R
import com.nuv.retrofitapplication.databinding.ActivityMoviesBinding
import com.nuv.retrofitapplication.kotlin_mvvm.adapters.MoviesViewAdapter
import com.nuv.retrofitapplication.kotlin_mvvm.interfacekotlin.RetrofitInterface
import com.nuv.retrofitapplication.kotlin_mvvm.movies_database.MoviesDatabase
import com.nuv.retrofitapplication.kotlin_mvvm.respository.MoviesRepository
import com.nuv.retrofitapplication.kotlin_mvvm.viewModel.MoviesViewModel
import com.nuv.retrofitapplication.kotlin_mvvm.viewModel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class MoviesActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMoviesBinding
    private lateinit var viewModel:MoviesViewModel
    private  var database=MoviesDatabase
    private val retrofitService=RetrofitInterface.getInstance()
    private val adapter= MoviesViewAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_movies)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this,ViewModelFactory(MoviesRepository(retrofitService,database.getDatabase(applicationContext, CoroutineScope(SupervisorJob())).getMoviesDao()))).get(MoviesViewModel::class.java)
        binding.recPopularMoviesMvvm.adapter=adapter
        binding.recPopularMoviesMvvm.layoutManager=GridLayoutManager(this,3)

        if(isConnected()){
            viewModel.getMovies().observe(this, {
                adapter.setMoviesList(it)
                viewModel.insertMovie(it)
            })
        }
        else{
            viewModel.moviesList.observe(this, {
                if (it.isNotEmpty()){
                    adapter.setMoviesList(it)
                }
                else{
                    val toast= Toast.makeText(this,"No Internet connected",Toast.LENGTH_SHORT)
                    toast.show()
                }
            })

        }

    }


private fun isConnected(): Boolean {
    var connected = false
    val cm =getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = cm.activeNetworkInfo
    connected = info != null && info.isAvailable && info.isConnected
    return connected
}
}