package com.example.nikhil.sbihackathon.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by admin on 26-06-2016.
 */
public class CustomAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Datacard", "Mobile", "Dth"};

    public CustomAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new DatacardFragment();
        } else if (position == 1){
            return new MobileFragment();
        } else  {
            return new DtheFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}