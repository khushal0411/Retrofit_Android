package com.nuv.retrofitapplication

import android.app.Application
import com.nuv.retrofitapplication.kotlin_mvvm.interfacekotlin.RetrofitInterface
import com.nuv.retrofitapplication.kotlin_mvvm.movies_database.MoviesDatabase
import com.nuv.retrofitapplication.kotlin_mvvm.respository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App:Application() {
    val applicationScope= CoroutineScope(SupervisorJob())
    val database by lazy { MoviesDatabase.getDatabase(this,applicationScope) }
    val repository by lazy{MoviesRepository(RetrofitInterface.getInstance(),database.getMoviesDao())}
}