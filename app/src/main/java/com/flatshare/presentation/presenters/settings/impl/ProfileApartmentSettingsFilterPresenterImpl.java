package com.flatshare.presentation.presenters.settings.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.interactors.settings.ProfileApartmentSettingsFilterInteractor;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.settings.ProfileApartmentSettingsFilterPresenter;

/**
 * Created by david on 15.01.2017.
 */

public class ProfileApartmentSettingsFilterPresenterImpl extends AbstractPresenter implements ProfileApartmentSettingsFilterPresenter, ProfileApartmentSettingsFilterInteractor.Callback {

    private ProfileApartmentSettingsFilterPresenter.View mView;

    public ProfileApartmentSettingsFilterPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        this.mView = view;
    }

    @Override
    public void onSentSuccess() {
        mView.hideProgress();
        mView.changeToProfileApartmentSettingsActivity();
    }

    @Override
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void changeApartmentFilterSettings(ApartmentFilterSettings apartmentFilterSettings) {
        mView.showProgress();

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
