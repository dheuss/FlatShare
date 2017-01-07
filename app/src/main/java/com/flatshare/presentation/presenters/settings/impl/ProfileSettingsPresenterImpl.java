package com.flatshare.presentation.presenters.settings.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.presentation.presenters.settings.ProfileSettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 28.12.2016.
 */

public class ProfileSettingsPresenterImpl extends AbstractPresenter implements ProfileSettingsPresenter {

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
}