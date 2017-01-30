package com.flatshare.presentation.presenters.settings;

import android.graphics.Bitmap;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 29.12.2016.
 */

public interface SettingsPresenter extends BasePresenter{

    void logOut();
    void changeMailAddress(String newMailAddress);
    void deleteAccount();
    void changePassword(String newPassword);
    void resetPasswordMail(String email);

    void getProfilePicture();

    interface View extends BaseView {
        void changeToLoginActivity();
        void showApartmentImage(Bitmap apartmentImage);
        void showTenantImage(Bitmap tenantImage);
    }
}
