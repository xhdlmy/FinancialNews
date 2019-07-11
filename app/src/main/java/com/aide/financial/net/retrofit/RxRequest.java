package com.aide.financial.net.retrofit;

import com.aide.financial.R;
import com.aide.financial.base.rx.ActivityLifecycleEvent;
import com.aide.financial.base.rx.FragmentLifecycleEvent;
import com.aide.financial.base.rx.RxActivity;
import com.aide.financial.base.rx.RxFragment;
import com.aide.financial.net.retrofit.exception.ERROR;
import com.aide.financial.net.retrofit.exception.ExceptionCategory;
import com.aide.financial.net.retrofit.exception.NoNetException;
import com.aide.financial.net.retrofit.exception.ProtocolException;
import com.aide.financial.net.retrofit.exception.RetryWhenNetworkException;
import com.aide.financial.net.retrofit.resp.GankResp;
import com.aide.financial.net.retrofit.subscriber.ProgressSubscriber;
import com.aide.financial.util.LogUtils;
import com.aide.financial.util.NetworkUtils;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Bruce on 2019/7/11.
 */

public class RxRequest {

    private static final String TAG = RxRequest.class.getSimpleName();
    private RxActivity mRxActivity;
    private RxFragment mRxFragment;
    private OnNextListener<GankResp> mListener;

    private boolean mIsNoDialog;

    public RxRequest(RxFragment rxFragment) {
        mRxFragment = rxFragment;
        mRxActivity = (RxActivity) mRxFragment.getActivity();
    }

    public RxRequest(RxFragment rxFragment, OnNextListener<GankResp> listener) {
        mRxFragment = rxFragment;
        mRxActivity = (RxActivity) mRxFragment.getActivity();
        mListener = listener;
    }

    // 在 post 之前调用
    public RxRequest withoutProgress(){
        mIsNoDialog = true;
        return this;
    }

    public RxRequest withProgress(boolean isNoDialog){
        mIsNoDialog = isNoDialog;
        return this;
    }

    public void post(String category, int count, int pager){
        if(mListener == null) return;
        Observable observable = getObservable(category, count, pager);
        ProgressSubscriber subscriber = new ProgressSubscriber(mListener);
        if(mIsNoDialog) subscriber.setNoDialog();
        observable.subscribe(subscriber);
    }

    public Observable<GankResp> getObservable(String category, int count, int pager){
        if(!NetworkUtils.isNetworkConnected()){
            return Observable.error(new NoNetException());
        }
        return RxRetrofit.getService().post(category, count, pager)
                .compose(this.handlerResp())
                .compose(this.handleError())
                .retryWhen(new RetryWhenNetworkException())
                .compose(this.handleLifecycle())
                .compose(this.handleThread());
    }

    private Gson mGson = new Gson();

    private ObservableTransformer<ResponseBody, GankResp> handlerResp() {
        return upstream -> upstream.map((Function<ResponseBody, GankResp>) responseBody -> {
            String string = responseBody.string();
            LogUtils.i(TAG, string);
            GankResp gankResp = mGson.fromJson(string, GankResp.class);
            if(gankResp.error){
                throw new ProtocolException(ERROR.JSON_ERROR, mRxActivity.getString(R.string.error_protocol_json_resp));
            }
            return gankResp;
        });
    }

    // 统一处理生命周期 activity or fragment
    private <T> ObservableTransformer<T, T> handleLifecycle() {
        if(mRxFragment == null){
            return mRxActivity.bindUntilEvent(ActivityLifecycleEvent.DESTORY);
        }else{
            return mRxFragment.bindUntilEvent(FragmentLifecycleEvent.DETACH);
        }
    }

    // 统一处理错误
    private <T> ObservableTransformer<T, T> handleError() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(final Observable<T> upstream) {
                return upstream.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                        LogUtils.i(TAG, "handleError:" + throwable);
                        return Observable.error(ExceptionCategory.handleException(throwable));
                    }
                });
            }
        };
    }

    // 统一处理线程切换

    /**
     subscribeOn() 的线程控制可以从事件发出的开端就造成影响 (只使用一次即可，跟顺序无关)
     observeOn() 控制的是它后面的线程
     */
    private <T> ObservableTransformer<T, T> handleThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
