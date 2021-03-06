package com.flatshare.domain.interactors.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.RoommateQRInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Arber on 08/01/2017.
 */
public class RoommateQRInteractorImpl extends AbstractInteractor implements RoommateQRInteractor {


    private static final String TAG = "RoommateQRInt";

    private RoommateQRInteractor.Callback mCallback;
    private String roommateId;

    public RoommateQRInteractorImpl(MainThread mainThread,
                                  Callback callback, String roommateId) {

        super(mainThread);
        this.mCallback = callback;
        this.roommateId = roommateId;
    }


    @Override
    public void execute() {
        
        if(this.roommateId == null || this.roommateId.equals("")){
            Log.d(TAG, "execute: roommateID is null!");
            return;
        }

        String path = databaseRoot.getRoommateProfileNode(this.roommateId).getAvailable();

//        mDatabase.child(path).removeEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onDataChange: removeEventListener");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "onCancelled: removeEventListener");
//            }
//        });

        mDatabase.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                RoommateProfile roommateProfile = dataSnapshot.getValue(RoommateProfile.class);

                if(dataSnapshot == null){
                    // Do nothing
//                    notifyError("Roommate profile with profileID: " + roommateId + " does not exist!");
                } else { // ID was scanned
                    if(!dataSnapshot.getValue(Boolean.class)) {
                        getNewRoommateProfile();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }

    private void getNewRoommateProfile() {
        String path = databaseRoot.getRoommateProfileNode(this.roommateId).getRootPath();

        mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    RoommateProfile roommateProfile = dataSnapshot.getValue(RoommateProfile.class);
                    notifyCodeRead(roommateProfile);
                } else {
                    notifyCodeRead(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private void updateRoommateAvailable(final RoommateProfile roommateProfile) {
//
//        String path = databaseRoot.getRoommateProfileNode(roommateProfile.getRoommateId()).getAvailable();
//
//        mDatabase.child(path).setValue(false, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if(databaseError == null){ // no error
//                    roommateProfile.setAvailable(false);
//                } else {
//                    notifyError(databaseError.getMessage());
//                }
//            }
//        });
//
//    }


    private void notifyError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.notifyError(errorMessage);
            }
        });
    }

    private void notifyCodeRead(final RoommateProfile roommateProfile) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onCodeRead(roommateProfile);
            }
        });
    }
}
