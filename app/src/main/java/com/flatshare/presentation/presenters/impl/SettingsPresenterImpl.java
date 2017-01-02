package com.flatshare.presentation.presenters.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.ChangeMailAddressDataType;
import com.flatshare.domain.datatypes.auth.ChangePasswordDataType;
import com.flatshare.domain.datatypes.auth.ResetDataType;
import com.flatshare.network.AuthenticationManager;
import com.flatshare.network.impl.AuthenticationManagerImpl;
import com.flatshare.presentation.presenters.SettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 29.12.2016.
 */

public class SettingsPresenterImpl extends AbstractPresenter implements SettingsPresenter,
        AuthenticationManager.ResetCallBack,
        AuthenticationManager.ChangeMailCallBack,
        AuthenticationManager.ChangePasswordCallBack,
        AuthenticationManager.RemoveUserCallBack,
        AuthenticationManager.LogOutCallBack {

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
    public void onResetSuccessful() {

    }

    @Override
    public void onResetFailed(String error) {

    }


    @Override
    public void onChangePasswordSuccessful() {

    }

    @Override
    public void onChangePasswordFailed(String error) {

    }

    @Override
    public void onLogOutSuccessful() {

    }

    @Override
    public void onLogOutFailed(String error) {

    }

    @Override
    public void onRemoveUserSuccessful() {

    }

    @Override
    public void onRemoveUserFailed(String error) {

    }

    @Override
    public void onChangeMailSuccessful() {

    }

    @Override
    public void onChangedMailFailed(String error) {

    }

    @Override
    public void reset(ResetDataType resetDataType) {
        mView.showProgress();
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);
        authenticationManager.reset(resetDataType);
    }

    @Override
    public void changeMail(ChangeMailAddressDataType changeMailAddressDataType) {
        mView.showProgress();
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);
        authenticationManager.changeMail(changeMailAddressDataType);
    }

    @Override
    public void changePassword(ChangePasswordDataType changePasswordDataType) {
        mView.showProgress();
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);
        authenticationManager.changePassword(changePasswordDataType);
    }

    @Override
    public void removeUser() {
        mView.showProgress();
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);
        authenticationManager.removeUser();
    }

    @Override
    public void logOut() {
        mView.showProgress();
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);
        authenticationManager.logOut();
    }
}
