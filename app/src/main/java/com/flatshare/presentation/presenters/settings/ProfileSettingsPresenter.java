package com.flatshare.presentation.presenters.settings;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 28.12.2016.
 */

public interface ProfileSettingsPresenter extends BasePresenter{
    interface View extends BaseView {
        void changeToTenantSettings();
        void uploadSucces();
    }

}