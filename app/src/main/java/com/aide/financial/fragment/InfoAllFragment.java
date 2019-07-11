package com.aide.financial.fragment;

import android.content.Context;

import com.aide.financial.Constant;
import com.aide.financial.R;
import com.aide.financial.base.InfoFragment;
import com.aide.financial.base.InfoPresenter;
import com.aide.financial.base.InfoView;
import com.aide.financial.base.LazyPagerStateFragment;
import com.aide.financial.net.retrofit.OnNextListener;
import com.aide.financial.net.retrofit.RxRequest;
import com.aide.financial.net.retrofit.exception.ApiException;
import com.aide.financial.net.retrofit.resp.GankData;
import com.aide.financial.net.retrofit.resp.GankResp;
import com.aide.financial.util.LogUtils;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bruce on 2019/7/10.
 */

public class InfoAllFragment extends InfoFragment {

    private int mPager = 1;
    private List<GankData> mResults;

    @Override
    protected int getResId() {
        return R.layout.fragment_info_all;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mPresenter.getGankData(Constant.INFO_ALL, Constant.POST_COUNT_10, mPager);
    }

    @Override
    public void onGetInfoSuccess(List<GankData> list) {

    }

    @Override
    public void onGetInfoFailed(String errorMsg) {

    }

    @Override
    public void onLoadmoreSuccess(List<GankData> list) {

    }

    @Override
    public void onLoadmoreFailed(String errorMsg) {

    }
}
