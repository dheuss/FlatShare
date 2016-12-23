package com.flatshare.presentation.presenters.impl;

import android.widget.ImageView;
import android.widget.VideoView;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.MediaInteractor;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.impl.ApartmentProfileInteractorImpl;
import com.flatshare.domain.interactors.impl.UploadInteractorImpl;
import com.flatshare.presentation.presenters.ApartmentProfilePresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.utils.converters.MediaConverter;

/**
 * Created by Arber on 11/12/2016.
 */

public class ApartmentProfilePresenterImpl extends AbstractPresenter implements ApartmentProfilePresenter,
        ProfileInteractor.Callback, MediaInteractor.UploadCallback {


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
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void sendProfile(ApartmentUserProfile apartmentUserProfile) {


        ProfileInteractor interactor = new ApartmentProfileInteractorImpl(mMainThread, this, apartmentUserProfile);
        interactor.execute();

    }

    @Override
    public void uploadImage(ImageView imageView) {
        byte[] data = new MediaConverter().imageViewToByte(imageView);

        String mediaName = (String) imageView.getTag();

        MediaInteractor mediaInteractor = new UploadInteractorImpl(mMainThread, this, false, 1, mediaName, data);
        mediaInteractor.execute();
    }

    @Override
    public void uploadVideo(VideoView videoView) {

        byte[] data = new MediaConverter().videoViewToByte(videoView);

        String mediaName = (String) videoView.getTag();

        MediaInteractor mediaInteractor = new UploadInteractorImpl(mMainThread, this,false, 1, mediaName, data);
        mediaInteractor.execute();
    }

    @Override
    public void onError(String error) {

        mView.showError(error);
    }

    @Override
    public void onUploadSuccess() {
        //TODO: notify view that media was uploaded
    }
}
