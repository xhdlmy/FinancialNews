package com.aide.financial.base.rx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * 管理 Activity 生命周期
 * 根据 RxLifecycle2 源码改编而来
 */

@SuppressLint("Registered")
public class RxActivity extends AppCompatActivity {

    protected final BehaviorSubject<ActivityLifecycleEvent> mSubject = BehaviorSubject.create();

    public <T> ObservableTransformer<T, T> bindUntilEvent(final ActivityLifecycleEvent event) {
        return upstream -> upstream.takeUntil(mSubject.filter(lifecycleEvent -> lifecycleEvent.equals(event)));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubject.onNext(ActivityLifecycleEvent.CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSubject.onNext(ActivityLifecycleEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSubject.onNext(ActivityLifecycleEvent.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSubject.onNext(ActivityLifecycleEvent.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSubject.onNext(ActivityLifecycleEvent.STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubject.onNext(ActivityLifecycleEvent.DESTORY);
    }

}
