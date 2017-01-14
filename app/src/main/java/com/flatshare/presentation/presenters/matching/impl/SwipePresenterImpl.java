//package com.flatshare.presentation.presenters.matching.impl;
//
//import android.util.Log;
//
//import com.flatshare.domain.MainThread;
//import com.flatshare.domain.datatypes.db.profiles.UserProfile;
//import com.flatshare.domain.interactors.matching.SwipeInteractor;
//import com.flatshare.domain.interactors.matching.impl.TenantSwipeInteractorImpl;
//import com.flatshare.presentation.presenters.base.AbstractPresenter;
//import com.flatshare.presentation.presenters.matching.SwipePresenter;
//
///**
// * Created by Arber on 07/01/2017.
// */
//
//public class SwipePresenterImpl<T extends UserProfile> extends AbstractPresenter implements SwipePresenter<T>, SwipeInteractor.Callback {
//
//
//    private static final String TAG = "SwipePresenterImpl";
//
//    private SwipePresenter.View mView;
//
//    public SwipePresenterImpl(MainThread mainThread,
//                              View view) {
//        super(mainThread);
//        mView = view;
//    }
//
//    @Override
//    public void resume() {
//
//        Log.d(TAG, "inside resume()");
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void stop() {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public void onError(String message) {
//        Log.d(TAG, "inside onError: " + message);
//        mView.hideProgress();
//        mView.showError(message);
//    }
//
//    @Override
//    public void accept(T profile) { // or a list instead of only 1 profile ( maybe 10 at once)
//
//        String thisId = getThisId();
//
//
//        SwipeInteractor swipeInteractor = new TenantSwipeInteractorImpl(mMainThread, this, tenantProfileId, thisId, profile, true);
//        swipeInteractor.execute();
//    }
//
//    @Override
//    public void reject(T profile) {
//
//        String thisId = getThisId();
//
//        SwipeInteractor swipeInteractor = new TenantSwipeInteractorImpl(mMainThread, this, tenantProfileId, thisId, profile, false);
//        swipeInteractor.execute();
//    }
//
//    public String getThisId() {
//        String thisId;
//
//        if (userState.getPrimaryUserProfile().getClassificationId() == 0) {
//            thisId = userState.getTenantId();
//        } else {
//            thisId = userState.getApartmentId();
//        }
//
//        return thisId;
//    }
//}
