package com.ubertest.common.image.cache;

import android.graphics.Bitmap;

import com.ubertest.common.image.BaseRequestHandler;
import com.ubertest.common.image.ImageLoader;
import com.ubertest.common.image.RequestHandlerListener;
import com.ubertest.common.image.utils.Utils;

public class CacheRequestHandler implements BaseRequestHandler {
    public ImageLoader.Request request;
    private RequestHandlerListener listener;

    public CacheRequestHandler(ImageLoader.Request request, RequestHandlerListener listener) {
        this.request = request;
        this.listener = listener;
    }

    @Override
    public void handleRequest() {
        final String key = Utils.getKey(request);
        Bitmap bmp = CacheManager.getInstance().get(key);

        if (bmp == null) {
            bmp = CacheManager.getInstance().get(request.url);
        }

        listener.onRequestCompleted(request, bmp);
    }
}
