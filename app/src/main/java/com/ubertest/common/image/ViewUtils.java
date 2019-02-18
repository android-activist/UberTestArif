package com.ubertest.common.image;

import android.graphics.Bitmap;

public class ViewUtils {
    public static void setImage(final ImageLoader.Request request, final Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }

        Object tag = request.view.getTag();
        if (!request.url.equals(tag)) {
            return;
        }

        request.view.post(new Runnable() {
            @Override
            public void run() {
                request.view.setImageBitmap(bitmap);
            }
        });
    }
}
