package com.flatshare.presentation.presenters.settings;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 29.12.2016.
 */

public interface SettingsPresenter extends BasePresenter{

    void logOut();
    void changeMailAddress(String newMailAddress);
    void deleteAccount();

    interface View extends BaseView {
        void changeToLoginActivity();
    }
}
