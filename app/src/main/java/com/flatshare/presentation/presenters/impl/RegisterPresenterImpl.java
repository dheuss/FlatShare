package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.network.AuthenticationManager;
import com.flatshare.network.impl.AuthenticationManagerImpl;
import com.flatshare.presentation.presenters.RegisterPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 11/12/2016.
 */

public class RegisterPresenterImpl extends AbstractPresenter implements RegisterPresenter,
        AuthenticationManager.RegisterCallBack{


    private RegisterPresenter.View mView;

    public RegisterPresenterImpl(Executor executor, MainThread mainThread,
                                 View view) {
        super(executor, mainThread);

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
    public void onRegisterSuccessful() {
        mView.hideProgress();
        mView.changeToProfileActivity();
    }

    @Override
    public void onRegisterFailed(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void register(RegisterDataType registerDataType) {
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);

//        // run the interactor
        authenticationManager.register(registerDataType);
    }
}
