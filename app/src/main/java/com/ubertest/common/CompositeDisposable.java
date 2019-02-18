package com.ubertest.common;

import java.util.ArrayList;
import java.util.List;

public class CompositeDisposable implements Disposable {
    private boolean disposed;
    private final List<Disposable> disposables = new ArrayList<>();

    public <T> boolean add(Disposable disposable) {
        if (disposed) return false;
        synchronized (this) {
            if (!disposed) {
                disposables.add(disposable);
                return true;
            }
        }
        return false;
    }

    public <T> boolean remove(Disposable disposable) {
        if (delete(disposable)) {
            disposable.dispose();
            return true;
        }
        return false;
    }

    public <T> boolean delete(Disposable disposable) {
        if (disposed) return false;
        synchronized (this) {
            if (!disposed) {
                disposables.remove(disposable);
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        if (disposed) return;

        synchronized (this) {
            if (!disposed) {
                disposed = true;
                for (Disposable disposable : disposables) {
                    disposable.dispose();
                }
                disposables.clear();
            }
        }

    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

}
