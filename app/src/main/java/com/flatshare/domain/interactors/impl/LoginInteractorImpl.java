package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.interactors.LoginInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class LoginInteractorImpl extends AbstractInteractor implements LoginInteractor {

    private static final String TAG = "LoginInt";

    private LoginDataType loginDataType;
    private LoginInteractor.Callback mCallback;

    public LoginInteractorImpl(MainThread mainThread,
                               Callback callback, LoginDataType loginDataType) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.loginDataType = loginDataType;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onLoginFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onLoginSuccess());
    }

    @Override
    public void execute() {
        //TODO: login
    }
}

