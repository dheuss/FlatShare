package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.network.DatabaseTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
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

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onSentFailure(errorMessage));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {
        Log.d(TAG, "inside notifySuccess");

        mMainThread.post(() -> mCallback.onSentSuccess(primaryUserProfile.getClassificationId()));
    }

    @Override
    public void execute() {

        String uId = DatabaseTree.USER_ID;
        String usersPath = DatabaseTree.USERS_PATH;

        mDatabase.child(usersPath + uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {
                    createProfile(usersPath + uId);
                } else {
                    notifySuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }

    private void createProfile(String path) {

        mDatabase.child(path).setValue(this.primaryUserProfile, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                notifySuccess();
            } else {
                notifyError(databaseError.getMessage());
            }
        });

    }

}
