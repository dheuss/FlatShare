package com.flatshare.presentation.presenters.settings;

import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 28.12.2016.
 */

public interface ProfileSettingsPresenter extends BasePresenter{
    void changeProfile(TenantProfile tenantProfile);
    interface View extends BaseView {
        void changeToTenantSettings();
        void uploadSucces();
    }

}
