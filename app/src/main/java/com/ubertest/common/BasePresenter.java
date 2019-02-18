package com.ubertest.common;

import android.support.annotation.CallSuper;

import java.util.HashMap;

public abstract class BasePresenter implements Presenter {
    private HashMap<String, SubjectObservable<? extends ViewModel>> observablesMap = new HashMap<>();

    public boolean mDestroyed;

    @Override
    public void start() {
        mDestroyed = false;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @CallSuper
    @Override
    public void stop() {
        mDestroyed = true;
    }

    @Override
    public boolean isDead() {
        return mDestroyed;
    }

    private <T extends ViewModel> SubjectObservable<T> getObservables(String type) {
        return getOrCreateObservables(type);
    }

    private <T extends ViewModel> SubjectObservable<T> getOrCreateObservables(String type) {
        SubjectObservable<T> subjectObservable = (SubjectObservable<T>) observablesMap.get(type);
        if (subjectObservable == null) {
            subjectObservable = new SubjectObservable<>();
            observablesMap.put(type, subjectObservable);
        }

        return subjectObservable;
    }

    protected  <T extends ViewModel> Disposable subscribe(String type, Observer<T> observer) {
        SubjectObservable<ViewModel> observables = getOrCreateObservables(type);
        return observables.subscribe((Observer<ViewModel>) observer);
    }

    protected <T extends ViewModel> void unSubscribe(String type, Observer<T> observer) {
        SubjectObservable<ViewModel> observables = getObservables(type);
        if (observables != null) {
            observables.unSubscribe((Observer<ViewModel>) observer);
        }
    }

    protected <T extends ViewModel> void notifyAllCallbacks(String type, T t) {
        SubjectObservable<ViewModel> observables = getObservables(type);
        if (observables != null) {
            observables.notifyAllCallbacks(t);
        }
    }

    protected void unSubscribeAll(String type) {
        SubjectObservable<ViewModel> observables = getObservables(type);
        if (observables != null) {
            observables.unSubscribeAll();
        }
    }
}
