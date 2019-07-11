package com.aide.financial.base;

import com.aide.financial.base.rx.RxActivity;
import com.aide.financial.base.rx.RxFragment;
import com.aide.financial.net.retrofit.OnNextListener;
import com.aide.financial.net.retrofit.RxRequest;
import com.aide.financial.net.retrofit.exception.ApiException;
import com.aide.financial.net.retrofit.resp.GankResp;

public class InfoPresenter extends BasePresenter<InfoView> {

    public InfoPresenter(InfoView view) {
        super(view);
    }

    public void getGankData(String category, int count, int pager){
        if(pager <= 0) {
            mView.onGetInfoFailed("pager must from 1 start");
        }
        new RxRequest(mFragment, new OnNextListener<GankResp>() {
            @Override
            public void onNext(GankResp gankResp) {
                if(pager == 1){
                    mView.onGetInfoSuccess(gankResp.results);
                }else{
                    mView.onLoadmoreSuccess(gankResp.results);
                }
            }

            @Override
            public void onError(ApiException e) {
                super.onError(e);
                if(e.message == null) e.message = "load gank msg failed";
                if(pager == 1){
                    mView.onGetInfoFailed(e.message);
                }else{
                    mView.onLoadmoreFailed(e.message);
                }
            }
        }).post(category, count, pager);
    }

}
