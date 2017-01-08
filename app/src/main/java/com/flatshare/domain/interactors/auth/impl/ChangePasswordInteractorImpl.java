package com.flatshare.domain.interactors.auth.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.ChangePasswordInteractor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onChangePasswordFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onChangePasswordSuccess();
            }
        });
    }

    @Override
    public void execute() {
        if (userFirebase != null && !newPassword.equals("")){
            if (newPassword.length() < 6){
                notifyError("Password too short, enter minimum 6 chars");
            } else {
                userFirebase.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    ChangePasswordInteractorImpl.this.notifySuccess();
                                } else {
                                    ChangePasswordInteractorImpl.this.notifyError("Cant change Password");
                                }
                            }
                        });
                }
        } else if (newPassword.equals("")) {
            notifyError("Enter passsword");
        }
    }
}