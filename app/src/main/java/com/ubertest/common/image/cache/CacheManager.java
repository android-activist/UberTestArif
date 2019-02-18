package com.ubertest.common.image.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.ubertest.common.image.ImageLoader;
import com.ubertest.common.image.utils.Utils;

public class CacheManager {
    private static CacheManager instance = new CacheManager();

    private LruCache<String, Bitmap> mMemoryCache;

    public static CacheManager getInstance() {
        return instance;
    }

    public CacheManager() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void add(String key, Bitmap bitmap) {
        if (get(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public void add(ImageLoader.Request request, Bitmap bitmap) {
        bitmap = Utils.resize(bitmap, request.view.getWidth(), request.view.getHeight());

        final String key = Utils.getKey(request);

        if (get(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap get(String key) {
        return mMemoryCache.get(key);
    }
}
