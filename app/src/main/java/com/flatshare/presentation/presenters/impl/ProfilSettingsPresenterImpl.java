package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.presentation.presenters.ProfilSettingsPresenter;
import com.flatshare.presentation.presenters.SettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.ui.AbstractActivity;

/**
 * Created by david on 28.12.2016.
 */

public class ProfilSettingsPresenterImpl extends AbstractPresenter implements ProfilSettingsPresenter {

    private ProfilSettingsPresenter.View mView;

    public ProfilSettingsPresenterImpl(MainThread mainThread, View view) {
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
