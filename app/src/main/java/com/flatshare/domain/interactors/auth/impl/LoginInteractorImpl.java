package com.flatshare.domain.interactors.auth.impl;

import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.LoginInteractor;

/**
 * Created by Arber on 06/01/2017.
 */

public class LoginInteractorImpl extends AbstractAuthenticator implements LoginInteractor {

    private static final String TAG = "LoginInt";

    private LoginDataType loginDataType;
    private LoginInteractor.Callback mCallback;

    public LoginInteractorImpl(MainThread mainThread,
                               Callback callback, LoginDataType loginDataType) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.loginDataType = loginDataType;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onLoginFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onLoginSuccess());
    }

    @Override
    public void execute() {

        String email = loginDataType.getEmail();
        String password = loginDataType.getPassword();


        if (!validateForm(email, password)) {
            notifyError("Invalid Email and/or Password");
            return;
        }
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithEmail:failed", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
//                                    Toast.LENGTH_SHORT).show();

                        notifyError("signInWithEmail:failed");
                    } else {
                        Log.d(TAG, "SUCCESSS!!");
                        notifySuccess();
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

