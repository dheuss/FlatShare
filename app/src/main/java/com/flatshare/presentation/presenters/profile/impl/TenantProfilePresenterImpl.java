package com.flatshare.presentation.presenters.profile.impl;

import android.net.Uri;
import android.widget.VideoView;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.enums.MediaType;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.profile.ProfileInteractor;
import com.flatshare.domain.interactors.profile.impl.TenantProfileInteractorImpl;
import com.flatshare.domain.interactors.media.impl.UploadInteractorImpl;
import com.flatshare.presentation.presenters.profile.TenantProfilePresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 11/12/2016.
 */

public class TenantProfilePresenterImpl extends AbstractPresenter implements TenantProfilePresenter,
        ProfileInteractor.Callback, MediaInteractor.UploadCallback {


    private static final String TAG = "TenantProfilePresenter";
    private TenantProfilePresenter.View mView;

    public TenantProfilePresenterImpl(MainThread mainThread,
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
        mView.changeToTenantSettings();
    }

    @Override
    public void onSentFailure(String error) {
        userState.setTenantProfile(null);
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void sendProfile(TenantProfile tenantProfile) {

        mView.showProgress();
        userState.setTenantProfile(tenantProfile);
        ProfileInteractor interactor = new TenantProfileInteractorImpl(mMainThread, this, tenantProfile);
        interactor.execute();

    }

    @Override
    public void uploadImage(Uri uri) {

//        mView.showProgress();

        MediaInteractor mediaInteractor = new UploadInteractorImpl(mMainThread, this, true, MediaType.IMAGE, uri, userState.getTenantId());
        mediaInteractor.execute();
    }

    @Override
    public void uploadVideo(VideoView videoView) {

    }

    @Override
    public void onError(String error) {
        mView.showError(error);
    }

    @Override
    public void onUploadSuccess() {

        mView.uploadSuccess();
    }
}
