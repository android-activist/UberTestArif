package com.ubertest.common.image;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NetworkRequestPoolManager {
    private static ImageRequestPoolManager instance = new ImageRequestPoolManager();

    private ThreadPoolExecutor poolExecutor;

    public NetworkRequestPoolManager() {
        final BlockingQueue queue = new LinkedBlockingQueue();
        poolExecutor = new ThreadPoolExecutor(8, 8,
                0L, TimeUnit.MILLISECONDS, queue);
    }

    public static ImageRequestPoolManager getInstance() {
        return instance;
    }

    public void execute(BaseRequestHandler requestHandler) {
        final RequestHandleWorker worker = new RequestHandleWorker(requestHandler);
        poolExecutor.execute(worker);
    }
}