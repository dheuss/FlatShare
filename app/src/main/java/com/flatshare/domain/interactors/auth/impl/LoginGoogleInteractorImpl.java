package com.flatshare.domain.interactors.auth.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.LoginGoogleInteractor;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by david on 06.01.2017.
 */

public class LoginGoogleInteractorImpl extends AbstractAuthenticator implements LoginGoogleInteractor {

    private static final String TAG = "LoginGoogleInt";

    private LoginGoogleInteractor.Callback mCallback;

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;

    public LoginGoogleInteractorImpl(MainThread mainThread,
                               Callback callback) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onLoginGoogleFailure(errorMessage));
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onLoginGoogleSuccess());
    }

    @Override
    public void execute() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        //Inside on Create, right after GoogleSignInOptions
//        mGoogleApiClient = new GoogleApiClient.Builder(getApplication())
//                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//                    }
//                })
//                .addApi(mAuth.GOOGLE_SIGN_IN_API, gso)
//                .build();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()) {
//                GoogleSignInAccount account = result.getSignInAccount();
//                firebaseAuthWithGoogle(account);
//            } else {
//
//            }
//        }
//    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        notifySuccess();
                    } else {
                        notifyError("GoogleLoginFailed");
                    }
                });
    }
}
