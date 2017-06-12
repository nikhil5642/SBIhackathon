package com.example.nikhil.sbihackathon;

import android.app.Activity;

/**
 * Created by nikhil on 11/6/17.
 */

public class list_item_main {
    int icon;
    String title;
    Class activity;

    public list_item_main(int icon,String title,Class activity ){
        this.icon=icon;
        this.title=title;
        this.activity=activity;
    }
    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public Class getActivity() {
        return activity;
    }
}
