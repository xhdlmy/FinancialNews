package com.aide.financial.base;

import com.aide.financial.base.rx.RxActivity;
import com.aide.financial.base.rx.RxFragment;

public class BasePresenter<V> {

    public V mView;
    public RxActivity mActivity;
    public RxFragment mFragment;

    public BasePresenter(V view) {
        attachView(view);
        if(view instanceof RxActivity) mActivity = (RxActivity) view;
        if(view instanceof RxFragment){
            mFragment = (RxFragment) view;
            mActivity = (RxActivity) mFragment.getActivity();
        }
    }

    public void attachView(V mView) {
        this.mView = mView;
    }

    public void detachView() {
        this.mView = null;
    }

    public V getView(){
        return mView;
    }

}
