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
    private ApartmentFilterSettings apartmentFilterSettings;

    public ApartmentSettingsInteractorImpl(MainThread mainThread, Callback callback, ApartmentFilterSettings apartmentFilterSettings) {
        super(mainThread);
        this.mCallback = callback;
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
                mCallback.onSentSuccess();
            }
        });
    }

    @Override
    public void execute() {
        mDatabase.child(databaseRoot.getUserProfileNode(userId).getRootPath()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //PrimaryUserProfile primaryUserProfile = dataSnapshot.getValue(PrimaryUserProfile.class);
                //String aId = primaryUserProfile.getApartmentProfileId();
                //createApartmentSettings(databaseRoot.getApartmentProfileNode(aId).getApartmentFilterSettings());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
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
