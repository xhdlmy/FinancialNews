package com.aide.financial.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aide.financial.util.LogUtils;

public abstract class InfoFragment extends LazyPagerStateFragment implements InfoView {

    protected InfoPresenter mPresenter = new InfoPresenter(this);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
