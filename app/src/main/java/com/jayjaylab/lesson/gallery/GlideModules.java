package com.jayjaylab.lesson.gallery;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Homin on 2016-04-29.
 */
public class GlideModules implements GlideModule {
    final String TAG = GlideModules.class.getSimpleName();
    final int DISK_CACHE_SIZE = 1024 * 1024 * 250;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        Log.d(TAG, "applyOptions()");

        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (4.0 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (4.0 * defaultBitmapPoolSize);
        Log.d(TAG, "memoryCacheSize : " + customBitmapPoolSize +
                ", poolSize : " + customBitmapPoolSize);

        builder.setMemoryCache( new LruResourceCache( customMemoryCacheSize ));
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE));
        builder.setBitmapPool( new LruBitmapPool( customBitmapPoolSize ));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Log.d(TAG, "registerComponents()");
    }
}
