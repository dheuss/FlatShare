package com.flatshare.presentation.presenters.base;


import android.util.Log;

import com.flatshare.domain.MainThread;


/**
 * This is a base class for all presenters which are communicating with interactors. This base class will hold a
 * reference to the Executor and MainThread objects that are needed for running interactors in a background thread.
 */
public abstract class AbstractPresenter {

    private static final String TAG = "AbstractPresenter";

    protected MainThread mMainThread;

    public AbstractPresenter(MainThread mainThread) {

        mMainThread = mainThread;
    }
}
