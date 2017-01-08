package com.flatshare.presentation.presenters.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.matching.ShowDetailProfilApartmentInteractor;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matching.ShowDetailProfilApartmentPresenter;

/**
 * Created by david on 08.01.2017.
 */

public class ShowDetailProfilApartmentPresenterImpl extends AbstractPresenter implements ShowDetailProfilApartmentPresenter, ShowDetailProfilApartmentInteractor.Callback {

    private static final String TAG = "ShowDetailProfilApartmentPresenterImpl";

    private ShowDetailProfilApartmentPresenter.View mView;

    public ShowDetailProfilApartmentPresenterImpl(MainThread mainThread, View view) {
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
