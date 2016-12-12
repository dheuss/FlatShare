package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.impl.ApartmentProfileInteractorImpl;
import com.flatshare.presentation.presenters.ApartmentProfilePresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 11/12/2016.
 */

public class ApartmentProfilePresenterImpl extends AbstractPresenter implements ApartmentProfilePresenter,
        ProfileInteractor.Callback {


    private ApartmentProfilePresenter.View mView;

    public ApartmentProfilePresenterImpl(Executor executor, MainThread mainThread,
                                         View view) {
        super(executor, mainThread);

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
    public void onSentSuccess(int classificationId) {

        mView.hideProgress();
        mView.changeToApartmentSettings();
    }

    @Override
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void sendProfile(ApartmentUserProfile apartmentUserProfile) {

        ProfileInteractor interactor = new ApartmentProfileInteractorImpl(mExecutor, mMainThread, this, apartmentUserProfile);
        interactor.execute();

    }


    @Override
    public void onError(String message) {

        mView.showError(message);
    }
}
