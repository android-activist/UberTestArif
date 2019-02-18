package com.ubertest.common;

public interface Presenter {
    void start();
    void stop();
    void resume();
    void pause();
    boolean isDead();
}
