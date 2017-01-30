package com.flatshare.presentation.presenters.settings.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.auth.ChangeMailInteractor;
import com.flatshare.domain.interactors.auth.ChangePasswordInteractor;
import com.flatshare.domain.interactors.auth.DeleteAccountInteractor;
import com.flatshare.domain.interactors.auth.LogoutInteractor;
import com.flatshare.domain.interactors.auth.ResetPasswordInteractor;
import com.flatshare.domain.interactors.auth.impl.ChangeMailInteractorImpl;
import com.flatshare.domain.interactors.auth.impl.ChangePasswordInteractorImpl;
import com.flatshare.domain.interactors.auth.impl.DeleteAccountInteractorImpl;
import com.flatshare.domain.interactors.auth.impl.LogoutInteractorImpl;
import com.flatshare.domain.interactors.auth.impl.ResetPasswordInteractorImpl;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.media.impl.DownloadApartmentImageInteractorImpl;
import com.flatshare.domain.interactors.media.impl.DownloadTenantImageInteractorImpl;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.settings.SettingsPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by david on 29.12.2016.
 */

public class SettingsPresenterImpl extends AbstractPresenter implements SettingsPresenter,
        LogoutInteractor.Callback,
        ChangeMailInteractor.Callback,
        DeleteAccountInteractor.Callback,
        ChangePasswordInteractor.Callback,
        ResetPasswordInteractor.Callback,
        MediaInteractor.DownloadCallback{

    private static final String TAG = "SettingsPresenterImpl";
    private SettingsPresenter.View mView;

    public SettingsPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        mView = view;
    }

    @Override
    public void resume() {
        Log.d(TAG, "RESUMING!!!");
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
    public void logOut() {
        LogoutInteractor logoutInteractor = new LogoutInteractorImpl(mMainThread, this);
        logoutInteractor.execute();
    }

    @Override
    public void changeMailAddress(String newMailAddress) {
        ChangeMailInteractor changeMailInteractor = new ChangeMailInteractorImpl(mMainThread, this, newMailAddress);
        changeMailInteractor.execute();
    }

    @Override
    public void deleteAccount(){
        DeleteAccountInteractor deleteAccountInteractor = new DeleteAccountInteractorImpl(mMainThread, this);
        deleteAccountInteractor.execute();
    }

    @Override
    public void changePassword(String newPassword){
        ChangePasswordInteractor changePasswordInteractor = new ChangePasswordInteractorImpl(mMainThread, this, newPassword);
        changePasswordInteractor.execute();
    }

    @Override
    public void resetPasswordMail(String email){
        ResetPasswordInteractor resetPasswordInteractor = new ResetPasswordInteractorImpl(mMainThread, this, email);
        resetPasswordInteractor.execute();
    }

    @Override
    public void getProfilePicture() {
        MediaInteractor downloadInteractor;
        if (userState.getTenantProfile() == null) {
            downloadInteractor = new DownloadApartmentImageInteractorImpl(mMainThread, this, userState.getApartmentProfile());
        } else {
            downloadInteractor = new DownloadTenantImageInteractorImpl(mMainThread, this, userState.getTenantProfile());
        }
        downloadInteractor.execute();
    }

    @Override
    public void onLogoutSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onLogoutFail(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }

    @Override
    public void onChangeMailSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onChangeMailFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }


    @Override
    public void onDeleteAccountSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onDeleteAccountFailure(String errorMessage){
        mView.hideProgress();
        onError(errorMessage);
    }

    @Override
    public void onChangePasswordSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onChangePasswordFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }

    @Override
    public void onResetPasswordSuccess() {
        userState.setLoggedIn(false);
        mView.hideProgress();
        mView.changeToLoginActivity();
    }

    @Override
    public void onResetPasswordFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }

    @Override
    public void onTenantDownloadSuccess(List<Pair<TenantProfile, Bitmap>> tenantsImageList) {

    }

    @Override
    public void onApartmentDownloadSuccess(List<Pair<ApartmentProfile, Bitmap>> apartmentImageList) {

    }

    @Override
    public void onDownloadTenantImageSuccess(Bitmap tenantImage) {
        mView.hideProgress();
        mView.showApartmentImage(tenantImage);
    }

    @Override
    public void onDownloadApartmentImageSucess(Bitmap apartmentImage) {
        mView.hideProgress();
        mView.showApartmentImage(apartmentImage);
    }

    @Override
    public void onDownloadError(String error) {

    }
}
