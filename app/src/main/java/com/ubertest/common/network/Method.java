package com.ubertest.common.network;

public enum Method {
    GET("GET");

    public String type;

    Method(String requestType) {
        this.type = requestType;
    }

    public String getType() {
        return type;
    }
}
