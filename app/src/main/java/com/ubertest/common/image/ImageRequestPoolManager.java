package com.ubertest.common.image;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ImageRequestPoolManager {
    private static ImageRequestPoolManager instance = new ImageRequestPoolManager();

    private ThreadPoolExecutor poolExecutor;

    public ImageRequestPoolManager() {
        poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
    }

    public static ImageRequestPoolManager getInstance() {
        return instance;
    }

    public void execute(BaseRequestHandler requestHandler) {
        final RequestHandleWorker worker = new RequestHandleWorker(requestHandler);
        poolExecutor.execute(worker);
    }
}
