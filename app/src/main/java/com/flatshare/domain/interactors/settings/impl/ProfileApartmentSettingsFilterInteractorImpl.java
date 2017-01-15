package com.flatshare.domain.interactors.settings.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.settings.ProfileApartmentSettingsFilterInteractor;

/**
 * Created by david on 15.01.2017.
 */

public class ProfileApartmentSettingsFilterInteractorImpl extends AbstractInteractor implements ProfileApartmentSettingsFilterInteractor {

    private static final String TAG = "ProfileApartmentSettingsFilterInteractorImpl";

    private ApartmentProfile apartmentProfile;

    private ProfileApartmentSettingsFilterInteractor.Callback mCallback;

    public ProfileApartmentSettingsFilterInteractorImpl(MainThread mainThread, Callback callback, ApartmentProfile apartmentProfile) {
        super(mainThread);
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

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentSuccess();//ProfileType.APARTMENT.getValue()
            }
        });
    }

    @Override
    public void execute() {
        Log.v(TAG, "execute methode called in ProfileApartmentSettingsFilterInteractorImpl");
        notifySuccess();
    }
}
