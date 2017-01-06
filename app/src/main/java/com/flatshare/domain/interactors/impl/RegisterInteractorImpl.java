package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.domain.interactors.RegisterInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class RegisterInteractorImpl extends AbstractInteractor implements RegisterInteractor {

    private static final String TAG = "RegisterInt";

    private RegisterDataType registerDataType;
    private RegisterInteractor.Callback mCallback;

    public RegisterInteractorImpl(MainThread mainThread,
                                    Callback callback, RegisterDataType registerDataType) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.registerDataType = registerDataType;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onRegisterFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onRegisterSuccess());
    }

    @Override
    public void execute() {
        //TODO: register
    }
}

