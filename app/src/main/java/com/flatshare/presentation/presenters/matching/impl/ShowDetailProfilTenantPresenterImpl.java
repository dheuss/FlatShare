package com.flatshare.presentation.presenters.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.matching.ShowDetailProfilTenantInteractor;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matching.ShowDetailProfilTenantPresenter;

/**
 * Created by david on 08.01.2017.
 */

public class ShowDetailProfilTenantPresenterImpl extends AbstractPresenter implements ShowDetailProfilTenantPresenter, ShowDetailProfilTenantInteractor.Callback {

    private static final String TAG = "ShowDetailProfilTenantPresenterImpl";

    private ShowDetailProfilTenantPresenter.View mView;

    public ShowDetailProfilTenantPresenterImpl(MainThread mainThread, View view) {
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
}
