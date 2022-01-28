package com.nuv.retrofitapplication.kotlin_mvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nuv.retrofitapplication.R
import com.nuv.retrofitapplication.constant.Constants
import com.nuv.retrofitapplication.databinding.MoviesListKotlinBinding
import com.nuv.retrofitapplication.kotlin_mvvm.model.MovieDetails


class MoviesViewAdapter:RecyclerView.Adapter<ViewHolder>() {
 private var movies = mutableListOf<MovieDetails>()

    fun setMoviesList(movies:List<MovieDetails>){
        this.movies=movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= MoviesListKotlinBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie= movies[position]
        holder.binding.movieDetails=movie
        holder.binding.tvMovieDate.text=movie.releaseDate.toString().substring(0,4)
        Glide.with(holder.itemView).load(Constants.URL_IMAGE + movie.posterPath.toString()).apply(RequestOptions().placeholder(
            R.drawable.ccccc))
            .into(holder.binding.imgMovie)

    }

    override fun getItemCount(): Int {
       return movies.size
    }
}

class ViewHolder(val binding:MoviesListKotlinBinding):RecyclerView.ViewHolder(binding.root) {

}

