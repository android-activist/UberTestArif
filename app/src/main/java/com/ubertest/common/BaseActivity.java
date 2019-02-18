package com.ubertest.common;

import android.app.AlertDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BaseActivity getActivity() {
        return this;
    }

    public String getRequestTag() {
        return this.getClass().getSimpleName() + this.hashCode();
    }

    public boolean isDead() {
        return isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed());
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void disposeDisposables() {
        compositeDisposable.dispose();
    }

    public <T extends Presenter> T getPresenter() {
        return null;
    }

    public void showAlertDialog(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}
