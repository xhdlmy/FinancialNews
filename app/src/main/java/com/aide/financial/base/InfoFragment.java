package com.aide.financial.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aide.financial.R;
import com.aide.financial.adapter.recycle.BaseRecyclerAdapter;
import com.aide.financial.net.retrofit.exception.ApiException;
import com.aide.financial.net.retrofit.resp.GankData;
import com.aide.financial.util.LogUtils;
import com.aide.financial.widget.RecyclerErrorLayout;

import java.util.List;

public abstract class InfoFragment extends LazyPagerStateFragment implements InfoView {

    protected RecyclerErrorLayout mLayout;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;

    protected RecyclerView.LayoutManager mRecyclerManager;

    protected int mPager = 1;
    protected String mCategory;
    protected int mCount;
    protected @LayoutRes int mRecycleLayoutResId;
    protected BaseRecyclerAdapter<GankData> mAdapter;
    protected InfoPresenter mPresenter = new InfoPresenter(this);

    protected abstract void initInfoParams();
    protected abstract void createAdapter(List<GankData> list);

    protected void customParams() {
        mRecyclerManager = new LinearLayoutManager(mContext);
        initInfoParams();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止切换 Tab onDestroy 时， mPresenter 重置为 null
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_info_all;
    }

    @Override
    public void initView() {
        customParams();
        mLayout = mView.findViewById(R.id.recycler_error_layout);
        mSwipeRefreshLayout = mLayout.getSwipeRefreshLayout();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPager = 1;
            mPresenter.getGankData(mCategory, mCount, mPager);
        });
        mRecyclerView = mLayout.getRecyclerView();
        mLayout.getRetryButton().setOnClickListener(v -> {
            mPager = 1;
            mPresenter.getGankData(mCategory, mCount, mPager);
        });
    }

    @Override
    public void initData() {
        mPresenter.getGankData(mCategory, mCount, mPager);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onGetInfoSuccess(List<GankData> list) {
        LogUtils.i(TAG, "onGetInfoSuccess");
        // 只有在数据获取成功后，才不用 initData();
        mIsFirstLoadData = true;

        if(mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);

        mPager++;
        if(mAdapter == null){
            // 第一次加载
            mLayout.showRecyclerView();
            createAdapter(list);
            // 如果第一次加载没有 Constant.COUNT_10 个数，则关闭更多加载；否则设置监听加载更多事件
            if(list.size() < mCount) {
                mAdapter.setLoadMoreEnable(false);
            }
            mAdapter.setOnLoadMoreListener(() -> mPresenter.getGankData(mCategory, mCount, mPager));

            mRecyclerView.setLayoutManager(mRecyclerManager);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            // 用户下拉刷新
            mAdapter.setDatas(list);
            if (list.size() < mCount){
                mAdapter.setLoadMoreEnable(false); // 不然 pager 不增加，会一直加载
            }else{
                mAdapter.loadComplete();
            }
        }
    }

    @Override
    public void onGetInfoFailed(ApiException exception) {
        mLayout.showErrorView();
    }

    @Override
    public void onLoadmoreSuccess(List<GankData> list) {
        if(mAdapter == null) return;
        // 加载更多
        if(list != null && list.size() != 0) {
            mPager++;
            mAdapter.addDatas(list);
            mAdapter.loadComplete();
        }else{
            mAdapter.loadEnd();
        }
    }

    @Override
    public void onLoadmoreFailed(ApiException exception) {
        if(mAdapter == null) return;
        mAdapter.loadFail();
    }
    
}
