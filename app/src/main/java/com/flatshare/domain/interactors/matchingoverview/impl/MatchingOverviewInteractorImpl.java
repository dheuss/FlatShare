package com.flatshare.domain.interactors.matchingoverview.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matchingoverview.MatchingOverviewInteractor;
/**
 * Created by david on 06/12/2016.
 */

public class MatchingOverviewInteractorImpl extends AbstractInteractor implements MatchingOverviewInteractor {

    private static final String TAG = "MatchingOverviewInteractorImpl";

    private MatchingOverviewInteractor.Callback mCallback;

    public MatchingOverviewInteractorImpl(MainThread mainThread, Callback callback, String apartmentProfileId) {
        super(mainThread);
        this.mCallback = callback;
    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentSuccess();
            }
        });
    }

    @Override
    public void execute() {
        Log.v(TAG, "execute methode called in MatchingOverviewInteractorImpl");

        notifySuccess();
    }
}
