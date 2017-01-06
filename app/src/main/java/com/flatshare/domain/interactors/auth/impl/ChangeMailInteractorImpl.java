package com.flatshare.domain.interactors.auth.impl;

import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.ChangeMailInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

public class ChangeMailInteractorImpl extends AbstractInteractor implements ChangeMailInteractor {

    private static final String TAG = "ChangeMailInt";

    private ChangeMailInteractor.Callback mCallback;
    private String newMailAddress;

    public ChangeMailInteractorImpl(MainThread mainThread,
                                          Callback callback,
                                    String newMailAddress) {
        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.newMailAddress = newMailAddress;
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
        String email = newMailAddress;
        if (TextUtils.isEmpty(email)){
            return;
        }

        userFirebase.updateEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.v(TAG, "changeMail:successful:" + task.isSuccessful());
                        notifySuccess();
                    } else {
                        Log.v(TAG, "chanageMail:failed:" + task.getException());
                        notifyError("logoutFailed");
                    }
                });
    }
}
