package com.aide.financial.adapter.recycle.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class AlphaInAnimation implements BaseAnimation {
    private static final float DEFAULT_ALPHA_FROM = 0f;
    private final float mFrom;

    public AlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public AlphaInAnimation(float alphaFrom) {
        mFrom = alphaFrom;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }
}
