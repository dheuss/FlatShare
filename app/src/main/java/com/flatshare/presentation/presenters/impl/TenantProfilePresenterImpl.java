package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.impl.TenantProfileInteractorImpl;
import com.flatshare.presentation.presenters.TenantProfilePresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 11/12/2016.
 */

public class TenantProfilePresenterImpl extends AbstractPresenter implements TenantProfilePresenter,
        ProfileInteractor.Callback {


    private TenantProfilePresenter.View mView;

    public TenantProfilePresenterImpl(Executor executor, MainThread mainThread,
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
    public void onSentSuccess(int classificationId) {

        mView.hideProgress();
        mView.changeToTenantSettings();
    }

    @Override
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void sendProfile(TenantUserProfile tenantUserProfile) {

        ProfileInteractor interactor = new TenantProfileInteractorImpl(mExecutor, mMainThread, this, tenantUserProfile);
        interactor.execute();

    }


    @Override
    public void onError(String message) {

        mView.showError(message);
    }
}
