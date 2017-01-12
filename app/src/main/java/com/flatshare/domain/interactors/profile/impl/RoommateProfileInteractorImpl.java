package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.profile.SecondaryProfileInteractor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arber on 09/01/2017.
 */
public class RoommateProfileInteractorImpl extends AbstractInteractor implements SecondaryProfileInteractor {

    private static final String TAG = "RoommateProfileInt";

    private SecondaryProfileInteractor.Callback mCallback;

    public RoommateProfileInteractorImpl(MainThread mainThread,
                                         Callback callback) {

        super(mainThread);
        this.mCallback = callback;
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


    private void notifyRoommateProfileCreated(final RoommateProfile roommateProfile) {
        Log.d(TAG, "inside notifySuccess");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onProfileCreated(roommateProfile);
            }
        });
    }


    @Override
    public void execute() {

        final String rId = mDatabase.child(databaseRoot.getRoommateProfiles()).push().getKey();

        final RoommateProfile roommateProfile = new RoommateProfile();
        roommateProfile.setRoommateId(rId);

        Map<String, Object> map = new HashMap<>();
        map.put(databaseRoot.getRoommateProfileNode(rId).getRootPath(), roommateProfile);
        map.put(databaseRoot.getUserProfileNode(userId).getRoommateProfileId(), rId);

        mDatabase.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) { // Error
                    notifyError(databaseError.toException().getMessage());
                } else {
                    notifyRoommateProfileCreated(roommateProfile);
                }
            }
        });

    }
}
