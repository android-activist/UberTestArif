package com.ubertest.common;

import com.ubertest.common.network.ServerErrorModel;

import java.util.Map;

public abstract class BaseInteractor {
    public static final String BASE_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search";

    public boolean mDestroyed;

    public void stop() {
        mDestroyed = true;
    }

    public boolean isDead() {
        return mDestroyed;
    }

    public interface ErrorListener {
        void onError(int requestCode, ServerErrorModel error, Map<String, Object> extraData);
    }
}
