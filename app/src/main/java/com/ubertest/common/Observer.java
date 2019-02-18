package com.ubertest.common;

public interface Observer<T> {

    void onSubscribe(Disposable disposable);

    void onUpdate(T t);
}
