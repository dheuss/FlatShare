package com.flatshare.presentation.presenters.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.matching.RoommateWaitingInteractor;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matching.RoommateWaitingPresenter;
import com.flatshare.presentation.ui.activities.profile.RoommateWaitingActivity;

/**
 * Created by Arber on 11/01/2017.
 */
public class RoommateWaitingPresenterImpl extends AbstractPresenter implements RoommateWaitingPresenter, RoommateWaitingInteractor.Callback {

    private static final String TAG = "RoommateWaitingPresenter";

    private RoommateWaitingPresenter.View mView;

    public RoommateWaitingPresenterImpl(MainThread mainThread,
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
    public void onError(String message) {
        Log.d(TAG, "inside onError: " + message);
        mView.hideProgress();
        mView.showError(message);
    }

    @Override
    public void notifyError(String errorMessage) {

    }

    @Override
    public void onApartmentReady() {

    }
}
