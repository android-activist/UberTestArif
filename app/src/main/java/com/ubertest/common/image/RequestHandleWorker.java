package com.ubertest.common.image;

public class RequestHandleWorker implements Runnable {
    private BaseRequestHandler handler;

    public RequestHandleWorker(BaseRequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.handleRequest();
    }
}
