package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.profile.FilterSettingsInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by david on 11.01.2017.
 */
public class ApartmentSettingsInteractorImpl extends AbstractInteractor implements FilterSettingsInteractor {

    private static final String TAG = "ApartmentSettingsInteractorImpl";
    private Callback mCallback;
    private String apartmentId;
    private ApartmentFilterSettings apartmentFilterSettings;

    public ApartmentSettingsInteractorImpl(MainThread mainThread, Callback callback, String apartmentId, ApartmentFilterSettings apartmentFilterSettings) {
        super(mainThread);
        this.mCallback = callback;
        this.apartmentId = apartmentId;
        this.apartmentFilterSettings = apartmentFilterSettings;
    }

    private void notifyError(final String errorMessage) {
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
                mCallback.onSentSuccess(apartmentFilterSettings);
            }
        });
    }

    @Override
    public void execute() {

        String path = databaseRoot.getApartmentProfileNode(apartmentId).getApartmentFilterSettings();

            mDatabase.child(path).setValue(this.apartmentFilterSettings, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        ApartmentSettingsInteractorImpl.this.notifySuccess();
                    } else {
                        ApartmentSettingsInteractorImpl.this.notifyError(databaseError.getMessage());
                    }
                }
            });


        // TODO: if anything goes wrong do it the hard way...get roommateId, get apartmentId, and then store it...
    }

    private void createApartmentSettings(String path) {
        mDatabase.child(path).setValue(this.apartmentFilterSettings, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    ApartmentSettingsInteractorImpl.this.notifySuccess();
                } else {
                    ApartmentSettingsInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });
    }
}
