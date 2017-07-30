package com.example.nikhil.sbihackathon;

import android.app.Activity;

/**
 * Created by nikhil on 12/6/17.
 */

public class grid_item {
    int image;
    String Name;
    Class activity;


    public int getImage() {
        return image;
    }

    public String getName() {
        return Name;
    }

    public Class getActivity() {
        return activity;
    }

    public grid_item(int image, String name, Class activity) {

        this.image = image;
        Name = name;
        this.activity = activity;
    }
}
