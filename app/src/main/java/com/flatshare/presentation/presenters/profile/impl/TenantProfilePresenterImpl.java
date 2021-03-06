package com.flatshare.presentation.presenters.profile.impl;

import android.widget.VideoView;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.datatypes.enums.MediaType;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.profile.SecondaryProfileInteractor;
import com.flatshare.domain.interactors.profile.impl.TenantProfileInteractorImpl;
import com.flatshare.domain.interactors.media.impl.UploadInteractorImpl;
import com.flatshare.presentation.presenters.profile.TenantProfilePresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Arber on 11/12/2016.
 */

public class TenantProfilePresenterImpl extends AbstractPresenter implements TenantProfilePresenter,
        SecondaryProfileInteractor.Callback, MediaInteractor.UploadCallback {


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
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void onProfileCreated(UserProfile profile) {
        TenantProfile tenantProfile = (TenantProfile) profile;
        tenantProfile.setDone(true);
        userState.setTenantProfile(tenantProfile);
        mView.hideProgress();
        mView.changeToTenantSettings();
    }

    @Override
    public void sendProfile(TenantProfile tenantProfile) {
        mView.showProgress();
        tenantProfile.setTenantId(userState.getTenantId());
        SecondaryProfileInteractor interactor = new TenantProfileInteractorImpl(mMainThread, this, tenantProfile);
        interactor.execute();
    }

    @Override
    public void uploadImage(byte[] data) {
//        mView.showProgress();
        MediaInteractor mediaInteractor = new UploadInteractorImpl(mMainThread, this, true, MediaType.IMAGE, data, userState.getTenantId());
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
