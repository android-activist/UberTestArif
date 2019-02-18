package com.ubertest.common;

public abstract class DisposableObserver<T> implements Disposable, Observer<T> {

    private Disposable disposable;

    @Override
    public void dispose() {
        disposable.dispose();
    }

    @Override
    public boolean isDisposed() {
        return disposable.isDisposed();
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }
}
