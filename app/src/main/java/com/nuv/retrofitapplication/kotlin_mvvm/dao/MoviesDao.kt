package com.nuv.retrofitapplication.kotlin_mvvm.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieDetails

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: List<MovieDetails>)

    @Query("SELECT * FROM MovieDetails")
    fun getAllMovies():LiveData<List<MovieDetails>>
}