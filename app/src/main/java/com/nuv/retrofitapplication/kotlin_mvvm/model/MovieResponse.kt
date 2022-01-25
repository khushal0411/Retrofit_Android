package com.nuv.retrofitapplication.kotlin_mvvm.model

import com.google.gson.annotations.SerializedName


data class MovieResponse (

    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var results      : ArrayList<MovieDetails> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null

)