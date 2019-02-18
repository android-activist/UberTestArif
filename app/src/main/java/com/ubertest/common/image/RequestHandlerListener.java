package com.ubertest.common.image;

import android.graphics.Bitmap;

public interface RequestHandlerListener {
    void onRequestCompleted(ImageLoader.Request request, Bitmap bitmap);
}