package com.flatshare.network.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.datatypes.auth.ChangeMailAddressDataType;
import com.flatshare.domain.datatypes.auth.ChangePasswordDataType;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.domain.datatypes.auth.ResetDataType;
import com.flatshare.network.AuthenticationManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user_ = firebaseAuth.getCurrentUser();
                if (user_ != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
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

    public AuthenticationManagerImpl(AuthenticationManager.LogOutCallBack logOutCallBack){
        this();
        this.logOutCallBack = logOutCallBack;
    }

    @Override
    public void login(LoginDataType loginDataType) {

    }

    @Override
    public void register(RegisterDataType signUpDataType) {

    }

    @Override
    public void reset(ResetDataType resetDataType) {
        String email = resetDataType.getEmail();
        if (TextUtils.isEmpty(email)) {
            return;
        }
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.v(TAG, "resetEmail:successful:" + task.isSuccessful());
                        } else {
                            Log.v(TAG, "resetEmail:failed:" + task.getException());
                        }
                    }
                });
    }

    @Override
    public void changeMail(ChangeMailAddressDataType changeMailAddressDataType) {
    }

    @Override
    public void changePassword(ChangePasswordDataType changePasswordDataType) {
    }

    @Override
    public void removeUser() {

    }



    @Override
    public void logOut() {
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
