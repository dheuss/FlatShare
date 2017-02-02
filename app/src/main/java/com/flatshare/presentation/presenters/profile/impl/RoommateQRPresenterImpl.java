package com.flatshare.presentation.presenters.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.matching.RoommateQRInteractor;
import com.flatshare.domain.interactors.matching.impl.RoommateQRInteractorImpl;
import com.flatshare.domain.interactors.profile.SecondaryProfileInteractor;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.profile.RoommateQRPresenter;

/**
 * Created by Arber on 08/01/2017.
 */
public class RoommateQRPresenterImpl extends AbstractPresenter implements RoommateQRPresenter, SecondaryProfileInteractor.Callback, RoommateQRInteractor.Callback {

    private static final String TAG = "RoommateQRPresenter";

    private RoommateQRPresenter.View mView;

    public RoommateQRPresenterImpl(MainThread mainThread,
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
    public void listenToDB(String roommateId) {
        RoommateQRInteractor roommateQRInteractor = new RoommateQRInteractorImpl(mMainThread, this, roommateId);
        roommateQRInteractor.execute();
    }

    @Override
    public void checkIfApartmentProfileCreated() {

    }

    @Override
    public void notifyError(String errorMessage) {
        onError(errorMessage);
    }

    @Override
    public void onCodeRead(RoommateProfile roommateProfile) {
        if (roommateProfile != null) {
            userState.setRoommateProfile(roommateProfile);
        } else {
            userState.getRoommateProfile().setAvailable(false);
        }
        mView.onQRCodeRead();
    }

    @Override
    public void onSentFailure(String error) {
        notifyError(error);
    }

    @Override
    public void onProfileCreated(UserProfile profile) {
        mView.hideProgress();
        userState.setRoommateProfile((RoommateProfile) profile);
//        mView.changeToApartmentSettings();
    }
}
