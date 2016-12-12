package com.flatshare.presentation.presenters.impl;

import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.impl.PrimaryProfileInteractorImpl;
import com.flatshare.presentation.presenters.PrimaryProfilePresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 08/12/2016.
 */

public class PrimaryProfilePresenterImpl extends AbstractPresenter implements PrimaryProfilePresenter,
        ProfileInteractor.Callback {


    private PrimaryProfilePresenter.View mView;

    public PrimaryProfilePresenterImpl(Executor executor, MainThread mainThread,
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
        if(classificationId == 0){
            mView.changeToTenantProfile();
        } else {
            mView.changeToApartmentProfile();
        }
    }

    @Override
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void sendProfile(PrimaryUserProfile primaryUserProfile) {

        ProfileInteractor interactor = new PrimaryProfileInteractorImpl(mExecutor,mMainThread,this,primaryUserProfile);
        interactor.execute();

    }


    @Override
    public void onError(String message) {

        mView.showError(message);
    }
}