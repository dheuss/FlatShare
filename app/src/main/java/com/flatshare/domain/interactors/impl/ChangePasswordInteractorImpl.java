package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.ChangePasswordInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class ChangePasswordInteractorImpl extends AbstractInteractor implements ChangePasswordInteractor {

    private static final String TAG = "ChangePasswordInt";


    private ChangePasswordInteractor.Callback mCallback;

    public ChangePasswordInteractorImpl(MainThread mainThread,
                                        Callback callback) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onChangePasswordFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onChangePasswordSuccess());
    }

    @Override
    public void execute() {
        //TODO: changePassword
    }

}