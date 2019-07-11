package com.aide.financial.adapter.viewpager;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aide.financial.base.LazyPagerStateFragment;

import java.util.List;

public class InfoPagerAdapter extends FragmentStatePagerAdapter {

    private List<LazyPagerStateFragment> mFragments;

    public InfoPagerAdapter(FragmentManager fm, @NonNull List<LazyPagerStateFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        if(mFragments == null) return null;
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        if (mFragments == null) return 0;
        return mFragments.size();
    }
}