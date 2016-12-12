package com.flatshare.network.impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.network.AuthenticationManager;

/**
 * Created by Arber on 06/12/2016.
 */

public class AuthenticationManagerImpl implements AuthenticationManager {


    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private AuthenticationManager.LoginCallback loginCallback;

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private AuthenticationManager.RegisterCallBack registerCallBack;


    private String TAG = "AuthenticationManager";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private AuthenticationManagerImpl() {
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        // [START auth_state_listener]
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
        // [END auth_state_listener]
    }

    public AuthenticationManagerImpl(AuthenticationManager.LoginCallback loginCallback) {
        this();
        this.loginCallback = loginCallback;

    }

    public AuthenticationManagerImpl(AuthenticationManager.RegisterCallBack registerCallBack) {

        this();
        this.registerCallBack = registerCallBack;
    }

    @Override
    public void login(LoginDataType loginDataType) {

        String email = loginDataType.getEmail();
        String password = loginDataType.getPassword();

        if (!validateForm(email, password)) {
            //TODO: show error in view if not valid
        }

//        showProgressDialog();

        // TODO: check arguments on addOnCompleteListener
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithEmail:failed", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
//                                    Toast.LENGTH_SHORT).show();
                        // TODO: update view accordingly
                        loginCallback.onLoginFailed("signInWithEmail:failed");
                    } else {
                        Log.d(TAG, "SUCCESSS!!");
                        loginCallback.onLoginSuccessful();
                    }

                    // [START_EXCLUDE]
//                        hideProgressDialog();
                    // [END_EXCLUDE]
                });
        // [END sign_in_with_email]
    }

    @Override
    public void register(RegisterDataType signUpDataType) {

        String email = signUpDataType.getEmail();
        String password = signUpDataType.getPassword();

        if (!validateForm(email, password)) {
            return;
        }

//        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        // TODO: update view
                    }

                    // [START_EXCLUDE]
//                        hideProgressDialog();
                    // [END_EXCLUDE]
                });
        // [END create_user_with_email]
    }


    @Override
    public void logOut() {
        mAuth.signOut();
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


    private void notifyError() {


        loginCallback.onLoginFailed("Cannot login");

    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void postMessage() {
        loginCallback.onLoginSuccessful();

    }


//    public void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage(getString(R.string.loading));
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
//    }
//
//    public void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        hideProgressDialog();
//    }
}
