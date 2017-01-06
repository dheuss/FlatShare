package com.flatshare.domain.interactors.auth.impl;

import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.ResetPasswordInteractor;


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

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(() -> mCallback.onResetPasswordFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(() -> mCallback.onResetPasswordSuccess());
    }

    @Override
    public void execute() {

        if (TextUtils.isEmpty(email)){
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       notifySuccess();
                   } else {
                       notifyError("Cant send Password reste email");
                   }
                });
    }
}
