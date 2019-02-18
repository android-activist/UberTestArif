package com.ubertest.common;

import java.util.ArrayList;
import java.util.Iterator;

public class SubjectObservable<T> {
    T currentValue;
    private final ArrayList<BehaviorDisposable<T>> behaviorDisposables = new ArrayList<>();

    public Disposable subscribe(Observer<T> observer) {
        if (observer == null) {
            return null;
        }

        BehaviorDisposable<T> bd = new BehaviorDisposable<>(observer, this);
        add(bd);
        observer.onSubscribe(bd);

        if(currentValue != null){
            observer.onUpdate(currentValue);
        }
        return bd;
    }

    public void unSubscribe(Observer<T> observer) {
        Iterator<BehaviorDisposable<T>> iterator = behaviorDisposables.iterator();
        BehaviorDisposable<T> next;
        while(iterator.hasNext()){
            next = iterator.next();
            if(next.observer.equals(observer)){
                iterator.remove();
            }
        }
    }

    private void add(BehaviorDisposable<T> behaviorDisposable) {
        behaviorDisposables.add(behaviorDisposable);
    }

    private void remove(BehaviorDisposable<T> behaviorDisposable) {
        behaviorDisposables.remove(behaviorDisposable);
    }

    public void notifyAllCallbacks(T t) {
        currentValue = t;
        ArrayList<BehaviorDisposable<T>> tempList = new ArrayList<>(this.behaviorDisposables);
        for(BehaviorDisposable<T> behaviorDisposable : tempList){
            if(!behaviorDisposable.isDisposed()){
                behaviorDisposable.observer.onUpdate(t);
            }
        }
    }

    public void unSubscribeAll() {
        behaviorDisposables.clear();
    }

    static final class BehaviorDisposable<T> implements Disposable {
        SubjectObservable<T> subject;
        Observer<T> observer;

        boolean disposed;

        BehaviorDisposable(Observer<T> observer, SubjectObservable<T> subject) {
            this.subject = subject;
            this.observer = observer;
        }

        @Override
        public void dispose() {
            if (!isDisposed()) {
                disposed = true;
                subject.remove(this);
            }
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }
    }
}
