package com.onlineshoppingmall;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mlist;
    public MyPagerAdapter(FragmentManager fm, List<Fragment>list) {
        super(fm);
        mlist=list;
    }

    @Override
    public Fragment getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
