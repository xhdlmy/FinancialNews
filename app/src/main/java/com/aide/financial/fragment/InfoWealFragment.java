package com.aide.financial.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.aide.financial.Constant;
import com.aide.financial.FinancialApplication;
import com.aide.financial.R;
import com.aide.financial.adapter.recycle.BaseRecyclerAdapter;
import com.aide.financial.adapter.recycle.BaseViewHolder;
import com.aide.financial.base.InfoFragment;
import com.aide.financial.base.glide.GlideApp;
import com.aide.financial.base.glide.MyTarget;
import com.aide.financial.net.retrofit.resp.GankData;
import com.aide.financial.util.ScreenUtils;

import java.util.List;

/**
 * Created by Bruce on 2019/7/10.
 * 需要解决的问题：
 * 1. ImageView 的宽高开始不确定，为 0，然后 RecycleView 一直加载，直到 Glide 加载出图片；
 *      解决：预先给 ImageView 一个预加载的高度，例如320dp
 * 2. Glide 获取图片内容，得到图片宽高，如何规定 ImageView 的宽高；
 *          Glide 设置 SimpleTarget<Bitmap> 通过 LayoutParam 设置宽高
 * 3. 如何获取图片的大小，以及压缩图片，不至于图片占用过多内存；
 * 4. 如何打印出图片是网络获取还是缓存中获取；
 */

public class InfoWealFragment extends InfoFragment {

    private int mScreenWidth = ScreenUtils.getScreenWidth(FinancialApplication.getContext());
    private int mSpanCount = 2;

    @Override
    protected void initInfoParams() {
        mCategory = Constant.INFO_WEAL;
        mCount = Constant.COUNT_10;
        mRecycleLayoutResId = R.layout.item_recycler_info_weal;
        // 想要实现瀑布流
        mRecyclerManager = new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected void createAdapter(List<GankData> list) {
        mAdapter = new BaseRecyclerAdapter<GankData>(mContext, mRecycleLayoutResId, list) {
            @Override
            protected void onBindData(BaseViewHolder holder, GankData data, int position) {
                final ImageView ivWeal = holder.getView(R.id.iv_weal);
                GlideApp.with(mFragment)
                        .asBitmap()
                        .trans()
                        .load(data.url)
                        .into(new MyTarget(ivWeal, mScreenWidth / mSpanCount));
            }
        };
    }
}
