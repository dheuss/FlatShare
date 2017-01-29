package com.flatshare.presentation.presenters.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.matching.PMatchesListenerInteractor;
import com.flatshare.domain.interactors.matching.impl.PMatchesListenerInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matching.MatchesPresenter;

/**
 * Created by Arber on 29/01/2017.
 */

public class MatchesPresenterImpl extends AbstractPresenter
        implements MatchesPresenter, PMatchesListenerInteractor.Callback{


    private static final String TAG = "MatchesPresenter";

    private MatchesPresenter.View mView;

    public MatchesPresenterImpl(MainThread mainThread,
                                          View view) {
        super(mainThread);

        Log.d(TAG, "inside constructor");

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

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onMatchCreated(String key) {
        mView.incrementBadgeCounter();
    }

    @Override
    public void setPotentialMatchesListener() {
//        PMatchesListenerInteractor pMatchesListenerInteractor = new PMatchesListenerInteractorImpl(mMainThread, this);
//        pMatchesListenerInteractor.execute();
    }
}