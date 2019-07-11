package com.aide.financial.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aide.financial.util.LogUtils;

public abstract class LazyPagerStateFragment extends BaseFragment {

    protected boolean mIsViewCreated;
    protected boolean mIsFirstLoadData;

    // 调用时机：不能保证这个方法与Fragment生命周期的排序。
    // 最初会被调用两次
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        LogUtils.i(TAG, "setUserVisibleHint " + isVisibleToUser);
        requestData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsViewCreated = true;
        requestData();
    }

    /**
     *  加载数据的时机：setUserVisibleHint() or onViewCreated() 不知道哪个先调用，
     *                  但是只要调用过一次，mIsFirstLoadData = true，不是每次更新的情况下，只调用一次
     * @param isNeedUpdateEveryTime 是否需要每次都去更新数据
     * @return 返回值代表是否加载数据
     */
    public boolean requestData(boolean isNeedUpdateEveryTime){
        if(getUserVisibleHint() && mIsViewCreated && (!mIsFirstLoadData || isNeedUpdateEveryTime)){
            LogUtils.i(TAG, "initData");
            initData();
            mIsFirstLoadData = true;
            return true;
        }
        return false;
    }

    public boolean requestData(){
        return requestData(false);
    }

    public abstract void initData();

}
