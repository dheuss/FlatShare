package com.flatshare.presentation.presenters.auth.impl;


import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.auth.ResetPasswordLoginInteractor;
import com.flatshare.domain.interactors.auth.impl.ResetPasswordLoginInteractorImpl;
import com.flatshare.presentation.presenters.auth.ResetPasswordPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 19.12.2016.
 */

public class ResetPasswordPresenterImpl extends AbstractPresenter implements ResetPasswordPresenter,
        ResetPasswordLoginInteractor.Callback{

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
    public void reset(String email) {
        mView.showProgress();
        ResetPasswordLoginInteractor resetPasswordLoginInteractor = new ResetPasswordLoginInteractorImpl(mMainThread, this, email);
        resetPasswordLoginInteractor.execute();
    }

    @Override
    public void onResetPasswordLoginSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onResetPasswordLoginFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }
}
