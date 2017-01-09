package com.flatshare.presentation.presenters.profile.impl;

import com.flatshare.domain.datatypes.db.common.ProfileType;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.profile.ProfileInteractor;
import com.flatshare.domain.interactors.profile.impl.PrimaryProfileInteractorImpl;
import com.flatshare.domain.interactors.profile.impl.RoommateProfileInteractorImpl;
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

        if (classificationId == ProfileType.TENANT.getValue()) {
            mView.changeToTenantProfile();
        } else if (classificationId == ProfileType.APARTMENT.getValue()) {
            mView.changeToApartmentProfile();
        } else if (classificationId == ProfileType.ROOMMATE.getValue()) {
            mView.changeToRoomateQR(userState.getRoommateId());
        } else {

            mView.showError("Invalid  classificationId: " + classificationId);

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
    public void createRoommateProfile() {
        mView.showProgress();

        if (userState.getRoommateProfile() != null) { // already exists, update profile

            mView.changeToRoomateQR(userState.getRoommateId());

        } else { // create it

            ProfileInteractor interactor = new RoommateProfileInteractorImpl(mMainThread, this);
            interactor.execute();

        }

    }

    @Override
    public void onError(String message) {

        mView.showError(message);
    }
}
