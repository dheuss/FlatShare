package com.flatshare.domain.interactors.matchingoverview.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matchingoverview.MatchingOverviewInteractor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by david on 06/12/2016.
 */

public class MatchingOverviewInteractorImpl extends AbstractInteractor implements MatchingOverviewInteractor {

    private static final String TAG = "MatchingOverviewInteractorImpl";
    private final String tenantId;
    private final String apartmentId;

    private MatchingOverviewInteractor.Callback mCallback;

    public MatchingOverviewInteractorImpl(MainThread mainThread, Callback callback, String tenantId, String apartmentId) {
        super(mainThread);
        this.mCallback = callback;
        this.tenantId = tenantId;
        this.apartmentId = apartmentId;
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

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentSuccess();
            }
        });
    }

    @Override
    public void execute() {
        Log.v(TAG, "execute methode called in MatchingOverviewInteractorImpl");
        Log.d(TAG, "execute: TenantID: " + tenantId);
        Log.d(TAG, "execute: ApartmentID: " + apartmentId);

        final String path = databaseRoot.getMatchesNode(tenantId, apartmentId).getRootPath();
        mDatabase.child(path).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    Log.d(TAG, "onComplete: Sandro" + path);
                    notifySuccess();
                }else{
                    notifyError(databaseError.getMessage());
                }

            }
        });
    }
}
