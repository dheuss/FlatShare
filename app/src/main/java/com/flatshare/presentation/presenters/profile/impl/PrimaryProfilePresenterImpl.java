package com.flatshare.presentation.presenters.profile.impl;

import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.profile.ProfileInteractor;
import com.flatshare.domain.interactors.profile.impl.PrimaryProfileInteractorImpl;
import com.flatshare.presentation.presenters.profile.PrimaryProfilePresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 08/12/2016.
 */

public class PrimaryProfilePresenterImpl extends AbstractPresenter implements PrimaryProfilePresenter,
        ProfileInteractor.Callback {


    private PrimaryProfilePresenter.View mView;

    public PrimaryProfilePresenterImpl(MainThread mainThread,
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
    public void onSentSuccess(int classificationId) {

        mView.hideProgress();
        if (classificationId == 0) {
            mView.changeToTenantProfile();
        } else {
            mView.changeToApartmentProfile();
        }
    }

    @Override
    public void onSentFailure(String error) {
        userState.setPrimaryUserProfile(null);
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void sendProfile(PrimaryUserProfile primaryUserProfile) {

        mView.showProgress();

        if (userState.getPrimaryUserProfile() != null) { // already exists, update profile

            primaryUserProfile.setEmail(userState.getPrimaryUserProfile().getEmail());
            userState.setPrimaryUserProfile(primaryUserProfile);

        } else {

            userState.setPrimaryUserProfile(primaryUserProfile);
        }

        ProfileInteractor interactor = new PrimaryProfileInteractorImpl(mMainThread, this, primaryUserProfile);
        interactor.execute();

    }

    @Override
    public void createQRCode() {
        // TODO: use some interactor to get userId..
        mView.changeToRoomateQR("some User ID!");
    }


    @Override
    public void onError(String message) {

        mView.showError(message);
    }
}
