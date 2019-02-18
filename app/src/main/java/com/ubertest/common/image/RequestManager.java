package com.ubertest.common.image;

import android.graphics.Bitmap;

import com.ubertest.R;
import com.ubertest.common.image.cache.CacheManager;
import com.ubertest.common.image.cache.CacheRequestHandler;
import com.ubertest.common.image.network.NetworkRequestHandler;

public class RequestManager {
    private static RequestManager instance = new RequestManager();

    public static RequestManager getInstance() {
        return instance;
    }


    public void request(ImageLoader.Request request) {
        request.view.setImageResource(R.mipmap.ic_launcher);
        request.view.setTag(request.url);

        requestFromCache(request);
    }

    private void requestFromCache(final ImageLoader.Request request) {
        BaseRequestHandler handler = new CacheRequestHandler(request, new RequestHandlerListener() {
            @Override
            public void onRequestCompleted(ImageLoader.Request request, Bitmap bitmap) {
                if (bitmap == null) {
                    requestFromNetwork(request);
                } else {
                    ViewUtils.setImage(request, bitmap);
                }
            }
        });

        ImageRequestPoolManager.getInstance().execute(handler);
    }

    private void requestFromNetwork(final ImageLoader.Request request) {
        BaseRequestHandler handler = new NetworkRequestHandler(request, new RequestHandlerListener() {
            @Override
            public void onRequestCompleted(ImageLoader.Request request, Bitmap bitmap) {
                if (bitmap != null) {
                    CacheManager.getInstance().add(request, bitmap);

                    ViewUtils.setImage(request, bitmap);
                }
            }
        });

        NetworkRequestPoolManager.getInstance().execute(handler);
    }
}
