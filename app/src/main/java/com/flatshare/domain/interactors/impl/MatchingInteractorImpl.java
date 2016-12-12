package com.flatshare.domain.interactors.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.MatchingInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.repository.MatchingRepository;
import com.flatshare.storage.MatchingRepositoryImpl;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class MatchingInteractorImpl extends AbstractInteractor implements MatchingInteractor {

    private static final String TAG = "MatchingInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private MatchingInteractor.Callback mCallback;
    private MatchingRepository matchingRepository;

    public MatchingInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  Callback callback) {

        super(threadExecutor, mainThread);
        Log.d(TAG, "inside constructor");
        this.mCallback = callback;
        this.matchingRepository = new MatchingRepositoryImpl(this);
    }

    private void notifyMatchFound(List<UserProfile> userProfiles) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(() -> mCallback.onMatchFound(userProfiles));
    }


    private void notifyNoMatchFound() {

        mMainThread.post(() -> mCallback.onNoMatchFound());
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void run() {

        Log.d(TAG, "inside run()");

        ArrayList<UserProfile> userProfiles = calculateMatches();


        if (userProfiles.isEmpty()) {
            notifyNoMatchFound();
        } else {
            notifyMatchFound(userProfiles);
        }

//        try {
//            profileRepository.storeClassification(isTenant);
//            notifySuccess();
//        } catch (CreateFailureException e) {
//            Log.w(TAG, e);
//            notifyError();
//        }
    }

    private ArrayList<UserProfile> calculateMatches() {
        return new ArrayList<>();
    }
}
