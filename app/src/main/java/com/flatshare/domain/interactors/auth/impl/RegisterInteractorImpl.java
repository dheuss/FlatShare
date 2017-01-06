package com.flatshare.domain.interactors.auth.impl;

import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.RegisterInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

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

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onRegisterFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onRegisterSuccess());
    }

    @Override
    public void execute() {

        String email = this.registerDataType.getEmail();
        String password = this.registerDataType.getPassword();

        if (!validateForm(email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (task.isSuccessful()) {
                        // TODO: update view
                        notifySuccess();
                    } else {
                        notifyError(task.getException().getMessage());
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

