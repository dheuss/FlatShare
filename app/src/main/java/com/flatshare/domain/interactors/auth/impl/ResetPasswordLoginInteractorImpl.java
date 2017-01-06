package com.flatshare.domain.interactors.auth.impl;

import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.ResetPasswordLoginInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;


public class ResetPasswordLoginInteractorImpl extends AbstractInteractor implements ResetPasswordLoginInteractor {

    private static final String TAG = "ResetPasswordLoginInt";

    private ResetPasswordLoginInteractor.Callback mCallback;

    private String email;

    public ResetPasswordLoginInteractorImpl(MainThread mainThread,
                                       Callback callback,
                                       String email) {
        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.email = email;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(() -> mCallback.onResetPasswordLoginFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(() -> mCallback.onResetPasswordLoginSuccess());
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
