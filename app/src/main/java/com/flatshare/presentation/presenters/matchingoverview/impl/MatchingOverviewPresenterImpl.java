package com.flatshare.presentation.presenters.matchingoverview.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.matchingoverview.MatchingOverviewInteractor;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;


public class MatchingOverviewPresenterImpl extends AbstractPresenter implements MatchingOverviewPresenter,
        MatchingOverviewInteractor.Callback{

    private static final String TAG = "MatchingOverviewPresenterImpl";
    private MatchingOverviewPresenter.View mView;

    public MatchingOverviewPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        mView = view;
    }

    @Override
    public void onSentSuccess() {

    }

    @Override
    public void onSentFailure(String error) {

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

    @Override
    public void deleteMatch() {

    }
}
