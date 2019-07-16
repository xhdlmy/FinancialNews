package com.aide.financial.base;

import com.aide.financial.net.retrofit.exception.ApiException;
import com.aide.financial.net.retrofit.resp.GankData;

import java.util.List;

public interface InfoView {

    void onGetInfoSuccess(List<GankData> list);
    void onGetInfoFailed(ApiException exception);
    void onLoadmoreSuccess(List<GankData> list);
    void onLoadmoreFailed(ApiException exception);

}
