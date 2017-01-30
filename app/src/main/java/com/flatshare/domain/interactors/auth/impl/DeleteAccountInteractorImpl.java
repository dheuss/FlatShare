package com.flatshare.domain.interactors.auth.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.DeleteAccountInteractor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Dvid on 06/01/2017.
 */

public class DeleteAccountInteractorImpl extends AbstractAuthenticator implements DeleteAccountInteractor {

    private static final String TAG = "DeleteAccountInt";


    private DeleteAccountInteractor.Callback mCallback;

    public DeleteAccountInteractorImpl(MainThread mainThread,
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
                mCallback.onDeleteAccountFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onDeleteAccountSuccess();
            }
        });
    }

    @Override
    public void execute() {
        if(firebaserUserId != null){
            firebaserUserId.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.v(TAG, "deleteAccount:successful:" + task.isSuccessful());
                                DeleteAccountInteractorImpl.this.notifySuccess();
                            } else {
                                Log.v(TAG, "deleteAccount:failed:" + task.getException());
                                DeleteAccountInteractorImpl.this.notifyError("deleteAccountFailed");
                            }
                        }
                    });
        }
    }
}

