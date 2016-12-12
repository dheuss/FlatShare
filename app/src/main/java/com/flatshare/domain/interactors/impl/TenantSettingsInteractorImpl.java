package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.google.firebase.database.DatabaseException;

import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.FilterSettingsInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.repository.ProfileRepository;
import com.flatshare.storage.ProfileRepositoryImpl;


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

    private ProfileRepository profileRepository; // maybe use another one?

    public TenantSettingsInteractorImpl(Executor threadExecutor,
                                        MainThread mainThread,
                                        Callback callback, TenantFilterSettings tenantFilterSettings) {

        super(threadExecutor, mainThread);
        Log.d(TAG, "inside constructor");
        this.mCallback = callback;
        this.tenantFilterSettings = tenantFilterSettings;
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

        mMainThread.post(() -> mCallback.onSentSuccess());
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void run() {
        Log.d(TAG, "inside run()");

        try {
            //TODO: change it to do something
//            profileRepository.createTenantProfile(tenantFilterSettings);
            notifySuccess();
        } catch (DatabaseException e) {
            Log.w(TAG, e);
            notifyError();
        }
    }
}
