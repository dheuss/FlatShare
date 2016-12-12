package com.flatshare.presentation.presenters.base;


import android.util.Log;

import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;


/**
 * This is a base class for all presenters which are communicating with interactors. This base class will hold a
 * reference to the Executor and MainThread objects that are needed for running interactors in a background thread.
 */
public abstract class AbstractPresenter {

    private static final String TAG = "AbstractPresenter";

    protected Executor mExecutor;
    protected MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {

        Log.d(TAG, "inside constructor!");

        mExecutor = executor;
        mMainThread = mainThread;
    }
}
