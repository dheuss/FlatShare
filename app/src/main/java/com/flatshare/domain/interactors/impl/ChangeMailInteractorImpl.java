package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.ChangeMailInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class ChangeMailInteractorImpl extends AbstractInteractor implements ChangeMailInteractor {


    private static final String TAG = "ChangeMailInt";


    private ChangeMailInteractor.Callback mCallback;

    public ChangeMailInteractorImpl(MainThread mainThread,
                                          Callback callback) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onChangeMailFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onChangeMailSuccess());
    }

    @Override
    public void execute() {
        //TODO: changeMail
    }
}
