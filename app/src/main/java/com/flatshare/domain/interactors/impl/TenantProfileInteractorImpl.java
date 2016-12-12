package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.google.firebase.database.DatabaseException;

import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.repository.ProfileRepository;
import com.flatshare.storage.ProfileRepositoryImpl;


/**
 *
 * */
public class TenantProfileInteractorImpl extends AbstractInteractor implements ProfileInteractor {

    private static final String TAG = "TenantProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private ProfileInteractor.Callback mCallback;

    private TenantUserProfile tenantUserProfile;

    private ProfileRepository profileRepository;

    public TenantProfileInteractorImpl(Executor threadExecutor,
                                        MainThread mainThread,
                                        Callback callback, TenantUserProfile tenantUserProfile) {

        super(threadExecutor, mainThread);
        Log.d(TAG, "inside constructor");
        this.mCallback = callback;
        this.tenantUserProfile = tenantUserProfile;
        profileRepository = new ProfileRepositoryImpl();
    }

    private void notifyError() {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onSentFailure("some kind of error"));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onSentSuccess(0));
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void run() {
        Log.d(TAG, "inside run()");

        try {
            profileRepository.createTenantProfile(tenantUserProfile);
            notifySuccess();
        } catch (DatabaseException e) {
            Log.w(TAG, e);
            notifyError();
        }
    }
}