package com.aide.financial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aide.financial.base.BaseFragment;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by Bruce on 2019/7/10.
 * Fragment 在 ViewPager 的生命周期由 FragmentPagerAdapter 托管
 */

public class InfoPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;

    public InfoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public InfoPagerAdapter(FragmentManager fm, @NonNull List<BaseFragment> fragments){
        this(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
