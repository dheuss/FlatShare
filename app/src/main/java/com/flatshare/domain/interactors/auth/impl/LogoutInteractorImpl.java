package com.flatshare.domain.interactors.auth.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.LogoutInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class LogoutInteractorImpl extends AbstractAuthenticator implements LogoutInteractor {

    private static final String TAG = "LogoutInt";


    private LogoutInteractor.Callback mCallback;

    public LogoutInteractorImpl(MainThread mainThread,
                                    Callback callback) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLogoutFail(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLogoutSuccess();
            }
        });
    }

    @Override
    public void execute() {
        //TODO: Abfrage ob man sich wirklich ausloggen möchte
        mAuth.signOut();
        notifySuccess();
    }
}
