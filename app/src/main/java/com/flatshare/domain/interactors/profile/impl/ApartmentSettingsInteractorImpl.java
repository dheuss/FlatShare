package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.profile.FilterSettingsInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 11.01.2017.
 */
public class ApartmentSettingsInteractorImpl extends AbstractInteractor implements FilterSettingsInteractor {

    private static final String TAG = "ApartmentSettingsInteractorImpl";
    private Callback mCallback;
    private String apartmentId;
    private ApartmentFilterSettings apartmentFilterSettings;
    private List<String> roommateIds;

    public ApartmentSettingsInteractorImpl(MainThread mainThread, Callback callback, String apartmentId, ApartmentFilterSettings apartmentFilterSettings, List<String> roommateIds) {
        super(mainThread);
        this.mCallback = callback;
        this.apartmentId = apartmentId;
        this.apartmentFilterSettings = apartmentFilterSettings;
        this.roommateIds = roommateIds;
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
        Map<String, Object> map = new HashMap<>();
        map.put(path, this.apartmentFilterSettings);
        for (String id : roommateIds) {
            map.put(databaseRoot.getRoommateProfileNode(id).getDone(), true);
        }

        mDatabase.updateChildren(map, new DatabaseReference.CompletionListener() {
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
