package com.flatshare.domain.interactors.auth.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.ResetPasswordInteractor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class ResetPasswordInteractorImpl extends AbstractAuthenticator implements ResetPasswordInteractor {

    private static final String TAG = "ResetPasswordInt";

    private ResetPasswordInteractor.Callback mCallback;

    private String email;

    public ResetPasswordInteractorImpl(MainThread mainThread,
                                       Callback callback,
                                       String email) {
        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.email = email;
    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onResetPasswordFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onResetPasswordSuccess();
            }
        });
    }

    @Override
    public void execute() {

        if (TextUtils.isEmpty(email)){
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ResetPasswordInteractorImpl.this.notifySuccess();
                        } else {
                            ResetPasswordInteractorImpl.this.notifyError("Cant send Password reste email");
                        }
                    }
                });
    }
}
