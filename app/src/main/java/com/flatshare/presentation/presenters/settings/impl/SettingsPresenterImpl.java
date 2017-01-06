package com.flatshare.presentation.presenters.settings.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.LogoutInteractor;
import com.flatshare.domain.interactors.auth.impl.LogoutInteractorImpl;
import com.flatshare.presentation.presenters.settings.SettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 29.12.2016.
 */

public class SettingsPresenterImpl extends AbstractPresenter implements SettingsPresenter,
        LogoutInteractor.Callback{

    private static final String TAG = "SettingsPresenterImpl";
    private SettingsPresenter.View mView;

    public SettingsPresenterImpl(MainThread mainThread, View view) {
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
    public void logOut() {
        LogoutInteractor logoutInteractor = new LogoutInteractorImpl(mMainThread, this);
        logoutInteractor.execute();
    }

    @Override
    public void onLogoutSuccess() {
        userState.setLoggedIn(false);
        //TODO: maybe change back to main view?
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onLogoutFail(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }
}
