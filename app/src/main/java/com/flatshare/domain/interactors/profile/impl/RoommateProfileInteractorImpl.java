package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.ProfileType;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.profile.ProfileInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arber on 09/01/2017.
 */
public class RoommateProfileInteractorImpl extends AbstractInteractor implements ProfileInteractor {

    private static final String TAG = "RoommateProfileInt";

    private ProfileInteractor.Callback mCallback;

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


    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentSuccess(ProfileType.ROOMMATE.getValue());
            }
        });
    }


    @Override
    public void execute() {

        String rId = mDatabase.child(databaseRoot.getRoommateProfiles()).push().getKey();

        Map<String, Object> map = new HashMap<>();
        map.put(databaseRoot.getTenantProfileNode(rId).getRootPath(), new RoommateProfile());
        map.put(databaseRoot.getUserProfileNode(userId).getRoommateProfileId(), rId);

        mDatabase.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) { // Error
                    notifyError(databaseError.toException().getMessage());
                } else {
                    notifySuccess();
                }
            }
        });

    }
}
