package com.aide.financial.base.rx;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;

public class RxFragment extends Fragment {

    protected final BehaviorSubject<FragmentLifecycleEvent> mSubject = BehaviorSubject.create();

    public <T> ObservableTransformer<T, T> bindUntilEvent(final FragmentLifecycleEvent event) {
        return upstream -> upstream.takeUntil(mSubject.filter(lifecycleEvent -> lifecycleEvent.equals(event)));
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        mSubject.onNext(FragmentLifecycleEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubject.onNext(FragmentLifecycleEvent.CREATE);
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubject.onNext(FragmentLifecycleEvent.CREATE_VIEW);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        mSubject.onNext(FragmentLifecycleEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        mSubject.onNext(FragmentLifecycleEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        mSubject.onNext(FragmentLifecycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        mSubject.onNext(FragmentLifecycleEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        mSubject.onNext(FragmentLifecycleEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        mSubject.onNext(FragmentLifecycleEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onDetach() {
        mSubject.onNext(FragmentLifecycleEvent.DETACH);
        super.onDetach();
    }

}
