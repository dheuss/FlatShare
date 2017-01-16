package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.interactors.profile.FilterSettingsInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Arber on 10/12/2016.
 */
public class TenantSettingsInteractorImpl extends AbstractInteractor implements FilterSettingsInteractor {

    private static final String TAG = "TenantSettingsInt";
    private Callback mCallback;
    private String tenantId;
    private TenantFilterSettings tenantFilterSettings;

    public TenantSettingsInteractorImpl(MainThread mainThread, Callback callback, String tenandId, TenantFilterSettings tenantFilterSettings) {
        super(mainThread);
        this.mCallback = callback;
        this.tenantId = tenandId;
        this.tenantFilterSettings = tenantFilterSettings;
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
                mCallback.onSentSuccess(tenantFilterSettings);
            }
        });
    }

    @Override
    public void execute() {

        String path = databaseRoot.getTenantProfileNode(tenantId).getTenantFilterSettings();

        mDatabase.child(path).setValue(this.tenantFilterSettings, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    TenantSettingsInteractorImpl.this.notifySuccess();
                } else {
                    TenantSettingsInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });
    }

    private void createTenantSettings(String path) {
        mDatabase.child(path).setValue(this.tenantFilterSettings, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    TenantSettingsInteractorImpl.this.notifySuccess();
                } else {
                    TenantSettingsInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });
    }
}
