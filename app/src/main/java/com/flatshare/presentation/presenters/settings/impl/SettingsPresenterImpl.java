package com.flatshare.presentation.presenters.settings.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.ChangeMailInteractor;
import com.flatshare.domain.interactors.auth.DeleteAccountInteractor;
import com.flatshare.domain.interactors.auth.LogoutInteractor;
import com.flatshare.domain.interactors.auth.impl.ChangeMailInteractorImpl;
import com.flatshare.domain.interactors.auth.impl.DeleteAccountInteractorImpl;
import com.flatshare.domain.interactors.auth.impl.LogoutInteractorImpl;
import com.flatshare.presentation.presenters.settings.SettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 29.12.2016.
 */

public class SettingsPresenterImpl extends AbstractPresenter implements SettingsPresenter,
        LogoutInteractor.Callback,
        ChangeMailInteractor.Callback,
        DeleteAccountInteractor.Callback{

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
    public void changeMailAddress(String newMailAddress) {
        ChangeMailInteractor changeMailInteractor = new ChangeMailInteractorImpl(mMainThread, this, newMailAddress);
        changeMailInteractor.execute();
    }

    @Override
    public void deleteAccount(){
        DeleteAccountInteractor deleteAccountInteractor = new DeleteAccountInteractorImpl(mMainThread, this);
        deleteAccountInteractor.execute();
    }

    @Override
    public void onLogoutSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onLogoutFail(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }


    @Override
    public void onChangeMailSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onChangeMailFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }


    @Override
    public void onDeleteAccountSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onDeleteAccountFailure(String errorMessage){
        mView.hideProgress();
        onError(errorMessage);
    }
}
