package com.flatshare.presentation.presenters.profile.impl;

import android.widget.ImageView;
import android.widget.VideoView;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.interactors.matching.EmailRetrieverInteractor;
import com.flatshare.domain.interactors.matching.impl.EmailRetrieverInteractorImpl;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.profile.ProfileInteractor;
import com.flatshare.domain.interactors.profile.impl.ApartmentProfileInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.profile.ApartmentProfilePresenter;

import java.util.Map;

/**
 * Created by Arber on 11/12/2016.
 */

public class ApartmentProfilePresenterImpl extends AbstractPresenter implements ApartmentProfilePresenter,
        ProfileInteractor.Callback, MediaInteractor.UploadCallback, EmailRetrieverInteractor.Callback {


    private ApartmentProfilePresenter.View mView;

    public ApartmentProfilePresenterImpl(MainThread mainThread,
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
    public void onSentSuccess(int classificationId) {

        mView.hideProgress();
        mView.changeToApartmentSettings();
    }

    @Override
    public void onSentFailure(String error) {
        userState.setApartmentUserProfile(null);
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void sendProfile(ApartmentUserProfile apartmentUserProfile) {

        mView.showProgress();
        userState.setApartmentUserProfile(apartmentUserProfile);
        ProfileInteractor interactor = new ApartmentProfileInteractorImpl(mMainThread, this, apartmentUserProfile);
        interactor.execute();

    }

    @Override
    public void uploadImage(ImageView imageView) {

    }

    @Override
    public void uploadVideo(VideoView videoView) {

    }

    @Override
    public void getUserEmails() {
        EmailRetrieverInteractor emailRetrieverInteractor = new EmailRetrieverInteractorImpl(mMainThread, this);
        emailRetrieverInteractor.execute();
    }

    @Override
    public void onError(String error) {

        mView.showError(error);
    }

    @Override
    public void onUploadSuccess() {
        //TODO: notify view that media was uploaded
    }

    @Override
    public void emailsRetrievedSuccess(Map<String, String> emailIdMap) {
        mView.updateAdapter(emailIdMap);
    }

    @Override
    public void emailsRetrievedFailure(String errorMessage) {
        mView.showError(errorMessage);
    }
}