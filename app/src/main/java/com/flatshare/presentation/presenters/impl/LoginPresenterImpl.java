package com.flatshare.presentation.presenters.impl;


import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.network.AuthenticationManager;
import com.flatshare.network.impl.AuthenticationManagerImpl;
import com.flatshare.presentation.presenters.LoginPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 05/12/2016.
 */

public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter,
        AuthenticationManager.LoginCallback {


    private LoginPresenter.View mView;

    public LoginPresenterImpl(Executor executor, MainThread mainThread,
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
    public void onLoginSuccessful() {

        mView.hideProgress();
        mView.changeToProfileActivity();
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

        AuthenticationManager authenticationManager = new AuthenticationManagerImpl(this);

//        // run the interactor
        authenticationManager.login(loginDataType);

    }
}
