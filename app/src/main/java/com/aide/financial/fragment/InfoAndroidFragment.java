package com.aide.financial.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.aide.financial.Constant;
import com.aide.financial.R;
import com.aide.financial.adapter.recycle.BaseRecyclerAdapter;
import com.aide.financial.adapter.recycle.BaseViewHolder;
import com.aide.financial.base.InfoFragment;
import com.aide.financial.base.LazyPagerStateFragment;
import com.aide.financial.net.retrofit.resp.GankData;
import com.aide.financial.widget.dialog.MToast;

import java.util.List;

/**
 * Created by Bruce on 2019/7/10.
 */

public class InfoAndroidFragment extends InfoFragment {

    private String mLabel = Constant.INFO_ANDROID;
    private int mCount = Constant.COUNT_10;

    @Override
    public void initView() {
        initRecyclerView(mLabel, mCount);
    }

    @Override
    public void initData() {
        mPresenter.getGankData(mLabel, mCount, mPager);
    }

    @Override
    public void onGetInfoSuccess(List<GankData> list) {
        if(mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);

        mPager++;
        if(mAdapter == null){
            // 第一次加载
            mLayout.showRecyclerView();
            createAdapter(list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setAdapter(mAdapter);
        }else{
            // 用户下拉刷新
            swipeRefresh(list, mCount);
        }

    }

    protected void createAdapter(List<GankData> list) {
        int recycleItemLayout = R.layout.item_recycler_info_all;
        mAdapter = new BaseRecyclerAdapter<GankData>(mContext, recycleItemLayout, list) {
            @Override
            protected void onBindData(BaseViewHolder holder, GankData data, int position) {
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvAuthor = holder.getView(R.id.tv_author);
                TextView tvCreateTime = holder.getView(R.id.tv_create_time);
                TextView tvLabel = holder.getView(R.id.tv_label);
                tvTitle.setText(data.desc);
                tvAuthor.setText(data.who);
                tvCreateTime.setText(data.createdAt.substring(2, 10));
                tvLabel.setText(data.type);
                holder.getConvertView().setOnClickListener(v -> {
                    MToast.makeShort(mContext, data.url);
                });
            }
        };
        // 如果第一次加载没有 Constant.COUNT_10 个数，则关闭更多加载；否则设置监听加载更多事件
        if(list.size() < mCount) {
            mAdapter.setLoadMoreEnable(false);
        }
        mAdapter.setOnLoadMoreListener(() -> mPresenter.getGankData(mLabel, mCount, mPager));
    }

}
