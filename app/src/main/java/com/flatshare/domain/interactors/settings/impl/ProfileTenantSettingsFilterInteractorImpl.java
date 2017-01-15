package com.flatshare.domain.interactors.settings.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.settings.ProfileTenantSettingsFilterInteractor;

/**
 * Created by david on 15.01.2017.
 */

public class ProfileTenantSettingsFilterInteractorImpl extends AbstractInteractor implements ProfileTenantSettingsFilterInteractor{

    private static final String TAG = "ProfileTenantSettingsFilterInteractorImpl";

    private TenantFilterSettings tenantFilterProfile;

    private ProfileTenantSettingsFilterInteractor.Callback mCallback;

    public ProfileTenantSettingsFilterInteractorImpl(MainThread mainThread, Callback callback, TenantFilterSettings tenantFilterProfile) {
        super(mainThread);
        this.mCallback = callback;
        this.tenantFilterProfile = tenantFilterProfile;
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
        Log.v(TAG, "execute methode called in ProfileTenantSettingsFilterInteractorImpl");
        notifySuccess();
    }
}
