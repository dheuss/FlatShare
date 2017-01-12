package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.ProfileType;
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
        } else if (primaryUserProfile.getClassificationId() == ProfileType.APARTMENT.getValue()) {
            secondaryProfilePath = databaseRoot.getRoommateProfiles();
            secondaryProfileId = mDatabase.child(secondaryProfilePath).push().getKey();
            primaryUserProfile.setRoommateProfileId(secondaryProfileId);


            RoommateProfile roommateProfile = new RoommateProfile();
            roommateProfile.setRoommateId(secondaryProfileId);

            String apartmentProfilePath = databaseRoot.getApartmentProfiles();
            String apartmentId = mDatabase.child(apartmentProfilePath).push().getKey();
            roommateProfile.setApartmentId(apartmentId);

            ApartmentProfile apartmentProfile = new ApartmentProfile();
            apartmentProfile.setApartmentId(apartmentId);

            bindRoommateWithApartment(secondaryProfileId, apartmentId);
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
                    Log.d(TAG, "execute: PUSHING TO " + secondaryProfilePath);
                    createProfile(userProfile);
                } else {
                    PrimaryProfileInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });
    }

    private void bindRoommateWithApartment(String roommateId, String apartmentId) {

        String roommateProfilePath = databaseRoot.getRoommateProfileNode(roommateId).getRootPath();
        String apartmentProfilePath = databaseRoot.getApartmentProfileNode(apartmentId).getRootPath();


        RoommateProfile roommateProfile = new RoommateProfile();
        roommateProfile.setRoommateId(roommateId);
        roommateProfile.setApartmentId(apartmentId);
        roommateProfile.setAvailable(true);

        pushId(roommateProfilePath, roommateProfile);

        ApartmentProfile apartmentProfile = new ApartmentProfile();
        apartmentProfile.setApartmentId(apartmentId);
        mDatabase.child(apartmentProfilePath).setValue(apartmentProfile);
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

//    private void updateClassificationId() {
//
//        //TODO: check if redundant => can it be deleted?
//
//        Map<String, Object> map = new HashMap<>();
//        map.put(databaseRoot.getUserProfileNode(userId).getClassificationId(), this.primaryUserProfile.getClassificationId());
//
//        mDatabase.updateChildren(map, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError != null) { // Error
//                    PrimaryProfileInteractorImpl.this.notifyError(databaseError.toException().getMessage());
//                } else {
//                    PrimaryProfileInteractorImpl.this.notifyProfileCreated(primaryUserProfile);
//                }
//            }
//        });
//    }

}
