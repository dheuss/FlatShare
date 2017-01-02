package com.flatshare.presentation.presenters.impl;


import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.interactors.InitInteractor;
import com.flatshare.domain.interactors.impl.InitInteractorImpl;
import com.flatshare.network.AuthenticationManager;
import com.flatshare.network.impl.AuthenticationManagerImpl;
import com.flatshare.presentation.presenters.LoginPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 05/12/2016.
 */

public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter,
        AuthenticationManager.LoginCallback, InitInteractor.Callback {


    private static final String TAG = "LoginPresenter";
    private LoginPresenter.View mView;

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
    public void onLoginSuccessful() {

        InitInteractor initInteractor = new InitInteractorImpl(mMainThread, this);
        initInteractor.execute();

    }

    @Override
    public void onLoginFailed(String error) {

        mView.hideProgress();
        onError(error);

    }

    @Override
    public void login(LoginDataType loginDataType) {

        // initialize the interactor
//        LoginInteractor interactor = new LoginInteractorImpl(
//                mExecutor,
//                mMainThread,
//                this
//        );

        mView.showProgress();
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);

//        // run the interactor
        authenticationManager.login(loginDataType);

    }

    @Override
    public void onReceivedSuccess(PrimaryUserProfile primaryUserProfile, TenantUserProfile tenantUserProfile, ApartmentUserProfile apartmentUserProfile) {
        userState.setApartmentUserProfile(apartmentUserProfile);
        userState.setTenantUserProfile(tenantUserProfile);
        userState.setPrimaryUserProfile(primaryUserProfile);

        mView.hideProgress();
        mView.changeToMatchingActivity();
    }

    @Override
    public void onReceivedFailure(String error) {
        mView.hideProgress();
        onError(error);
        mView.changeToProfileActivity();
    }
}
