package com.flatshare.presentation.presenters.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.FilterSettings;
import com.flatshare.domain.interactors.profile.FilterSettingsInteractor;
import com.flatshare.domain.interactors.profile.impl.ApartmentSettingsInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.profile.ApartmentSettingsPresenter;

import java.util.List;

/**
 * Created by Arber on 11/12/2016.
 */

public class ApartmentSettingsPresenterImpl extends AbstractPresenter implements ApartmentSettingsPresenter, FilterSettingsInteractor.Callback{

    private static final String TAG = "ApartmentSettingsPresenterImpl";

    private ApartmentSettingsPresenter.View mView;

    public ApartmentSettingsPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        mView = view;
    }

    @Override
    public void onSentSuccess(FilterSettings apartmentFilterSettings) {
        userState.getApartmentProfile().setApartmentFilterSettings((ApartmentFilterSettings) apartmentFilterSettings);
        mView.hideProgress();
        mView.changeToMatchingActivity();
    }

    @Override
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void sendFilterSettings(ApartmentFilterSettings apartmentFilterSettings) {
        mView.showProgress();
        List<String> roommateIds = userState.getApartmentProfile().getRoommateIds();
        String apartmentId = userState.getRoommateProfile().getApartmentId();
        FilterSettingsInteractor interactor = new ApartmentSettingsInteractorImpl(mMainThread, this, apartmentId, apartmentFilterSettings, roommateIds);
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
        mView.showError(message);
    }
}
