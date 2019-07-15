package com.aide.financial.base.glide;

import android.annotation.SuppressLint;

import com.aide.financial.R;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * 自定义的操作
 */
@GlideExtension
public class MyExtension {

    private MyExtension(){}

    @SuppressLint("CheckResult")
    @GlideOption
    public static void trans(RequestOptions options){
        options.placeholder(R.drawable.glide_placeholder_shape)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

}
