package com.flatshare.domain.interactors.settings.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.settings.ProfileTenantSettingsInteractor;

/**
 * Created by david on 28.12.2016.
 */

public class ProfileTenantSettingsInteractorImpl extends AbstractInteractor implements ProfileTenantSettingsInteractor {

    private static final String TAG = "ProfileTenantSettingsInteractorImpl";

    private TenantProfile tenantProfile;

    private ProfileTenantSettingsInteractor.Callback mCallback;

    public ProfileTenantSettingsInteractorImpl(MainThread mainThread, Callback callback, TenantProfile tenantProfile) {
        super(mainThread);
        this.mCallback = callback;
        this.tenantProfile = tenantProfile;
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
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentSuccess();//ProfileType.TENANT.getValue()
            }
        });
    }

    @Override
    public void execute() {
        Log.v(TAG, "execute methode called in ProfileTenantSettingsInteractorImpl");
        notifySuccess();
//        String tId = mDatabase.child(databaseRoot.getTenantProfiles()).push().getKey();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put(databaseRoot.getTenantProfileNode(tId).getRootPath(), this.tenantProfile);
//        map.put(databaseRoot.getUserProfileNode(userId).getTenantProfileId(), tId);
//
//        mDatabase.updateChildren(map, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError != null) { // Error
//                    ProfileTenantSettingsInteractorImpl.this.notifyError(databaseError.toException().getMessage());
//                } else {
//                    ProfileTenantSettingsInteractorImpl.this.notifySuccess();
//                }
//            }
//        });
    }
}
