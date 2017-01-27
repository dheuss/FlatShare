package com.flatshare.domain.interactors.auth.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.RegisterInteractor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterInteractorImpl extends AbstractAuthenticator implements RegisterInteractor {

    private static final String TAG = "RegisterInt";

    private RegisterDataType registerDataType;
    private RegisterInteractor.Callback mCallback;

    public RegisterInteractorImpl(MainThread mainThread,
                                    Callback callback, RegisterDataType registerDataType) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.registerDataType = registerDataType;
    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRegisterFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRegisterSuccess();
            }
        });
    }

    @Override
    public void execute() {

        String email = this.registerDataType.getEmail();
        String password = this.registerDataType.getPassword();

        if (!validateForm(email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            RegisterInteractorImpl.this.notifySuccess();
                        } else {
                            RegisterInteractorImpl.this.notifyError(task.getException().getMessage());
                        }
                    }
                });
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
//            mEmailField.setError("Required.");
            valid = false;
        } else {
//            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
//            mPasswordField.setError("Required.");
            valid = false;
        } else {
//            mPasswordField.setError(null);
        }

        return valid;
    }
}

