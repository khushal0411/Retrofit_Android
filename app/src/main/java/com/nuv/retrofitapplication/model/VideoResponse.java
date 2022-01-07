package com.nuv.retrofitapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuv.retrofitapplication.model.VideoDetails;

import java.util.List;

public class VideoResponse {

    @SerializedName("videos")
    @Expose
    private List<VideoDetails> videos = null;

    public List<VideoDetails> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDetails> videos) {
        this.videos = videos;
    }
}
