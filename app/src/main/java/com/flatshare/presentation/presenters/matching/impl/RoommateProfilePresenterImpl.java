package com.flatshare.presentation.presenters.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.matching.RoommateQRInteractor;
import com.flatshare.domain.interactors.matching.impl.RoommateQRInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matching.RoommateProfilePresenter;
import com.flatshare.presentation.ui.activities.matching.RoommateQRActivity;

/**
 * Created by Arber on 08/01/2017.
 */
public class RoommateProfilePresenterImpl extends AbstractPresenter implements RoommateProfilePresenter, RoommateQRInteractor.Callback {

    private static final String TAG = "RoommateProfilePresenter";

    private RoommateProfilePresenter.View mView;

    public RoommateProfilePresenterImpl(MainThread mainThread,
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
    public void listenToDB(String roommateId) {
        RoommateQRInteractor roommateQRInteractor = new RoommateQRInteractorImpl(mMainThread, this, roommateId);
        roommateQRInteractor.execute();
    }

    @Override
    public void notifyError(String errorMessage) {

    }

    @Override
    public void onCodeRead() {
        mView.changeToSwipingActivity();
    }
}
