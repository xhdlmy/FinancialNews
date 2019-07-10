package com.aide.financial.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aide.financial.base.rx.RxFragment;
import com.aide.financial.util.LogUtils;

public abstract class BaseFragment extends RxFragment {

    protected BaseActivity mActivity;
    protected View mView;
    protected String TAG;
    protected Bundle mBundle;

    protected boolean mIsInitData;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TAG = this.getClass().getSimpleName();
        LogUtils.i(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        mActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "onCreateView");
        if(mView == null) {
            mView = inflater.inflate(getResId(), null);
            initView();
        }
        // 初始化数据并与视图绑定
        if(!mIsInitData) {
            mBundle = getArguments();
            initData();
            LogUtils.i(TAG, "initData");
        }
        mIsInitData = true;
        // 更新数据
        refreshData();
        return mView;
    }

    // ViewPager 切换时还是会执行，说明又会走一遍 initData()
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        LogUtils.i(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.i(TAG, "onDetach");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.i(TAG, "onSaveInstanceState");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){
            LogUtils.i(TAG, "onHiddenChanged onFragmentPause");
            onFragmentPause();
        }else{
            LogUtils.i(TAG, "onHiddenChanged onFragmentResume");
            onFragmentResume();
        }
        super.onHiddenChanged(hidden);
    }

    public void onFragmentResume(){}
    public void onFragmentPause(){}

    protected abstract int getResId();
    public abstract void initView();
    public abstract void initData();
    public abstract void refreshData();

}
