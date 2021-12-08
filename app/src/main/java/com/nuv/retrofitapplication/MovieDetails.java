package com.nuv.retrofitapplication;

import com.google.gson.annotations.SerializedName;

public class MovieDetails {
    @SerializedName("original_title")
    private String orignal_title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String vote_average;

    public MovieDetails(String orignal_title, String overview, String vote_average) {
        this.orignal_title = orignal_title;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    public String getOrignal_title() {
        return orignal_title;
    }


    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }
}