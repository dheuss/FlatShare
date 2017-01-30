package com.flatshare.domain.interactors.auth.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.AbstractAuthenticator;
import com.flatshare.domain.interactors.auth.ChangeMailInteractor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
/**
 * Created by David on 06/12/2016.
 */
public class ChangeMailInteractorImpl extends AbstractAuthenticator implements ChangeMailInteractor {

    private static final String TAG = "ChangeMailInt";

    private ChangeMailInteractor.Callback mCallback;
    private String newMailAddress;

    public ChangeMailInteractorImpl(MainThread mainThread,
                                          Callback callback,
                                    String newMailAddress) {
        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.newMailAddress = newMailAddress;
    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onChangeMailFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onChangeMailSuccess();
            }
        });
    }

    @Override
    public void execute() {
        String email = newMailAddress;
        if (TextUtils.isEmpty(email)){
            return;
        }

        userFirebase.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.v(TAG, "changeMail:successful:" + task.isSuccessful());
                            ChangeMailInteractorImpl.this.notifySuccess();
                        } else {
                            Log.v(TAG, "chanageMail:failed:" + task.getException());
                            ChangeMailInteractorImpl.this.notifyError("logoutFailed");
                        }
                    }
                });
    }
}
