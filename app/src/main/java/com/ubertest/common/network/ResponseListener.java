package com.ubertest.common.network;

public interface ResponseListener<T> {
    void onResponse(Request<T> request, T response);

    void onErrorResponse(NetworkError error);
}
