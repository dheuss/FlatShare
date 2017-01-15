package com.flatshare.presentation.presenters.settings.impl;

import android.net.Uri;
import android.widget.VideoView;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.settings.ProfileTenantSettingsInteractor;
import com.flatshare.domain.interactors.settings.impl.ProfileTenantSettingsInteractorImpl;
import com.flatshare.presentation.presenters.settings.ProfileTenantSettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 28.12.2016.
 */

public class ProfileTenantSettingsPresenterImpl extends AbstractPresenter implements ProfileTenantSettingsPresenter,
        ProfileTenantSettingsInteractor.Callback {

    private ProfileTenantSettingsPresenter.View mView;

    public ProfileTenantSettingsPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        this.mView = view;
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
        ProfileTenantSettingsInteractor interactor = new ProfileTenantSettingsInteractorImpl(mMainThread, this, tenantProfile);
        interactor.execute();
    }

    @Override
    public void uploadVideo(VideoView videoView) {

    }

    @Override
    public void uploadImage(Uri uri) {

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
}
