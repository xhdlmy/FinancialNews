package com.aide.financial.fragment;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aide.financial.Constant;
import com.aide.financial.FinancialApplication;
import com.aide.financial.R;
import com.aide.financial.adapter.recycle.BaseRecyclerAdapter;
import com.aide.financial.adapter.recycle.BaseViewHolder;
import com.aide.financial.base.InfoFragment;
import com.aide.financial.net.retrofit.resp.GankData;
import com.aide.financial.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

/**
 * Created by Bruce on 2019/7/10.
 */

public class InfoWealFragment extends InfoFragment {

    private int mScreenWidth = ScreenUtils.getScreenWidth(FinancialApplication.getContext());

    @Override
    protected void initInfoParams() {
        mCategory = Constant.INFO_WEAL;
        mCount = Constant.COUNT_10;
        mRecycleLayoutResId = R.layout.item_recycler_info_weal;
        // 想要实现瀑布流
        mRecyclerManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected void createAdapter(List<GankData> list) {
        mAdapter = new BaseRecyclerAdapter<GankData>(mContext, mRecycleLayoutResId, list) {
            @Override
            protected void onBindData(BaseViewHolder holder, GankData data, int position) {
                final ImageView ivWeal = holder.getView(R.id.iv_weal);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivWeal.getLayoutParams();
                int ivWidth = params.width;
                Log.i(TAG, "imageview width " + ivWidth);
                Glide.with(mContext)
                        .asBitmap()
                        .load(data.url)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                float width = resource.getWidth();
                                float height = resource.getHeight();
                                Log.i(TAG, "width " + width);
                                Log.i(TAG, "height " + height);
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivWeal.getLayoutParams();
                                params.height = (int) ( (mScreenWidth/2) / (width/height));
                                Log.i(TAG, "imageview height " + params.height);
                                ivWeal.setLayoutParams(params);
                                ivWeal.setImageBitmap(resource);
                            }
                        });
            }
        };
    }
}
