package com.aide.financial.activity;

import android.support.annotation.IntegerRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.aide.financial.R;
import com.aide.financial.adapter.viewpager.InfoPagerAdapter;
import com.aide.financial.base.BaseActivity;
import com.aide.financial.base.LazyPagerStateFragment;
import com.aide.financial.fragment.InfoAllFragment;
import com.aide.financial.fragment.InfoAndroidFragment;
import com.aide.financial.fragment.InfoExpandFragment;
import com.aide.financial.fragment.InfoIosFragment;
import com.aide.financial.fragment.InfoWealFragment;
import com.aide.financial.fragment.InfoWebFragment;

import java.util.Arrays;

import butterknife.BindView;

public class InfoActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private @IntegerRes Integer[] mResTabTitles = {
        R.string.info_all,
            R.string.info_weal,
        R.string.info_web,
        R.string.info_android,
        R.string.info_ios,
        R.string.info_expands,
    };

    private LazyPagerStateFragment[] mFragments = {
      new InfoAllFragment(),
      new InfoWealFragment(),
      new InfoWebFragment(),
      new InfoAndroidFragment(),
      new InfoIosFragment(),
      new InfoExpandFragment(),
    };

    @Override
    protected int getResId() {
        return R.layout.activity_info;
    }

    @Override
    public void initData() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (Integer resId : mResTabTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(resId));
        }
        mViewPager.setAdapter(new InfoPagerAdapter(getSupportFragmentManager(), Arrays.asList(mFragments)));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }
}
