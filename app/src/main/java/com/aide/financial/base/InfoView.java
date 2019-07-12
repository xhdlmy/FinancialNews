package com.aide.financial.base;

import com.aide.financial.net.retrofit.resp.GankData;

import java.util.List;

/**
 * Created by xhd on 2019/7/11.
 */

public interface InfoView {

    void onGetInfoSuccess(List<GankData> list);
    void onGetInfoFailed(String errorMsg);
    void onLoadmoreSuccess(List<GankData> list);
    void onLoadmoreFailed(String errorMsg);

}