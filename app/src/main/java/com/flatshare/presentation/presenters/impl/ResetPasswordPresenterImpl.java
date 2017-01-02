package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.datatypes.auth.ResetDataType;
import com.flatshare.presentation.presenters.ResetPasswordPresenter;
import com.flatshare.domain.MainThread;
import com.flatshare.network.AuthenticationManager;
import com.flatshare.network.impl.AuthenticationManagerImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 19.12.2016.
 */

public class ResetPasswordPresenterImpl extends AbstractPresenter implements ResetPasswordPresenter, AuthenticationManager.ResetCallBack {


    private ResetPasswordPresenter.View mView;

    public ResetPasswordPresenterImpl(MainThread mainThread, View view) {
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
        mView.showError(message);
    }

    @Override
    public void onResetSuccessful() {
        mView.hideProgress();
        mView.changeToProfileActivity();
    }

    @Override
    public void onResetFailed(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void reset(ResetDataType resetDataType) {
        mView.showProgress();
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);
        authenticationManager.reset(resetDataType);
    }
}
