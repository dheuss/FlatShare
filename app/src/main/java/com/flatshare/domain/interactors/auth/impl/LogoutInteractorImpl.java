package com.flatshare.domain.interactors.auth.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.LogoutInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class LogoutInteractorImpl extends AbstractInteractor implements LogoutInteractor {

    private static final String TAG = "LogoutInt";


    private LogoutInteractor.Callback mCallback;

    public LogoutInteractorImpl(MainThread mainThread,
                                    Callback callback) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onLogoutFail(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onLogoutSuccess());
    }

    @Override
    public void execute() {
        //TODO: Abfrage ob man sich wirklich ausloggen möchte
        mAuth.signOut();
        notifySuccess();
    }
}
