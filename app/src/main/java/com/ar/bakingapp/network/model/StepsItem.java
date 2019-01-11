package com.ar.bakingapp.network.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class StepsItem implements Serializable {

    @ColumnInfo(name = "video_url")
    @SerializedName("videoURL")
    private String videoURL;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String description;

    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "short_description")
    @SerializedName("shortDescription")
    private String shortDescription;

    @ColumnInfo(name = "thumbnail_url")
    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public String toString() {
        return
                "StepsItem{" +
                        "videoURL = '" + videoURL + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        ",shortDescription = '" + shortDescription + '\'' +
                        ",thumbnailURL = '" + thumbnailURL + '\'' +
                        "}";
    }
}