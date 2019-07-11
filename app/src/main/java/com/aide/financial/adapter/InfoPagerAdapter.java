package com.aide.financial.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.aide.financial.base.LazyPagerStateFragment;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by Bruce on 2019/7/10.
 * Fragment 在 ViewPager 的生命周期由 PagerAdapter 托管
 */

public class InfoPagerAdapter extends FragmentStatePagerAdapter {

    private List<LazyPagerStateFragment> mFragments;

    private InfoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public InfoPagerAdapter(FragmentManager fm, @NonNull List<LazyPagerStateFragment> fragments){
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
