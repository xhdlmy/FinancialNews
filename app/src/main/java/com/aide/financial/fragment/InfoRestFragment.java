package com.aide.financial.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.aide.financial.Constant;
import com.aide.financial.R;
import com.aide.financial.adapter.recycle.BaseRecyclerAdapter;
import com.aide.financial.adapter.recycle.BaseViewHolder;
import com.aide.financial.base.InfoFragment;
import com.aide.financial.base.LazyPagerStateFragment;
import com.aide.financial.net.retrofit.resp.GankData;
import com.aide.financial.net.retrofit.resp.GankResp;
import com.aide.financial.util.LogUtils;
import com.aide.financial.widget.dialog.MToast;

import java.util.List;

/**
 * Created by Bruce on 2019/7/10.
 */

public class InfoRestFragment extends InfoFragment {

    private String TAG = "xhd";

    private boolean testFlag;
    private String testString;
    private GankResp mGankResp = new GankResp();

    @Override
    protected void initInfoParams() {
        mCategory = Constant.INFO_REST;
        mCount = Constant.COUNT_10;
        mRecycleLayoutResId = R.layout.item_recycler_info_all;
    }

    @Override
    protected void createAdapter(List<GankData> list) {
        mAdapter = new BaseRecyclerAdapter<GankData>(mContext, mRecycleLayoutResId, list) {
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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "fragment:" + this);
        LogUtils.i(TAG, "flag:" + testFlag);
        LogUtils.i(TAG, "string:" + testString);
        LogUtils.i(TAG, "gank:" + mGankResp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        testFlag = true;
        testString = "上一次字符串";
    }
}
