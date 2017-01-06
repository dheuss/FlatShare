package com.flatshare.presentation.presenters.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.interactors.matching.MatchingInteractor;
import com.flatshare.domain.interactors.matching.impl.MatchingInteractorImpl;
import com.flatshare.presentation.presenters.matching.MatchingPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

import java.util.List;


/**
 * Created by Arber on 11/12/2016.
 */

public class MatchingPresenterImpl extends AbstractPresenter implements MatchingPresenter, MatchingInteractor.Callback  {


    private static final String TAG = "MatchingPresenterImpl";

    private MatchingPresenter.View mView;
//    private MessageRepository  mMessageRepository;

    public MatchingPresenterImpl(MainThread mainThread,
                                 View view) {
        super(mainThread);

        Log.d(TAG, "inside constructor");

        mView = view;
//        mMessageRepository = messageRepository;
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
    public void getMatches() {
        mView.showProgress();
        int classificationId = userState.getPrimaryUserProfile().getClassificationId();
        TenantUserProfile tenantUserProfile = userState.getTenantUserProfile();
        ApartmentUserProfile apartmentUserProfile = userState.getApartmentUserProfile();
        MatchingInteractor matchingInteractor = new MatchingInteractorImpl(mMainThread, this,classificationId, tenantUserProfile, apartmentUserProfile);
        matchingInteractor.execute();
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
    public void onTenantsFound(List<TenantUserProfile> tenants) {
        mView.hideProgress();
        mView.showTenants(tenants);
    }

    @Override
    public void onApartmentsFound(List<ApartmentUserProfile> apartments) {
        mView.hideProgress();
        mView.showApartments(apartments);
    }

}
