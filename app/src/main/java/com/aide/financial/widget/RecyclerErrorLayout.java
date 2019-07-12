package com.aide.financial.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.aide.financial.R;

/**
 * 组合式控件
 */

public class RecyclerErrorLayout extends RelativeLayout {

    private RecyclerView mRecyclerView;
    private RelativeLayout mRlError;
    private Button mBtnRetry;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public RecyclerErrorLayout(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerErrorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_pager_refresh_recyclerview, null, false);
        mSwipeRefreshLayout = inflate.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);
        mRecyclerView = inflate.findViewById(R.id.recycler_view);
        mRlError = inflate.findViewById(R.id.rl_error);
        mBtnRetry = inflate.findViewById(R.id.btn_retry);
        mRlError.setVisibility(GONE);
        addView(inflate);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout(){
        return mSwipeRefreshLayout;
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public Button getRetryButton(){
        return mBtnRetry;
    }

    public void showRecyclerView(){
        mSwipeRefreshLayout.setVisibility(VISIBLE);
        mRlError.setVisibility(GONE);
    }

    public void showErrorView(){
        mSwipeRefreshLayout.setVisibility(GONE);
        mRlError.setVisibility(VISIBLE);
    }

}
