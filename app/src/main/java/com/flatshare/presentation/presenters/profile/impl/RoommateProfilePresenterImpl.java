package com.flatshare.presentation.presenters.profile.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.profile.SecondaryProfileInteractor;
import com.flatshare.domain.interactors.profile.UniqueNicknameInteractor;
import com.flatshare.domain.interactors.profile.impl.RoommateProfileInteractorImpl;
import com.flatshare.domain.interactors.profile.impl.UniqueNicknameInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.profile.RoommateProfilePresenter;

/**
 * Created by Arber on 16/01/2017.
 */

public class RoommateProfilePresenterImpl extends AbstractPresenter implements RoommateProfilePresenter,
        SecondaryProfileInteractor.Callback, UniqueNicknameInteractor.Callback {


    private static final String TAG = "TenantProfilePresenter";
    private RoommateProfilePresenter.View mView;

    public RoommateProfilePresenterImpl(MainThread mainThread,
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
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void onProfileCreated(UserProfile profile) {
        RoommateProfile roommateProfile = (RoommateProfile) profile;
        roommateProfile.setDone(true);
        userState.setRoommateProfile(roommateProfile);
        mView.hideProgress();
        if(roommateProfile.isOwner()){
            mView.changeToApartmentProfileActivity();
        } else {
            mView.changeToRoommateQRActivity(roommateProfile.getId());
        }
    }

    @Override
    public void sendProfile(RoommateProfile roommateProfile) {
        mView.showProgress();

        roommateProfile.setRoommateId(userState.getRoommateId());
        SecondaryProfileInteractor interactor = new RoommateProfileInteractorImpl(mMainThread, this, roommateProfile);
        interactor.execute();
    }

    @Override
    public void checkNicknameUnique(String nickname) {
        UniqueNicknameInteractor uniqueNicknameInteractor = new UniqueNicknameInteractorImpl(mMainThread, this, nickname);
        uniqueNicknameInteractor.execute();
    }

    @Override
    public void nicknameResult(boolean unique){
        if(unique){
            mView.onNicknameUnique();
        } else {
            mView.onNicknameError("This nickname is already taken, please pick another one");
        }
    }

    @Override
    public void onRequestFailure(String errorMessage) {
        onError(errorMessage);
    }

    @Override
    public void onError(String error) {
        mView.showError(error);
    }

}
