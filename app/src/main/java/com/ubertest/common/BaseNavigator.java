package com.ubertest.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class BaseNavigator {
    protected BaseActivity activity;
    protected IntentProvider intentProvider;

    public BaseNavigator(BaseActivity activity) {
        this.activity = activity;
        intentProvider = new IntentProvider(activity);
    }

    /**
     * Will provide current running activity
     *
     * @return current running activity
     */
    public BaseActivity getCurrentActivity() {
        return activity;
    }

    /**
     * Show toast
     *
     * @param message message to be shown
     */
    public void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show toast
     *
     * @param resId resource id for message to be shown
     */
    public void showToast(int resId) {
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * finish current running activity
     */
    public void finish() {
        activity.finish();
    }

    /**
     * finish current running activity with result OK
     */
    public void finishWithResultOK() {
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }

    public boolean isDead() {
        return activity == null || activity.isDead();
    }

    public void startActivity(Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * call back press of current running activity
     */
    public void backPressed(boolean superBack) {
        if (superBack) {
            ((Activity) activity).onBackPressed();
        }

        activity.onBackPressed();
    }

    public void showAlert(String msg, boolean finishActivity) {
        new AlertDialog.Builder(activity)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.onBackPressed();
                    }
                }).show();
    }
}
