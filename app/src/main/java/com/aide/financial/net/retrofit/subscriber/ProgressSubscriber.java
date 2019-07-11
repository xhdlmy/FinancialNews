package com.aide.financial.net.retrofit.subscriber;


import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.aide.financial.R;
import com.aide.financial.base.ActivityCollector;
import com.aide.financial.base.rx.RxActivity;
import com.aide.financial.net.retrofit.OnNextListener;
import com.aide.financial.net.retrofit.RxRequest;
import com.aide.financial.net.retrofit.exception.ApiException;
import com.aide.financial.net.retrofit.exception.ERROR;
import com.aide.financial.net.retrofit.resp.GankResp;
import com.aide.financial.util.LogUtils;
import com.aide.financial.widget.dialog.MProgressDialog;
import com.aide.financial.widget.dialog.MToast;
import com.aide.financial.widget.dialog.config.MDialogConfig;
import com.aide.financial.widget.dialog.config.OnDialogCancelListener;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 全局控制网络请求的 ProgressBar
 * 默认每次请求都需要加载框显示，并且回退可以取消请求
 */

public class ProgressSubscriber implements Observer<GankResp> {

    private static final String TAG = ProgressSubscriber.class.getSimpleName();

    private Disposable mDisposable;

    private SoftReference<RxActivity> mActivitySoftReference;
    private SoftReference<OnNextListener<GankResp>> mOnNextListenerSoftReference;
    private RxActivity mActivity;
    private OnNextListener mOnNextListener;

    private MProgressDialog mProgressDialog;

    public ProgressSubscriber(@NonNull OnNextListener onNextListener){
        this(onNextListener, true);
    }

    public ProgressSubscriber(@NonNull OnNextListener onNextListener, boolean isNeedCancelDialog) {
        mActivitySoftReference = new SoftReference(ActivityCollector.getTopActivity());
        mOnNextListenerSoftReference = new SoftReference(onNextListener);
        mActivity = mActivitySoftReference.get();
        mOnNextListener = mOnNextListenerSoftReference.get();

        if (mActivity != null) {
            MDialogConfig config = new MDialogConfig.Builder()
                    .setCanceledOnBackKeyPressed(isNeedCancelDialog)
                    .setOnDialogCancelListener(new OnDialogCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            ProgressSubscriber.this.onCancel();
                        }
                    }).build();
            mProgressDialog = new MProgressDialog(mActivity, config);
        }
    }

    // 提供无需 Dialog 的请求过程
    public void setNoDialog(){
        mProgressDialog = null;
    }

    public void onCancel() {
        // 取消请求
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        // 统一 Toast
        if(mActivity != null) MToast.makeShort(mActivity, R.string.http_cancel);
        // 预留取消请求后各自所需处理接口
        if(mOnNextListener != null) mOnNextListener.onCancel();
        // Dialog 置空
        mProgressDialog = null;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        if(mProgressDialog != null  && !mProgressDialog.isShowing()) mProgressDialog.showProgress();
    }

    @Override
    public void onNext(GankResp value) {
        LogUtils.i(TAG, "onNext");
        if(mActivity == null || mOnNextListener == null) return;
        mOnNextListener.onNext(value);
    }

    @Override
    public void onComplete() {
        LogUtils.i(TAG, "onComplete");
        if(mProgressDialog != null) mProgressDialog.dismissProgress();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    
    @Override
    public void onError(Throwable e) {
        LogUtils.i(TAG, "onError");
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        /**
         * 统一处理 Exception
         */
        ApiException exception;
        if(e instanceof ApiException){
            exception = (ApiException) e;
        }else{
            exception = new ApiException(e, ERROR.UNKNOWN);
        }
        int code = exception.code;
        String message = exception.message;
        // 非协议约定错误
        if(code == ERROR.NO_NET_ERROR || code == ERROR.PARSE_ERROR || code == ERROR.UNKNOWN
                || code == ERROR.NETWORK_ERROR || code == ERROR.HTTP_ERROR
                || code == ERROR.JSON_ERROR){
            MToast.makeShort(mActivity, message);
            if(mOnNextListener != null) mOnNextListener.onError(exception);
        }

        if(mProgressDialog != null) mProgressDialog.dismissProgress();

    }

}
