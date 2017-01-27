package com.flatshare.presentation.presenters.matchingoverview.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.matching.PotentialMatchingInteractor;
import com.flatshare.domain.interactors.matching.impl.PotentialMatchingInteractorImpl;
import com.flatshare.domain.interactors.matchingoverview.MatchingOverviewInteractor;
import com.flatshare.domain.interactors.matchingoverview.impl.MatchingOverviewInteractorImpl;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.media.impl.ApartmentDownloadInteractorImpl;
import com.flatshare.domain.interactors.media.impl.TenantDownloadInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;

import java.util.List;


public class MatchingOverviewPresenterImpl extends AbstractPresenter implements MatchingOverviewPresenter,
        MatchingOverviewInteractor.Callback, MediaInteractor.DownloadCallback, PotentialMatchingInteractor.Callback {

    private static final String TAG = "MatchingOverviewPresenterImpl";
    private MatchingOverviewPresenter.View mView;

    public MatchingOverviewPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        mView = view;
    }

    @Override
    public void onSentSuccess() {
        mView.successfulDeleted();
    }

    @Override
    public void onSentFailure(String error) {

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
    public void getPotentialMatches() {
        mView.showProgress();
        int classificationId = userState.getPrimaryUserProfile().getClassificationId();
        TenantProfile tenantProfile = userState.getTenantProfile();
        ApartmentProfile apartmentProfile = userState.getApartmentProfile();
        PotentialMatchingInteractor potentialMatchingInteractor = new PotentialMatchingInteractorImpl(mMainThread, this, classificationId, tenantProfile, apartmentProfile);
        potentialMatchingInteractor.execute();
    }

    @Override
    public void userDeleteApartment(String apartmentProfileId) {
        MatchingOverviewInteractor machingOverviewInteractor = new MatchingOverviewInteractorImpl(mMainThread, this, apartmentProfileId);
        machingOverviewInteractor.execute();
    }

    @Override
    public void getProfilePictures(List<TenantProfile> tenantProfiles, List<ApartmentProfile> apartmentProfiles) {
        MediaInteractor downloadInteractor;
        if (tenantProfiles == null) {
            downloadInteractor = new ApartmentDownloadInteractorImpl(mMainThread, this, apartmentProfiles);
        } else {
            downloadInteractor = new TenantDownloadInteractorImpl(mMainThread, this, tenantProfiles);
        }
        downloadInteractor.execute();
    }

    @Override
    public void onTenantDownloadSuccess(List<Pair<TenantProfile, Bitmap>> tenantsImageList) {
        mView.hideProgress();
        mView.showTenants(tenantsImageList);
    }

    @Override
    public void onApartmentDownloadSuccess(List<Pair<ApartmentProfile, Bitmap>> apartmentImageList) {
        mView.hideProgress();
        mView.showApartments(apartmentImageList);
    }

    @Override
    public void onDownloadError(String error) {
        onError("Error on matchingOverviewPresenter Download: " + error);
    }

    @Override
    public void onNoMatchFound() {
        Log.d("No Match Fount", "");
    }

    @Override
    public void notifyError(String errorMessage) {
        Log.e("Error MatchingOverview ", errorMessage);
    }

    @Override
    public void onTenantsFound(List<TenantProfile> tenants) {
        getProfilePictures(tenants, null);
    }

    @Override
    public void onApartmentsFound(List<ApartmentProfile> apartments) {
        getProfilePictures(null, apartments);
    }
}
