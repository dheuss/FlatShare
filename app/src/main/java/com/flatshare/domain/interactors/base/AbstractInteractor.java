package com.flatshare.domain.interactors.base;

import android.util.Log;

import com.flatshare.domain.executor.MainThread;

/**
 * This abstract class implements some common methods for all interactors. Cancelling an interactor, check if its running
 * and finishing an interactor.
 * Field methods are declared volatile as we might use these methods from different threads (mainly from UI).
 *
 * For example, when an activity is getting destroyed then we should probably cancel an interactor
 * but the request will come from the UI thread unless the request was specifically assigned to a background thread.
 *
 * Created by Arber on 06/12/2016.
 */
public abstract class AbstractInteractor implements Interactor {

    private static final String TAG = "AbstractInteractor";
    protected MainThread mMainThread;

    protected volatile boolean mIsCanceled;
    protected volatile boolean mIsRunning;

    public AbstractInteractor(MainThread mainThread) {

        Log.d(TAG, "inside constructor");
        mMainThread = mainThread;
    }

    public void cancel() {

        Log.d(TAG, "inside cancel()");

        mIsCanceled = true;
        mIsRunning = false;
    }

    public boolean isRunning() {

        Log.d(TAG, "inside isRunning");

        return mIsRunning;
    }

    public void onFinished() {
        Log.d(TAG, "inside onFinished()");

        mIsRunning = false;
        mIsCanceled = false;
    }

}
