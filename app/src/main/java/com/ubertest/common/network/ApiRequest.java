package com.ubertest.common.network;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class ApiRequest<T> extends Request<T> {
    private final Handler handler;

    private final ResponseListener<T> listener;
    private ThreadPoolExecutor threadPoolExecutor;

    public ApiRequest(String url, ResponseListener<T> listener) {
        super(url, listener);

        if (listener == null) {
            throw new NullPointerException("ResponseListener cannot be null");
        }

        this.listener = listener;

        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        handler = new Handler(Looper.getMainLooper());
    }

    public final synchronized void fetch() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                startRequest();
            }
        });
    }

    private void startRequest() {
        final T response = request();
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onResponse(ApiRequest.this, response);
            }
        });
    }

    @Override
    protected final void onError(final NetworkError error) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onErrorResponse(error);
            }
        });
    }
}
