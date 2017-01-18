package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.datatypes.enums.ProfileType;
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
    private RoommateProfile roommateProfile;

    public RoommateProfileInteractorImpl(MainThread mainThread,
                                         Callback callback, RoommateProfile roommateProfile) {

        super(mainThread);
        this.mCallback = callback;
        this.roommateProfile = roommateProfile;
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
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onProfileCreated(roommateProfile);
            }
        });
    }

    @Override
    public void execute() {

        if (roommateProfile.isOwner()) { // also add apId
            String apartmentProfilePath = databaseRoot.getApartmentProfiles();
            String apartmentId = mDatabase.child(apartmentProfilePath).push().getKey();
            this.roommateProfile.setAvailable(false);
            roommateProfile.setApartmentId(apartmentId);
        }
        createProfile();

    }

    private void createProfile() {

        String roommateId = roommateProfile.getRoommateId();
        String roommateProfilePath = databaseRoot.getRoommateProfileNode(roommateId).getRootPath();

        String userClassificationPath = databaseRoot.getUserProfileNode(userId).getClassificationId();

        ApartmentProfile apartmentProfile = new ApartmentProfile();
        String apartmentId = roommateProfile.isOwner() ? roommateProfile.getApartmentId() : null;
        apartmentProfile.setApartmentId(apartmentId);

        Map<String, Object> map = new HashMap<>();
        if (this.roommateProfile.isOwner()) {
            map.put(userClassificationPath, ProfileType.APARTMENT.getValue());
        }
        map.put(roommateProfilePath, this.roommateProfile);

        if (apartmentId != null) {
            String apartmentProfilePath = databaseRoot.getApartmentProfileNode(apartmentId).getRootPath();
            map.put(apartmentProfilePath, apartmentProfile);
        }

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

//    private void bindRoommateWithApartment(String roommateId, String apartmentId) {
//
//        String roommateProfilePath = databaseRoot.getRoommateProfileNode(roommateId).getRootPath();
//        String apartmentProfilePath = databaseRoot.getApartmentProfileNode(apartmentId).getRootPath();
//
//        RoommateProfile roommateProfile = new RoommateProfile();
//        roommateProfile.setRoommateId(roommateId);
//        roommateProfile.setApartmentId(apartmentId);
//        roommateProfile.setOwner(true);
//        roommateProfile.setAvailable(false);
//
//        ApartmentProfile apartmentProfile = new ApartmentProfile();
//        apartmentProfile.setApartmentId(apartmentId);
//        pushId(roommateProfilePath, roommateProfile, apartmentProfile);
//        mDatabase.child(apartmentProfilePath).setValue(apartmentProfile);
//
//    }
//
//    private void pushId(final String secondaryProfilePath, final UserProfile userProfile, final ApartmentProfile apartmentProfile) {
//        mDatabase.child(secondaryProfilePath).setValue(userProfile, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError == null) {
//                    triggerPopup(userProfile, apartmentProfile);
//                } else {
//                    PrimaryProfileInteractorImpl.this.notifyError(databaseError.getMessage());
//                }
//            }
//        });
//    }
}
