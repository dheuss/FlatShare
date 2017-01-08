package com.flatshare.domain.interactors.auth;

import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Arber on 06/01/2017.
 */

public abstract class AbstractAuthenticator extends AbstractInteractor {

    private static final String TAG = "AbstractAuthenticator";

    // Authentication
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected FirebaseUser userFirebase;

    public AbstractAuthenticator(MainThread mainThread) {
        super(mainThread);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user_ = firebaseAuth.getCurrentUser();
                if (user_ != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + userId);

                    AbstractAuthenticator.this.userFirebase = user_;

                    Log.v(TAG, userFirebase + "  ++++++ ABstractActivity FIREBVASEUSER ID");
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


    }
}
