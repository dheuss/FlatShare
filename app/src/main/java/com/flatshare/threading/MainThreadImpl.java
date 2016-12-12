package com.flatshare.threading;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.flatshare.domain.executor.MainThread;


/**
 * This class makes sure that the runnable we provide will be run on the main UI thread.
 */
public class MainThreadImpl implements MainThread {


    private static final String TAG = "MainThreadImpl";

    private static MainThread sMainThread;

    private Handler mHandler;

    private MainThreadImpl() {
        Log.d(TAG, "inside constructor, creating handler, mHandler = new Handler(Looper.getMainLooper());");
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {

        Log.d(TAG, "inside post(Runnable runnable) to post in the UI?");
        Log.d(TAG, "inside post(Runnable runnable), mHandler.post(runnable)");

        mHandler.post(runnable);
    }

    public static MainThread getInstance() {
        if (sMainThread == null) {
            sMainThread = new MainThreadImpl();
        }

        return sMainThread;
    }
}
