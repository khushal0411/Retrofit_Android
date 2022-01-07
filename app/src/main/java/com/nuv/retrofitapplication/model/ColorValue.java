package com.nuv.retrofitapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ColorValue {

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("value")
    @Expose
    private String value;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}