package com.flatshare.domain.interactors.auth.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.DeleteAccountInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class DeleteAccountInteractorImpl extends AbstractInteractor implements DeleteAccountInteractor {

    private static final String TAG = "DeleteAccountInt";


    private DeleteAccountInteractor.Callback mCallback;

    public DeleteAccountInteractorImpl(MainThread mainThread,
                                    Callback callback) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onDeleteAccountFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onDeleteAccountSuccess());
    }

    @Override
    public void execute() {
        //TODO:
    }
}

