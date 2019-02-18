package com.ubertest.common.image.utils;

import android.graphics.Bitmap;

import com.ubertest.common.image.ImageLoader;

public class Utils {
    public static Bitmap resize(Bitmap image, int w, int h) {
        if (h > 0 && w > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            if (width < w || height < h) {
                return image;
            }

            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) w / (float) h;

            int finalWidth = w;
            int finalHeight = h;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) h * ratioBitmap);
            } else {
                finalHeight = (int) ((float) w / ratioBitmap);
            }

            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public static String getKey(ImageLoader.Request request) {
        return request.url + "&width=" + request.view.getWidth()+ "&height=" + request.view.getHeight();
    }
}
