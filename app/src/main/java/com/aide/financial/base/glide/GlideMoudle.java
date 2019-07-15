package com.aide.financial.base.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.aide.financial.Constant;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

/**
 * Created by computer on 2018/6/13.
 * 清除磁盘缓存：GlideApp.get(mActivity).clearDiskCache();
 */
@GlideModule
public class GlideMoudle extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        // 设置缓存
        builder.setDiskCache(
                new DiskLruCacheFactory(
                        context.getExternalFilesDir(Constant.GLIDE_DIR).getAbsolutePath(),
                        Constant.GLIDE_CACHE_SIZE)
        );
    }

}
