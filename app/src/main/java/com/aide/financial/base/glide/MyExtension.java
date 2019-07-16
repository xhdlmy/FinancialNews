package com.aide.financial.base.glide;

import android.support.annotation.NonNull;

import com.aide.financial.R;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;


/**
 * Created by Bruce on 2019/7/16.
 */

@GlideExtension
public class MyExtension {

    private MyExtension(){};

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> trans(BaseRequestOptions<?> options) {
        return options
                .placeholder(R.drawable.glide_placeholder_shape)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

}
