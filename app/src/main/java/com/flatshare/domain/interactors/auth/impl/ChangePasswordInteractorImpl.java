package com.flatshare.domain.interactors.auth.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.ChangePasswordInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class ChangePasswordInteractorImpl extends AbstractAuthenticator implements ChangePasswordInteractor {

    private static final String TAG = "ChangePasswordInt";


    private ChangePasswordInteractor.Callback mCallback;

    private String newPassword;

    public ChangePasswordInteractorImpl(MainThread mainThread,
                                        Callback callback,
                                        String newPassword) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.newPassword = newPassword;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onChangePasswordFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onChangePasswordSuccess());
    }

    @Override
    public void execute() {
        if (userFirebase != null && !newPassword.equals("")){
            if (newPassword.length() < 6){
                notifyError("Password too short, enter minimum 6 chars");
            } else {
                userFirebase.updatePassword(newPassword)
                        .addOnCompleteListener(task -> {
                           if (task.isSuccessful()){
                             notifySuccess();
                           } else {
                               notifyError("Cant change Password");
                           }
                        });
                }
        } else if (newPassword.equals("")) {
            notifyError("Enter passsword");
        }
    }
}