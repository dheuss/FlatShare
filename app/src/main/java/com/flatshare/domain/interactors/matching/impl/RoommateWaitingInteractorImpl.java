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
    private String apartmentId;

    public RoommateWaitingInteractorImpl(MainThread mainThread,
                                         Callback callback, String apartmentId) {

        super(mainThread);
        this.mCallback = callback;
        this.apartmentId = apartmentId;
    }


    @Override
    public void execute() {
        final String path = databaseRoot.getApartmentProfileNode(this.apartmentId).getApartmentFilterSettings();

        mDatabase.child(path).removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: removed listener");

                mDatabase.child(path).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ApartmentFilterSettings apartmentFilterSettings = dataSnapshot.getValue(ApartmentFilterSettings.class);

                        Log.d(TAG, "onDataChange: added Listener: " + (apartmentFilterSettings == null));

                        if (apartmentFilterSettings == null) {
                            // Do nothing
                            notifyError("Settings of ap with apartmentID: " + apartmentId + " not ready yet!");
                        } else { // Profile was created
                            removeValueListener(path);
                            notifySuccess();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        notifyError(databaseError.getMessage());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: something went wrong but its not important");
            }
        });

    }

    private void notifySuccess() {
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
                Log.d(TAG, "onDataChange: removed for 2nd time");
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
