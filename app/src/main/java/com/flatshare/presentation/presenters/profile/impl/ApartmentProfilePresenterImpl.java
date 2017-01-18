package com.flatshare.presentation.presenters.profile.impl;

import android.net.Uri;
import android.widget.VideoView;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.datatypes.enums.MediaType;
import com.flatshare.domain.interactors.matching.NicknameRetrieverInteractor;
import com.flatshare.domain.interactors.matching.impl.NicknameRetrieverInteractorImpl;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.media.impl.UploadInteractorImpl;
import com.flatshare.domain.interactors.profile.SecondaryProfileInteractor;
import com.flatshare.domain.interactors.profile.impl.ApartmentProfileInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.profile.ApartmentProfilePresenter;

import java.util.Map;

/**
 * Created by Arber on 11/12/2016.
 */

public class ApartmentProfilePresenterImpl extends AbstractPresenter implements ApartmentProfilePresenter,
        SecondaryProfileInteractor.Callback, MediaInteractor.UploadCallback, NicknameRetrieverInteractor.Callback {


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
    public void onSentFailure(String error) {
        mView.hideProgress();
        onError(error);
    }

    @Override
    public void onProfileCreated(UserProfile profile) {
        ApartmentProfile apartmentProfile = (ApartmentProfile) profile;
        apartmentProfile.setDone(true);
        userState.setApartmentProfile(apartmentProfile);

        mView.hideProgress();
        mView.changeToApartmentSettings();
    }

    @Override
    public void sendProfile(ApartmentProfile apartmentProfile) {

        mView.showProgress();

        // set id
        String apartmentId = userState.getRoommateProfile().getApartmentId();
        apartmentProfile.setApartmentId(apartmentId);

        // ad owner
        String ownerId = userState.getRoommateId();
        apartmentProfile.setOwnerRoommateId(ownerId);
        apartmentProfile.getRoommateIds().add(ownerId);

        SecondaryProfileInteractor interactor = new ApartmentProfileInteractorImpl(mMainThread, this, apartmentProfile);
        interactor.execute();

    }

    @Override
    public void uploadImage(Uri uri) {
//        mView.showProgress();
        MediaInteractor mediaInteractor = new UploadInteractorImpl(mMainThread, this, false, MediaType.IMAGE, uri, userState.getApartmentId());
        mediaInteractor.execute();
    }

    @Override
    public void uploadVideo(VideoView videoView) {

    }

    @Override
    public void getUserEmails() {
        String roommateId = userState.getRoommateProfile().getId();
        NicknameRetrieverInteractor nicknameRetrieverInteractor = new NicknameRetrieverInteractorImpl(mMainThread, this, roommateId);
        nicknameRetrieverInteractor.execute();
    }

    @Override
    public void onError(String error) {

        mView.showError(error);
    }

    @Override
    public void onUploadSuccess() {
        mView.uploadSuccess();
    }

    @Override
    public void nicknamesRetrievedSuccess(Map<String, String> nicknameIdMap) {
        mView.updateAdapter(nicknameIdMap);
    }

    @Override
    public void nicknamesRetrievedFailure(String errorMessage) {
        mView.showError(errorMessage);
    }
}
