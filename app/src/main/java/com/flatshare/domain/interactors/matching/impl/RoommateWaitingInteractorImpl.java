package com.flatshare.domain.interactors.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.RoommateWaitingInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Arber on 11/01/2017.
 */

public class RoommateWaitingInteractorImpl extends AbstractInteractor implements RoommateWaitingInteractor {


    private static final String TAG = "RoommateQRInt";

    private RoommateWaitingInteractor.Callback mCallback;
    private String roommateId;

    public RoommateWaitingInteractorImpl(MainThread mainThread,
                                         Callback callback, String roommateId) {

        super(mainThread);
        this.mCallback = callback;
        this.roommateId = roommateId;

        Log.d(TAG, "RoommateWaitingInteractorImpl: CONSTRUCTOR!");
    }


    @Override
    public void execute() {
        final String path = databaseRoot.getRoommateProfileNode(this.roommateId).getDone();

        Log.d(TAG, "execute: trying to add value event listener");

        mDatabase.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: ");

//                RoommateProfile roommateProfile = dataSnapshot.getValue(RoommateProfile.class);

                if (dataSnapshot.getValue() == null) {
                    // Do nothing
//                    notifyError("there is no roommateprofile with ID: " + roommateId + " created!");
                    Log.d(TAG, "onDataChange: WaitingListener found null as value!");
                } else { // Profile was created
                    if (dataSnapshot.getValue(Boolean.class)) {
                        removeValueListener(path);
                        notifySuccess();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

    private void notifySuccess() {
        Log.d(TAG, "notifySuccess: APARTMENT READY!!!");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onApartmentReady();
            }
        });
    }

    private void removeValueListener(String path) {
        mDatabase.child(path).removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: read about it...
                Log.d(TAG, "onDataChange: removed listener");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void notifyError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.notifyError(errorMessage);
            }
        });
    }

}
