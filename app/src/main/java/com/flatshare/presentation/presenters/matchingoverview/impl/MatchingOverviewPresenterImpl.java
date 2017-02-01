package com.flatshare.presentation.presenters.matchingoverview.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.matching.PotentialMatchingInteractor;
import com.flatshare.domain.interactors.matching.impl.PotentialMatchingInteractorImpl;
import com.flatshare.domain.interactors.matchingoverview.MatchesInteractor;
import com.flatshare.domain.interactors.matchingoverview.MatchingOverviewInteractor;
import com.flatshare.domain.interactors.matchingoverview.impl.MatchesInteractorImpl;
import com.flatshare.domain.interactors.matchingoverview.impl.MatchingOverviewInteractorImpl;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.media.impl.ApartmentDownloadInteractorImpl;
import com.flatshare.domain.interactors.media.impl.TenantDownloadInteractorImpl;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;

import java.util.List;


public class MatchingOverviewPresenterImpl extends AbstractPresenter implements MatchingOverviewPresenter,
        MatchingOverviewInteractor.Callback, MatchesInteractor.Callback {

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
    public void getMatches() {
        mView.showProgress();
        TenantProfile tenantProfile = userState.getTenantProfile();
        ApartmentProfile apartmentProfile = userState.getApartmentProfile();
        MatchesInteractor matchesInteractor = new MatchesInteractorImpl(mMainThread, this, tenantProfile, apartmentProfile);
        matchesInteractor.execute();
    }

    @Override
    public void userDeleteApartment(String tenantId, String apartmentId) {
        MatchingOverviewInteractor matchingOverviewInteractor = new MatchingOverviewInteractorImpl(mMainThread, this, tenantId, apartmentId);
        matchingOverviewInteractor.execute();
    }


    @Override
    public void onApartmentMatchesFound(List<Pair<ApartmentProfile, Bitmap>> apMatches) {
        mView.hideProgress();
        Log.d(TAG, "onApartmentMatchesFound: show apMatches List: " + apMatches);
        mView.showApartments(apMatches);
    }

    @Override
    public void onTenantMatchesFound(List<Pair<TenantProfile, Bitmap>> tenMatches) {
        mView.hideProgress();
        Log.d(TAG, "onTenantMatchesFound: show tenMatches List: " + tenMatches);
        mView.showTenants(tenMatches);
    }

    @Override
    public void onFailure(String error) {
        mView.hideProgress();
        Log.d("Matches Error", error);
    }
}
