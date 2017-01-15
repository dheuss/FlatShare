package com.flatshare.presentation.presenters.settings.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.interactors.settings.ProfileTenantSettingsFilterInteractor;
import com.flatshare.domain.interactors.settings.impl.ProfileTenantSettingsFilterInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.settings.ProfileTenantSettingsFilterPresenter;

/**
 * Created by david on 15.01.2017.
 */

public class ProfileTenantSettingsFilterPresenterImpl extends AbstractPresenter implements ProfileTenantSettingsFilterPresenter, ProfileTenantSettingsFilterInteractor.Callback  {

    private ProfileTenantSettingsFilterPresenter.View mView;

    public ProfileTenantSettingsFilterPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        this.mView = view;
    }

    @Override
    public void onSentSuccess() {
        mView.hideProgress();
        mView.changeToProfileTenantSettingsActivity();
    }

    @Override
    public void onSentFailure(String error) {
        userState.getTenantProfile().setTenantFilterSettings(null);
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void changeFilterSettings(TenantFilterSettings tenantFilterSettings) {
        mView.showProgress();
        userState.getTenantProfile().setTenantFilterSettings(tenantFilterSettings);
        ProfileTenantSettingsFilterInteractor interactor = new ProfileTenantSettingsFilterInteractorImpl(mMainThread, this, tenantFilterSettings);
        interactor.execute();
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
        mView.hideProgress();
        onError(message);
    }
}
