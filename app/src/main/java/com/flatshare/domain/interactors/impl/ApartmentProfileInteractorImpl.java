package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.repository.ProfileRepository;
import com.flatshare.storage.ProfileRepositoryImpl;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 */
public class ApartmentProfileInteractorImpl extends AbstractInteractor implements ProfileInteractor {

    private static final String TAG = "ApartmentProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private ProfileInteractor.Callback mCallback;

    private ApartmentUserProfile apartmentUserProfile;

    private ProfileRepository profileRepository;

    public ApartmentProfileInteractorImpl(Executor threadExecutor,
                                          MainThread mainThread,
                                          Callback callback, ApartmentUserProfile apartmentUserProfile) {

        super(threadExecutor, mainThread);
        Log.d(TAG, "inside constructor");
        this.mCallback = callback;
        this.apartmentUserProfile = apartmentUserProfile;
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

        mMainThread.post(() -> mCallback.onSentSuccess(1));
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void run() {
        Log.d(TAG, "inside run()");


        if(
            profileRepository.createApartmentProfile(apartmentUserProfile)){

            notifySuccess();
        } else{
            Log.w(TAG, "Could not create Apartment Profile");
            notifyError();
        }
    }
}
