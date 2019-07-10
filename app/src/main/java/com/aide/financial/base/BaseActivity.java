package com.aide.financial.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aide.financial.base.rx.RxActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends RxActivity {

    protected Context mContext;
    protected BaseActivity mActivity;
    protected String TAG;
    private Unbinder mButterKnifeBind;
    protected Intent mIntent;
    protected Bundle mBundle;
    protected Dialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        setContentView(getResId());
        mButterKnifeBind = ButterKnife.bind(this);
        TAG = this.getClass().getSimpleName();
        mIntent = getIntent();
        mBundle = mIntent.getExtras();
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mButterKnifeBind != null) mButterKnifeBind.unbind();
        if(mProgressDialog != null) mProgressDialog.dismiss();
        mContext = null;
        mActivity = null;
    }

    public void setMProgressDialog(Dialog progressDialog){
        mProgressDialog = progressDialog;
    }

    protected abstract int getResId();
    public abstract void initData();

}
