package com.flatshare.presentation.presenters.settings.impl;

import android.widget.ImageView;
import android.widget.VideoView;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.interactors.settings.ProfileApartmentSettingsInteractor;
import com.flatshare.domain.interactors.settings.impl.ProfileApartmentSettingsInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.settings.ProfileApartmentSettingsPresenter;

/**
 * Created by david on 15.01.2017.
 */

public class ProfileApartmentSettingsPresenterImpl extends AbstractPresenter implements ProfileApartmentSettingsPresenter, ProfileApartmentSettingsInteractor.Callback {

    private ProfileApartmentSettingsPresenter.View mView;

    public ProfileApartmentSettingsPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        this.mView = view;
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
    public void changeApartmentProfile(ApartmentProfile apartmentProfile) {
        mView.showProgress();
        userState.setApartmentProfile(apartmentProfile);
        ProfileApartmentSettingsInteractor interactor = new ProfileApartmentSettingsInteractorImpl(mMainThread, this, apartmentProfile);
        interactor.execute();
    }

    @Override
    public void uploadImage(ImageView imageView) {

    }

    @Override
    public void uploadVideo(VideoView videoView) {

    }

    @Override
    public void getUserEmails() {

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
