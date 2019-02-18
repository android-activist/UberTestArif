package com.ubertest.common.network;

public class NetworkError {
    private Throwable error;

    public NetworkError(Throwable e) {
        this.error = e;
    }
}
