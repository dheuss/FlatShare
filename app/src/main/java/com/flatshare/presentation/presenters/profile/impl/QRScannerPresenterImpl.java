package com.flatshare.presentation.presenters.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.profile.QRScannerInteractor;
import com.flatshare.domain.interactors.profile.impl.QRScannerInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.profile.QRScannerPresenter;

/**
 * Created by Arber on 10/01/2017.
 */

public class QRScannerPresenterImpl extends AbstractPresenter implements QRScannerPresenter,
        QRScannerInteractor.Callback {


    private static final String TAG = "QRScannerPresenter";
    private QRScannerPresenter.View mView;

    public QRScannerPresenterImpl(MainThread mainThread,
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

        mView.showError(message);
    }

    @Override
    public void addRoomate(String roommateId) {
        QRScannerInteractor qrScannerInteractor = new QRScannerInteractorImpl(mMainThread, this, roommateId);
        qrScannerInteractor.execute();
    }

    @Override
    public void onFailure(String errorMessage) {
        onError(errorMessage);
    }

    @Override
    public void onSuccess(Pair<String, String> idNicknamePair) {
        userState.getRoommateProfile().setAvailable(false);
        mView.displayReadCode(idNicknamePair);
    }
}
