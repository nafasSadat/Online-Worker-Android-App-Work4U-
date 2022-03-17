package com.example.Online_Worker_App;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WViewPagerAdaper extends FragmentPagerAdapter {


    public WViewPagerAdaper(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Worker_HomeFragment();
        } else if (position == 1) {
            return new CategoryFragment();
        } else {
            return new WorkerProfileFragment();
        }
    }




    @Override
    public int getCount() {
        return 3;
    }

}
