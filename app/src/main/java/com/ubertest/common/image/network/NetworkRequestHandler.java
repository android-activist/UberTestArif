package com.ubertest.common.image.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ubertest.common.image.BaseRequestHandler;
import com.ubertest.common.image.ImageLoader;
import com.ubertest.common.image.RequestHandlerListener;

import java.io.IOException;
import java.net.URL;

public class NetworkRequestHandler implements BaseRequestHandler {
    public ImageLoader.Request request;
    private RequestHandlerListener listener;

    public NetworkRequestHandler(ImageLoader.Request request, RequestHandlerListener listener) {
        this.request = request;
        this.listener = listener;
    }

    @Override
    public void handleRequest() {
        Bitmap bitmap = null;
        try {
            URL url = new URL(request.url);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            System.err.println(e);
        }

        listener.onRequestCompleted(request, bitmap);
    }
}
