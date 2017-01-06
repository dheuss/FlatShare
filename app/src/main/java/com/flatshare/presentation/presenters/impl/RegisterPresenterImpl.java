package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.RegisterInteractor;
import com.flatshare.domain.interactors.impl.RegisterInteractorImpl;
import com.flatshare.network.AuthenticationManager;
import com.flatshare.network.impl.AuthenticationManagerImpl;
import com.flatshare.presentation.presenters.RegisterPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 11/12/2016.
 */

public class RegisterPresenterImpl extends AbstractPresenter implements RegisterPresenter,
        RegisterInteractor.Callback{


    private RegisterPresenter.View mView;

    public RegisterPresenterImpl(MainThread mainThread,
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
    public void onError(String message) {

        mView.showError(message);
    }

    @Override
    public void register(RegisterDataType registerDataType) {
        RegisterInteractor registerInteractor = new RegisterInteractorImpl(mMainThread,this, registerDataType);
        registerInteractor.execute();
    }

    @Override
    public void onRegisterSuccess() {
        mView.hideProgress();
        mView.changeToProfileActivity();
    }

    @Override
    public void onRegisterFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }
}
