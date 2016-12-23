package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.network.DatabaseTree;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * This is an interactor boilerplate with a reference to a model repository.
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

        String apPath = DatabaseTree.APARTMENT_PROFILES_PATH;
        String usersPath = DatabaseTree.USERS_PATH;

        String city = apartmentUserProfile.getApartmentLocation().getCity();
        String district = apartmentUserProfile.getApartmentLocation().getDistrict();
        int zipCode = apartmentUserProfile.getApartmentLocation().getZipCode();

        String locationPath = DatabaseTree.APARTMENTS_LOCATION_PATH + city + "/" + district + "/" + zipCode;

        String apartmentId = mDatabase.child(apPath).push().getKey();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(apPath+apartmentId, this.apartmentUserProfile);
        map.put(usersPath + DatabaseTree.USER_ID, apartmentId);
        map.put(locationPath, apartmentId);

        mDatabase.updateChildren(map, (databaseError, databaseReference) -> {
            if(databaseError != null){ // Error
                notifyError(databaseError.toException().getMessage());
            } else {
                notifySuccess();
            }
        });

    }
}
