package com.flatshare.presentation.presenters.auth.impl;


import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.init.InitInteractor;
import com.flatshare.domain.interactors.auth.LoginInteractor;
import com.flatshare.domain.interactors.init.impl.InitInteractorImpl;
import com.flatshare.domain.interactors.auth.impl.LoginInteractorImpl;
import com.flatshare.presentation.presenters.auth.LoginPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 05/12/2016.
 */

public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter,
        LoginInteractor.Callback, InitInteractor.Callback {


    private static final String TAG = "LoginPresenter";
    private LoginPresenter.View mView;
    private String email;

    public LoginPresenterImpl(MainThread mainThread,
                              View view) {
        super(mainThread);
        mView = view;
    }

    @Override
    public void resume() {
        Log.d(TAG, "RESUMING!!!");
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
        mView.showError(message);
    }


    @Override
    public void login(LoginDataType loginDataType) {

        // TEST
//        TestInteractor testInteractor = new TestInteractor(mMainThread, 20);
//        testInteractor.execute();
        // TEST

        mView.showProgress();

        if (userState.getPrimaryUserProfile() != null) {
            userState.getPrimaryUserProfile().setEmail(loginDataType.getEmail());
        } else {
            PrimaryUserProfile primaryUserProfile = new PrimaryUserProfile();
            primaryUserProfile.setEmail(loginDataType.getEmail());
            userState.setPrimaryUserProfile(primaryUserProfile);
        }

        if (userState.isLoggedIn()) {
            onLoginSuccess();
        } else {
            LoginInteractor loginInteractor = new LoginInteractorImpl(mMainThread, this, loginDataType);
            loginInteractor.execute();
        }
    }

    @Override
    public void loginGoogle() {
        mView.showProgress();
    }

    @Override
    public void loginFacebook() {
        mView.showProgress();
    }

    @Override
    public void onTenantFound(PrimaryUserProfile primaryUserProfile, TenantProfile tenantProfile) {
        userState.setPrimaryUserProfile(primaryUserProfile);
        userState.setTenantProfile(tenantProfile);

        mView.hideProgress();
        if (tenantProfile.isDone()) {
            if (tenantProfile.getTenantFilterSettings() == null) {
                mView.changeToTenantSettingsActivity();
            } else {
                mView.changeToMatchingActivity();
            }
        } else {
            mView.changeToTenantProfileActivity();
        }
    }

    @Override
    public void onRoommateFound(PrimaryUserProfile primaryUserProfile, RoommateProfile roommateProfile) {
        userState.setPrimaryUserProfile(primaryUserProfile);
        userState.setRoommateProfile(roommateProfile);
        mView.hideProgress();

        if(roommateProfile.isAvailable()) {
            mView.notifyRoommateGenerateQR(roommateProfile.getRoommateId());
        } else {
            mView.changeToRoommateWaitingActivity();
        }
    }

    @Override
    public void onApartmentFound(PrimaryUserProfile primaryUserProfile, RoommateProfile roommateProfile, ApartmentProfile apartmentProfile) {
        userState.setPrimaryUserProfile(primaryUserProfile);
        userState.setRoommateProfile(roommateProfile);
        userState.setApartmentProfile(apartmentProfile);

        mView.hideProgress();
        if (apartmentProfile.isDone()) {
            if (apartmentProfile.getApartmentFilterSettings() == null) {
                mView.changeToApartmentSettingsActivity();
            } else {
                mView.changeToMatchingActivity();
            }
        } else {
            mView.changeToApartmentProfileActivity();
        }

    }

    @Override
    public void onReceivedFailure(String error) {
        mView.hideProgress();
        onError(error);
        mView.changeToProfileActivity();
    }

    @Override
    public void onLoginSuccess() {

        userState.setLoggedIn(true);

        InitInteractor initInteractor = new InitInteractorImpl(mMainThread, this);
        initInteractor.execute();

        destroy();
    }

    @Override
    public void onLoginFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }
}