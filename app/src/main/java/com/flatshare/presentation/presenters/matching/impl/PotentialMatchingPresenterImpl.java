package com.flatshare.presentation.presenters.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.matching.PMatchesListenerInteractor;
import com.flatshare.domain.interactors.matching.PotentialMatchingInteractor;
import com.flatshare.domain.interactors.matching.SwipeInteractor;
import com.flatshare.domain.interactors.matching.impl.ApartmentSwipeInteractorImpl;
import com.flatshare.domain.interactors.matching.impl.PMatchesListenerInteractorImpl;
import com.flatshare.domain.interactors.matching.impl.PotentialMatchingInteractorImpl;
import com.flatshare.domain.interactors.matching.impl.TenantSwipeInteractorImpl;
import com.flatshare.presentation.presenters.matching.PotentialMatchingPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

import java.util.List;


/**
 * Created by Arber on 11/12/2016.
 */

public class PotentialMatchingPresenterImpl extends AbstractPresenter implements PotentialMatchingPresenter, PotentialMatchingInteractor.Callback, SwipeInteractor.Callback, PMatchesListenerInteractor.Callback {


    private static final String TAG = "PotentialMatchingPresenterImpl";

    private PotentialMatchingPresenter.View mView;

    public PotentialMatchingPresenterImpl(MainThread mainThread,
                                          View view) {
        super(mainThread);

        Log.d(TAG, "inside constructor");

        mView = view;
    }

    @Override
    public void resume() {

        Log.d(TAG, "inside resume()");

        //mView.showProgress();

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
        Log.d(TAG, "inside onError: " + message);
        mView.hideProgress();
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
    public void tenantSwipedApartment(String apartmentProfileId, boolean accepted) {
        String tenantProfileId = userState.getTenantId();
        SwipeInteractor swipeInteractor = new TenantSwipeInteractorImpl(mMainThread, this, tenantProfileId, apartmentProfileId, accepted);
        swipeInteractor.execute();
    }

    @Override
    public void roommateSwipedTenant(String tenantProfileId, boolean accepted) {
        RoommateProfile roommateProfile = userState.getRoommateProfile();
        int totalNrRoommates = userState.getApartmentProfile().getRoommateIds().size();
        SwipeInteractor swipeInteractor = new ApartmentSwipeInteractorImpl(mMainThread, this, tenantProfileId, roommateProfile, totalNrRoommates, accepted);
        swipeInteractor.execute();
    }

    @Override
    public void setPotentialMatchesListener() {
        PMatchesListenerInteractor pMatchesListenerInteractor = new PMatchesListenerInteractorImpl(mMainThread, this);
        pMatchesListenerInteractor.execute();
    }

    @Override
    public void onNoMatchFound() {
        onError("No Matches were found!");
    }

    @Override
    public void notifyError(String errorMessage) {
        onError(errorMessage);
    }

    @Override
    public void onTenantsFound(List<TenantProfile> tenants) {
        mView.hideProgress();
        mView.showTenants(tenants);
    }

    @Override
    public void onApartmentsFound(List<ApartmentProfile> apartments) {
        mView.hideProgress();
        mView.showApartments(apartments);
    }

    @Override
    public void onFailure(String errorMessage) {
        onError("Error on matching Interactor: " + errorMessage);
    }

    @Override
    public void onListenerUpdated(boolean listenerAttached) {
        mView.updateListener(listenerAttached);
    }

    @Override
    public void onMatchCreated(String key) {
        Log.d(TAG, "onMatchCreated: " + key);
    }
}