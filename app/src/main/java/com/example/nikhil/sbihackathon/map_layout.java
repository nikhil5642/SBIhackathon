package com.example.nikhil.sbihackathon;

import java.io.Serializable;

/**
 * Created by nikhil on 19/6/17.
 */

public class map_layout implements Serializable {
    Double latitude,longitude;
    String title,key;
    int fback;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFback() {
        return fback;
    }

    public void setFback(int fback) {
        this.fback = fback;
    }

    public map_layout() {
    }

    public map_layout(Double latitude, Double longitude, String title, int fback, String key) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.fback = fback;
        this.key=key;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
