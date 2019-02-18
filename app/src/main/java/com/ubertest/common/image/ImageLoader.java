package com.ubertest.common.image;

import android.widget.ImageView;

public class ImageLoader {
    private String imageUrl;
    private ImageView view;

    public ImageLoader(ImageView view) {
        this.view = view;
    }

    public ImageLoader url(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void fetch() {
        final Request request = new Request();
        request.url = imageUrl;
        request.view = view;

        RequestManager.getInstance().request(request);
    }

    public class Request {
        public String url;
        public ImageView view;
    }
}
