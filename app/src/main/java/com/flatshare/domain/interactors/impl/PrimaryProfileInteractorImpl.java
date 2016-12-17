package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.repository.ProfileRepository;
import com.flatshare.storage.ProfileRepositoryImpl;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class PrimaryProfileInteractorImpl extends AbstractInteractor implements ProfileInteractor {

    private static final String TAG = "PrimaryProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private ProfileInteractor.Callback mCallback;

    private PrimaryUserProfile primaryUserProfile;

    private ProfileRepository profileRepository;

    public PrimaryProfileInteractorImpl(Executor threadExecutor,
                                        MainThread mainThread,
                                        Callback callback, PrimaryUserProfile primaryUserProfile) {

        super(threadExecutor, mainThread);
        Log.d(TAG, "inside constructor");
        this.mCallback = callback;
        this.primaryUserProfile = primaryUserProfile;
        this.profileRepository = new ProfileRepositoryImpl();
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

        mMainThread.post(() -> mCallback.onSentSuccess(primaryUserProfile.getClassificationId()));
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void run() {
        Log.d(TAG, "inside run()");

        if (
                profileRepository.createPrimaryProfile(primaryUserProfile)) {

            notifySuccess();
        } else {
            Log.w(TAG, "Could not create Primary Profile");
            notifyError();
        }
    }
}
