package com.ubertest.common;

public interface LifecycleView extends MvpView {

    void onCreate(BaseActivity activity);

    void onDestroy(BaseActivity activity);
}
