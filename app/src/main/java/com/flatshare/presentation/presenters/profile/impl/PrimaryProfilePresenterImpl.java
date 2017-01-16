package com.flatshare.presentation.presenters.profile.impl;

import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.profile.PrimaryProfileInteractor;
import com.flatshare.domain.interactors.profile.impl.PrimaryProfileInteractorImpl;
import com.flatshare.presentation.presenters.profile.PrimaryProfilePresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 08/12/2016.
 */

public class PrimaryProfilePresenterImpl extends AbstractPresenter implements PrimaryProfilePresenter,
        PrimaryProfileInteractor.Callback {


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
    public void onProfileCreated(PrimaryUserProfile primaryUserProfile, UserProfile secondaryProfile) {

        userState.setPrimaryUserProfile(primaryUserProfile);
        int classificationId = primaryUserProfile.getClassificationId();

        mView.hideProgress();

        if (classificationId == ProfileType.TENANT.getValue()) {
            userState.setTenantProfile((TenantProfile) secondaryProfile);
            mView.changeToTenantProfile();
        } else if (classificationId == ProfileType.ROOMMATE.getValue()) {
            userState.setRoommateProfile((RoommateProfile) secondaryProfile);
            mView.changeToRoommateActivity();
        } else {

            mView.showError("Invalid  classificationId: " + classificationId);

        }
    }

    @Override
    public void onSentFailure(String error) {
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

        PrimaryProfileInteractor interactor = new PrimaryProfileInteractorImpl(mMainThread, this, primaryUserProfile);
        interactor.execute();

    }

    @Override
    public void onError(String message) {

        mView.showError(message);
    }
}
