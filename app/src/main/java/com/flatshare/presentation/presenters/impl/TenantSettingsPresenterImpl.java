package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.FilterSettingsInteractor;
import com.flatshare.domain.interactors.impl.TenantSettingsInteractorImpl;
import com.flatshare.presentation.presenters.TenantSettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 11/12/2016.
 */

public class TenantSettingsPresenterImpl extends AbstractPresenter implements TenantSettingsPresenter,
        FilterSettingsInteractor.Callback {


    private TenantSettingsPresenter.View mView;

    public TenantSettingsPresenterImpl(MainThread mainThread,
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
    public void onSentSuccess() {
        mView.hideProgress();
        mView.changeToMatchingActivity();
    }

    @Override
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void onError(String message) {

        mView.showError(message);
    }

    @Override
    public void sendFilterSettings(TenantFilterSettings tenantFilterSettings) {
        FilterSettingsInteractor interactor = new TenantSettingsInteractorImpl(mMainThread, this, tenantFilterSettings);
        interactor.execute();
    }
}