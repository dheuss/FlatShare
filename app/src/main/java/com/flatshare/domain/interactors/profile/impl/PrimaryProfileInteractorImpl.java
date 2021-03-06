package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.profile.PrimaryProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


/**
 * Created by Arber on 10/12/2016.
 */
public class PrimaryProfileInteractorImpl extends AbstractInteractor implements PrimaryProfileInteractor {

    private static final String TAG = "PrimaryProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private PrimaryProfileInteractor.Callback mCallback;

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
    private void notifyProfileCreated(final UserProfile secondaryProfile) {

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onProfileCreated(primaryUserProfile, secondaryProfile);
            }
        });
    }

    @Override
    public void execute() {

        String secondaryProfilePath;
        String secondaryProfileId;

        Log.d(TAG, "execute: ex");

        if (primaryUserProfile.getClassificationId() == ProfileType.TENANT.getValue()) {
            secondaryProfilePath = databaseRoot.getTenantProfiles();
            secondaryProfileId = mDatabase.child(secondaryProfilePath).push().getKey();
            primaryUserProfile.setTenantProfileId(secondaryProfileId);

            TenantProfile tenantProfile = new TenantProfile();
            tenantProfile.setTenantId(secondaryProfileId);

            pushId(secondaryProfilePath + secondaryProfileId, tenantProfile);
        } else if (primaryUserProfile.getClassificationId() == ProfileType.ROOMMATE.getValue()) {
            secondaryProfilePath = databaseRoot.getRoommateProfiles();
            secondaryProfileId = mDatabase.child(secondaryProfilePath).push().getKey();
            primaryUserProfile.setRoommateProfileId(secondaryProfileId);

            RoommateProfile roommateProfile = new RoommateProfile();
            roommateProfile.setRoommateId(secondaryProfileId);

            pushId(secondaryProfilePath + secondaryProfileId, roommateProfile);
        } else {
            notifyError("classificationID: " + primaryUserProfile.getClassificationId() + " is not valid!");
            return;
        }
    }

    private void pushId(final String secondaryProfilePath, final UserProfile userProfile) {
        mDatabase.child(secondaryProfilePath).setValue(userProfile, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    createProfile(userProfile);
                } else {
                    PrimaryProfileInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });
    }

    private void createProfile(final UserProfile userProfile) {

        Log.d(TAG, "creating main profile!");

        String primaryProfilePath = databaseRoot.getUserProfileNode(userId).getRootPath();

        mDatabase.child(primaryProfilePath).setValue(this.primaryUserProfile, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    PrimaryProfileInteractorImpl.this.notifyProfileCreated(userProfile);
                } else {
                    PrimaryProfileInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });

    }
}
