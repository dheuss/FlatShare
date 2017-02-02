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


    private static final String TAG = "RoommateWaitingInt";

    private RoommateWaitingInteractor.Callback mCallback;
    private RoommateProfile roommateProfile;

    public RoommateWaitingInteractorImpl(MainThread mainThread,
                                         Callback callback, RoommateProfile roommateProfile) {

        super(mainThread);
        this.mCallback = callback;
        this.roommateProfile = roommateProfile;

    }


    @Override
    public void execute() {
        final String path = databaseRoot.getRoommateProfileNode(this.roommateProfile.getRoommateId()).getDone();

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
//                        notifySuccess();
                        getApartment();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

    private void getApartment() {

        Log.d(TAG, "getApartment: APID: " + roommateProfile.getApartmentId());
        String apartmentsPath = databaseRoot.getApartmentProfileNode(roommateProfile.getApartmentId()).getRootPath();

        mDatabase.child(apartmentsPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    notifySuccess(null);
                } else {
                    ApartmentProfile apartmentProfile = dataSnapshot.getValue(ApartmentProfile.class);
                    notifySuccess(apartmentProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }

    private void notifySuccess(final ApartmentProfile apartmentProfile) {
//        Log.d(TAG, "notifySuccess: APARTMENT READY!!!");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onApartmentReady(apartmentProfile);
            }
        });
    }

    private void removeValueListener(String path) {
        mDatabase.child(path).removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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
