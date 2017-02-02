package com.flatshare.presentation.presenters.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.interactors.matching.RoommateWaitingInteractor;
import com.flatshare.domain.interactors.matching.impl.RoommateWaitingInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matching.RoommateWaitingPresenter;

/**
 * Created by Arber on 11/01/2017.
 */
public class RoommateWaitingPresenterImpl extends AbstractPresenter implements RoommateWaitingPresenter, RoommateWaitingInteractor.Callback {

    private static final String TAG = "RoommateWaitingPresenter";

    private RoommateWaitingPresenter.View mView;

    public RoommateWaitingPresenterImpl(MainThread mainThread,
                                        View view) {
        super(mainThread);
        mView = view;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        Log.d(TAG, "inside onError: " + message);
        mView.hideProgress();
        mView.showError(message);
    }

    @Override
    public void notifyError(String errorMessage) {

    }

    @Override
    public void onApartmentReady(ApartmentProfile apartmentProfile) {
        Log.d(TAG, "onApartmentReady: " + apartmentProfile.toString());
        userState.setApartmentProfile(apartmentProfile);
        mView.hideProgress();
        mView.changeToMatchingActivity();
    }

    @Override
    public void listenToDB() {
//        String roommateId = userState.getRoommateId();
        RoommateProfile roommateProfile = userState.getRoommateProfile();
        Log.d(TAG, "listenToDB: " + roommateProfile.getRoommateId());
        RoommateWaitingInteractor roommateWaitingInteractor = new RoommateWaitingInteractorImpl(mMainThread, this, roommateProfile);
        roommateWaitingInteractor.execute();
    }
}
