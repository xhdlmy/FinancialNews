package com.aide.financial.activity;

import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.aide.financial.R;
import com.aide.financial.adapter.InfoPagerAdapter;
import com.aide.financial.base.BaseActivity;
import com.aide.financial.base.BaseFragment;
import com.aide.financial.fragment.InfoAllFragment;
import com.aide.financial.fragment.InfoAndroidFragment;
import com.aide.financial.fragment.InfoExpandFragment;
import com.aide.financial.fragment.InfoIosFragment;
import com.aide.financial.fragment.InfoRestFragment;
import com.aide.financial.fragment.InfoWealFragment;
import com.aide.financial.fragment.InfoWebFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class InfoActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private @IntegerRes Integer[] mResTabTitles = {
        R.string.info_all,
        R.string.info_web,
        R.string.info_android,
        R.string.info_ios,
        R.string.info_rest,
        R.string.info_expands,
        R.string.info_weal,
    };

    private BaseFragment[] mFragments = {
      new InfoAllFragment(),
      new InfoWebFragment(),
      new InfoAndroidFragment(),
      new InfoIosFragment(),
      new InfoRestFragment(),
      new InfoExpandFragment(),
      new InfoWealFragment(),
    };

    @Override
    protected int getResId() {
        return R.layout.activity_info;
    }

    @Override
    public void initData() {
        for (Integer resId : mResTabTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(resId));
        }
        mViewPager.setAdapter(new InfoPagerAdapter(getSupportFragmentManager(), Arrays.asList(mFragments)));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
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
    }
}
