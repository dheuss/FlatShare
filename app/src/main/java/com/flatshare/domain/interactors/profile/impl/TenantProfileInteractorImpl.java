package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.profile.SecondaryProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Arber on 12/12/2016.
 */
public class TenantProfileInteractorImpl extends AbstractInteractor implements SecondaryProfileInteractor {

    private static final String TAG = "TenantProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private SecondaryProfileInteractor.Callback mCallback;

    private TenantProfile tenantProfile;

    public TenantProfileInteractorImpl(
            MainThread mainThread,
            Callback callback, TenantProfile tenantProfile) {

        super(mainThread);
        this.mCallback = callback;
        this.tenantProfile = tenantProfile;
    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentFailure(errorMessage);
            }
        });
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onProfileCreated(tenantProfile);
            }
        });
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void execute() {

        String tenantId = tenantProfile.getTenantId();

        System.out.println("ID ID ID ID ID ID: " + tenantId);

        Map<String, Object> map = new HashMap<>();
        map.put(databaseRoot.getTenantProfileNode(tenantId).getRootPath(), this.tenantProfile);
        map.put(databaseRoot.getUserProfileNode(userId).getTenantProfileId(), tenantId);

        mDatabase.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) { // Error
                    TenantProfileInteractorImpl.this.notifyError(databaseError.toException().getMessage());
                } else {
                    TenantProfileInteractorImpl.this.notifySuccess();
                }
            }
        });

    }
}
