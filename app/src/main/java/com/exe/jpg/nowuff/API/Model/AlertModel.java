package com.exe.jpg.nowuff.API.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Patrick on 25/10/2017.
 */

public class AlertModel
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("campus")
    @Expose
    private Integer campus;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("posted")
    @Expose
    private String posted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }
}
