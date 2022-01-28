package com.nuv.retrofitapplication.kotlin_mvvm.movies_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nuv.retrofitapplication.kotlin_mvvm.dao.MoviesDao
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieDetails
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [MovieDetails::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

    companion object {
        private var databaseInstance: MoviesDatabase? = null
        fun getDatabase(context: Context,scope:CoroutineScope): MoviesDatabase {

            return databaseInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "MovieDatabase"
                ).allowMainThreadQueries().build()
                databaseInstance = instance
                return instance
            }
        }

    }
}