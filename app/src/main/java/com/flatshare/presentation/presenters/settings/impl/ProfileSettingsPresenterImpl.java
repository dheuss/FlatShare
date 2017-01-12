package com.flatshare.presentation.presenters.settings.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.profile.impl.TenantSettingsInteractorImpl;
import com.flatshare.domain.interactors.settings.ProfileSettingsInteractor;
import com.flatshare.domain.interactors.settings.impl.ProfileSettingsInteractorImpl;
import com.flatshare.presentation.presenters.settings.ProfileSettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 28.12.2016.
 */

public class ProfileSettingsPresenterImpl extends AbstractPresenter implements ProfileSettingsPresenter,
        ProfileSettingsInteractor.Callback {

    private ProfileSettingsPresenter.View mView;

    public ProfileSettingsPresenterImpl(MainThread mainThread, View view) {
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

    }

    @Override
    public void changeProfile(TenantProfile tenantProfile) {
        mView.showProgress();
        userState.setTenantProfile(tenantProfile);
        ProfileSettingsInteractor interactor = new ProfileSettingsInteractorImpl(mMainThread, this, tenantProfile);
        interactor.execute();
    }

    @Override
    public void onSentSuccess(int classificationId) {
        mView.hideProgress();
        mView.changeToMatchingActivity();
    }

    @Override
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }
}
