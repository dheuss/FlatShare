package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.interactors.profile.ProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Arber on 10/12/2016.
 */
public class ApartmentProfileInteractorImpl extends AbstractInteractor implements ProfileInteractor {

    private static final String TAG = "ApartmentProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private ProfileInteractor.Callback mCallback;

    private ApartmentUserProfile apartmentUserProfile;

    public ApartmentProfileInteractorImpl(MainThread mainThread,
                                          Callback callback, ApartmentUserProfile apartmentUserProfile) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.apartmentUserProfile = apartmentUserProfile;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onSentFailure(errorMessage));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onSentSuccess(1));
    }

    @Override
    public void execute() {

        apartmentUserProfile.getRoommateIds().add(userId);

        String city = apartmentUserProfile.getApartmentLocation().getCity();
        String district = apartmentUserProfile.getApartmentLocation().getDistrict();
        int zipCode = apartmentUserProfile.getApartmentLocation().getZipCode();

        String locationPath = databaseRoot.getApartmentLocationsNode().getCitiesNode(city).getDistrictsNode(district).getZipCodesNode(zipCode).getRootPath();

        String apartmentId = mDatabase.child(databaseRoot.getApartmentProfiles()).push().getKey();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(databaseRoot.getApartmentProfileNode(apartmentId).getRootPath(), this.apartmentUserProfile);

        map.put(databaseRoot.getUserProfileNode(userId).getApartmentProfileId(), apartmentId);
        map.put(locationPath, apartmentId);

        mDatabase.updateChildren(map, (databaseError, databaseReference) -> {
            if (databaseError != null) { // Error
                notifyError(databaseError.toException().getMessage());
            } else {
                notifySuccess();
            }
        });

    }
}
