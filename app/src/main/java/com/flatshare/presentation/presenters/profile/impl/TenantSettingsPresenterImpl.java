package com.flatshare.presentation.presenters.profile.impl;

import android.util.Log;

import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.profile.FilterSettingsInteractor;
import com.flatshare.domain.interactors.profile.impl.TenantSettingsInteractorImpl;
import com.flatshare.presentation.presenters.profile.TenantSettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 11/12/2016.
 */

public class TenantSettingsPresenterImpl extends AbstractPresenter implements TenantSettingsPresenter,
        FilterSettingsInteractor.Callback {


    private static final String TAG = "TenantSettingsPresenter";
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
        userState.getTenantUserProfile().setTenantFilterSettings(null);
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void onError(String message) {
        mView.showError(message);
    }

    @Override
    public void sendFilterSettings(TenantFilterSettings tenantFilterSettings) {
        mView.showProgress();

        Log.d(TAG, "userstate null? " + (userState == null) + " or is tenantuserprofile null? " + (userState.getTenantUserProfile() == null));

        userState.getTenantUserProfile().setTenantFilterSettings(tenantFilterSettings);
        FilterSettingsInteractor interactor = new TenantSettingsInteractorImpl(mMainThread, this, tenantFilterSettings);
        interactor.execute();
    }
}