package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.FilterSettingsInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.network.DatabaseTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class TenantSettingsInteractorImpl extends AbstractInteractor implements FilterSettingsInteractor {

    private static final String TAG = "TenantSettingsInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private Callback mCallback;

    private TenantFilterSettings tenantFilterSettings;

    public TenantSettingsInteractorImpl(MainThread mainThread,
                                        Callback callback, TenantFilterSettings tenantFilterSettings) {

        super(mainThread);
        this.mCallback = callback;
        this.tenantFilterSettings = tenantFilterSettings;
    }

    private void notifyError(String errorMessage) {

        mMainThread.post(() -> mCallback.onSentFailure(errorMessage));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onSentSuccess());
    }

    @Override
    public void execute() {

        String tPath = DatabaseTree.TENANT_PROFILES_PATH;
        String tsPath = DatabaseTree.FILTER_SETTINGS_PATH;
        String uId = DatabaseTree.USER_ID;
        String usersPath = DatabaseTree.USERS_PATH;

        mDatabase.child(usersPath+uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PrimaryUserProfile primaryUserProfile = dataSnapshot.getValue(PrimaryUserProfile.class);
                String tId = primaryUserProfile.getTenantProfileId();

                createTenantSettings(tPath+tId + "/" + tsPath);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }

    private void createTenantSettings(String path) {
        mDatabase.child(path).setValue(this.tenantFilterSettings, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                notifySuccess();
            } else {
                notifyError(databaseError.getMessage());
            }
        });
    }
}
