package com.aide.financial.base.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aide.financial.R;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by Bruce on 2019/7/16.
 */

public class MyTarget extends CustomViewTarget<ImageView, Bitmap> {

    private int mWidth;

    public MyTarget(@NonNull ImageView view) {
        super(view);
    }

    public MyTarget(@NonNull ImageView view, int width) {
        super(view);
        mWidth = width;
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {
        view.setImageDrawable(placeholder);
    }


    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {

    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        float width = resource.getWidth();
        float height = resource.getHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        int margin = (int) view.getContext().getResources().getDimension(R.dimen.item_gank_drawable_padding);
        params.setMargins(margin, margin, margin, margin);
        params.width = mWidth - params.leftMargin - params.rightMargin;
        params.height = (int) ( params.width / (width / height));
        view.setLayoutParams(params);
        view.setImageBitmap(resource);
    }
}
