package com.flatshare.presentation.presenters;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 29.12.2016.
 */

public interface SettingsPresenter extends BasePresenter{

    interface View extends BaseView {
        void changeToTenantSettings();
        void uploadSucces();
    }
}
