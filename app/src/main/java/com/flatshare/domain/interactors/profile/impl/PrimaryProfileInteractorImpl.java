package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.interactors.profile.ProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Arber on 10/12/2016.
 */
public class PrimaryProfileInteractorImpl extends AbstractInteractor implements ProfileInteractor {

    private static final String TAG = "PrimaryProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private ProfileInteractor.Callback mCallback;

    private PrimaryUserProfile primaryUserProfile;

    public PrimaryProfileInteractorImpl(MainThread mainThread,
                                        Callback callback, PrimaryUserProfile primaryUserProfile) {

        super(mainThread);
        this.mCallback = callback;
        this.primaryUserProfile = primaryUserProfile;
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
        Log.d(TAG, "inside notifySuccess");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentSuccess(primaryUserProfile.getClassificationId());
            }
        });
    }

    @Override
    public void execute() {

        mDatabase.child(databaseRoot.getUserProfileNode(userId).getRootPath()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    createProfile();
                } else {
                    updateClassificationId();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }

    private void createProfile() {

        Log.d(TAG, "creating main profile!");

        mDatabase.child(databaseRoot.getUserProfileNode(userId).getRootPath()).setValue(this.primaryUserProfile, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    PrimaryProfileInteractorImpl.this.notifySuccess();
                } else {
                    PrimaryProfileInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });

    }

    private void updateClassificationId() {


        Map<String, Object> map = new HashMap<>();
        map.put(databaseRoot.getUserProfileNode(userId).getClassificationId(), this.primaryUserProfile.getClassificationId());

        mDatabase.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) { // Error
                    PrimaryProfileInteractorImpl.this.notifyError(databaseError.toException().getMessage());
                } else {
                    PrimaryProfileInteractorImpl.this.notifySuccess();
                }
            }
        });
    }

}
