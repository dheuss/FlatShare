package com.flatshare.domain.interactors.settings.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.ProfileType;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.settings.ProfileSettingsInteractor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 28.12.2016.
 */

public class ProfileSettingsInteractorImpl extends AbstractInteractor implements ProfileSettingsInteractor {

    private static final String TAG = "ProfileSettingsInteractorImpl";

    private TenantProfile tenantProfile;

    private ProfileSettingsInteractor.Callback mCallback;

    public ProfileSettingsInteractorImpl(MainThread mainThread, Callback callback, TenantProfile tenantProfile) {
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
                mCallback.onSentSuccess(ProfileType.TENANT.getValue());
            }
        });
    }

    @Override
    public void execute() {

        String tId = mDatabase.child(databaseRoot.getTenantProfiles()).push().getKey();

        System.out.println("GETKEY ---" + mDatabase.child(databaseRoot.getTenantProfiles()).push().getKey());
        System.out.println("USERID --- " + userId);
        System.out.println("FIREBASEUSERID --- " + firebaserUserId);
        System.out.println("GETROOTPATH --- " + databaseRoot.getTenantProfileNode(tId).getRootPath());
        System.out.println("GETTENANTPROFILID --- " + databaseRoot.getUserProfileNode(userId).getTenantProfileId().toString());
        System.out.println("DATABASEROOT --- " + databaseRoot.getTenantProfileNode(tId).toString());
        System.out.println("--- " + databaseRoot.getTenantProfileNode(tId));
        ProfileSettingsInteractorImpl.this.notifySuccess();

        //ProfileSettingsInteractorImpl.this.notifySuccess();
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
//                    ProfileSettingsInteractorImpl.this.notifyError(databaseError.toException().getMessage());
//                } else {
//                    ProfileSettingsInteractorImpl.this.notifySuccess();
//                }
//            }
//        });
    }
}
