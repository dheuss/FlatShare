package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.interactors.profile.SecondaryProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Arber on 10/12/2016.
 */
public class ApartmentProfileInteractorImpl extends AbstractInteractor implements SecondaryProfileInteractor {

    private static final String TAG = "ApartmentProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private SecondaryProfileInteractor.Callback mCallback;

    private ApartmentProfile apartmentProfile;

    public ApartmentProfileInteractorImpl(MainThread mainThread,
                                          Callback callback,ApartmentProfile apartmentProfile) {

        super(mainThread);
        this.mMainThread = mainThread;
        this.mCallback = callback;
        this.apartmentProfile = apartmentProfile;
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
    private void notifySuccess(final ApartmentProfile apartmentProfile) {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onProfileCreated(apartmentProfile);
            }
        });
    }

    @Override
    public void execute() {

        String apartmentId = apartmentProfile.getApartmentId();

        String city = apartmentProfile.getApartmentLocation().getCity();
        String district = apartmentProfile.getApartmentLocation().getDistrict();
        int zipCode = apartmentProfile.getApartmentLocation().getZipCode();

        String locationPath = databaseRoot.getApartmentLocationsNode().getCitiesNode(city).getDistrictsNode(district).getZipCodesNode(zipCode).getRootPath();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(databaseRoot.getApartmentProfileNode(apartmentId).getRootPath(), this.apartmentProfile);
        map.put(locationPath, apartmentId);

        for (String id : this.apartmentProfile.getRoommateIds()){
            map.put(databaseRoot.getRoommateProfileNode(id).getApartmentId(), this.apartmentProfile.getId());
            map.put(databaseRoot.getRoommateProfileNode(id).getAvailable(), false);
        }

        mDatabase.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) { // Error
                    ApartmentProfileInteractorImpl.this.notifyError(databaseError.toException().getMessage());
                } else {
                    ApartmentProfileInteractorImpl.this.notifySuccess(apartmentProfile);
                }
            }
        });

    }
}
