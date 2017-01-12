package com.flatshare.presentation.presenters.profile.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.profile.QRScannerInteractor;
import com.flatshare.domain.interactors.profile.impl.QRScannerInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.profile.QRScannerPresenter;

/**
 * Created by Arber on 10/01/2017.
 */

public class QRScannerPresenterImpl extends AbstractPresenter implements QRScannerPresenter,
        QRScannerInteractor.Callback {


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
    public void onSuccess(String roommateId) {
        userState.getRoommateProfile().setAvailable(false);
        mView.displayReadCode(roommateId);
    }
}
