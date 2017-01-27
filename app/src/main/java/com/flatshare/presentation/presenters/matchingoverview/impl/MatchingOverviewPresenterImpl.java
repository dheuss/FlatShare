package com.flatshare.presentation.presenters.matchingoverview.impl;

import com.flatshare.R;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.interactors.matchingoverview.MatchingOverviewInteractor;
import com.flatshare.domain.interactors.matchingoverview.impl.MatchingOverviewInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;

import java.util.ArrayList;
import java.util.List;


public class MatchingOverviewPresenterImpl extends AbstractPresenter implements MatchingOverviewPresenter,
        MatchingOverviewInteractor.Callback {

    private static final String TAG = "MatchingOverviewPresenterImpl";
    private MatchingOverviewPresenter.View mView;

    public MatchingOverviewPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        mView = view;
    }

    @Override
    public void onSentSuccess() {
        //TODO get Matching info

        //mView.generateMatchingOverview(matchingTitleList, matchingImageList);
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
        MatchingOverviewInteractor machingOverviewInteractor = new MatchingOverviewInteractorImpl(mMainThread, this);
        machingOverviewInteractor.execute();
        //TODO Welche Daten werden benötigt um Match zu löschen
    }
}
