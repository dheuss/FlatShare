package com.flatshare.network.impl;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flatshare.domain.datatypes.auth.ChangeMailAddressDataType;
import com.flatshare.domain.datatypes.auth.ChangePasswordDataType;
import com.flatshare.domain.datatypes.auth.ResetDataType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.network.AuthenticationManager;

/**
 * Created by Arber on 06/12/2016.
 */

public class AuthenticationManagerImpl implements AuthenticationManager {

    private AuthenticationManager.LoginCallback loginCallback;
    private AuthenticationManager.RegisterCallBack registerCallBack;
    private AuthenticationManager.ResetCallBack resetCallBack;
    private AuthenticationManager.ChangeMailCallBack changeMailCallBack;
    private AuthenticationManager.ChangePasswordCallBack changePasswordCallBack;
    private AuthenticationManager.RemoveUserCallBack removeUserCallBack;
    private AuthenticationManager.LogOutCallBack logOutCallBack;

    private String TAG = "AuthenticationManager";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private final FirebaseUser user;

    private AuthenticationManagerImpl() {
        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user_ = firebaseAuth.getCurrentUser();
            if (user_ != null) {
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
    }

    public AuthenticationManagerImpl(AuthenticationManager.LoginCallback loginCallback) {
        this();
        this.loginCallback = loginCallback;
    }

    public AuthenticationManagerImpl(AuthenticationManager.RegisterCallBack registerCallBack) {
        this();
        this.registerCallBack = registerCallBack;
    }

    public AuthenticationManagerImpl(AuthenticationManager.ResetCallBack resetCallBack) {
        this();
        this.resetCallBack = resetCallBack;
    }

    @Override
    public void reset(ResetDataType resetDataType) {
        String email = resetDataType.getEmail();
        if (TextUtils.isEmpty(email)) {
            return;
        }
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.v(TAG, "resetEmail:successful:" + task.isSuccessful());
                    } else {
                        Log.v(TAG, "resetEmail:failed:" + task.getException());
                    }
                });
    }

    @Override
    public void changeMail(ChangeMailAddressDataType changeMailAddressDataType) {
        System.out.println("Change Mail for Mail: " + changeMailAddressDataType.getEmail() + " USER: " + user.getUid());
        String email = changeMailAddressDataType.getEmail();
        if (TextUtils.isEmpty(email)){
            return;
        }
        user.updateEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.v(TAG, "changeMail:successful:" + task.isSuccessful());
                        logOut();
                    } else {
                        Log.v(TAG, "chanageMail:failed:" + task.getException());
                        logOutCallBack.onLogOutFailed("logoutFailed");
                    }
                });
    }

    @Override
    public void changePassword(ChangePasswordDataType changePasswordDataType) {
        System.out.println("Change Password for Mail: " + changePasswordDataType.getPassword());

    }

    @Override
    public void removeUser() {

    }

    @Override
    public void login(LoginDataType loginDataType) {

        String email = loginDataType.getEmail();
        String password = loginDataType.getPassword();

        if (!validateForm(email, password)) {
            //TODO: show error in view if not valid
            loginCallback.onLoginFailed("Invalid Email and/or Password");
            return;
        }
        // TODO: check arguments on addOnCompleteListener
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
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
                });
    }

    @Override
    public void register(RegisterDataType signUpDataType) {

        String email = signUpDataType.getEmail();
        String password = signUpDataType.getPassword();

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
                        registerCallBack.onRegisterSuccessful();
                    } else {
                        registerCallBack.onRegisterFailed(task.getException().getMessage());
                    }
                });
    }


    @Override
    public void logOut() {
        mAuth.signOut();
        logOutCallBack.onLogOutSuccessful();
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
}
