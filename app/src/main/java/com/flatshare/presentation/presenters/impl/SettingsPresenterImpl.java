package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.presentation.presenters.SettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 29.12.2016.
 */

public class SettingsPresenterImpl extends AbstractPresenter implements SettingsPresenter {

    private SettingsPresenter.View mView;

    public SettingsPresenterImpl(MainThread mainThread) {
        super(mainThread);
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
