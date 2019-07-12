package com.aide.financial.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aide.financial.base.rx.RxFragment;
import com.aide.financial.util.LogUtils;

public abstract class BaseFragment extends RxFragment {

    protected Context mContext;
    protected BaseActivity mActivity; // 慎用
    protected BaseFragment mFragment;
    protected View mView;
    protected String TAG;

    // 开发过程中对 fragment 生命周期方法的 Log 研究
    private boolean lifecycleSwitch = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TAG = this.getClass().getSimpleName();
        if (lifecycleSwitch) LogUtils.i(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
        mActivity = (BaseActivity) getActivity();
        if (lifecycleSwitch) LogUtils.i(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (lifecycleSwitch) LogUtils.i(TAG, "onCreateView");
        // 一旦 Fragment 从回退栈（BackStack）中返回时，View 将会被销毁和重建
        if(mView == null) {
            mView = inflater.inflate(getResId(), null);
            initView();
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (lifecycleSwitch) LogUtils.i(TAG, "onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (lifecycleSwitch) LogUtils.i(TAG, "onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lifecycleSwitch) LogUtils.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (lifecycleSwitch) LogUtils.i(TAG, "onPause");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (lifecycleSwitch) LogUtils.i(TAG, "onHiddenChanged");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (lifecycleSwitch) LogUtils.i(TAG, "onSaveInstanceState");
        // Activity Home 或者 内存杀死
        // ViewPager 切换也会执行
    }

    @Override
    public void onStop() {
        super.onStop();
        if (lifecycleSwitch) LogUtils.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        if (lifecycleSwitch) LogUtils.i(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lifecycleSwitch) LogUtils.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (lifecycleSwitch) LogUtils.i(TAG, "onDetach");
    }

    protected abstract int getResId();
    public abstract void initView();

}
