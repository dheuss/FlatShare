package com.flatshare.presentation.presenters.settings;

import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 15.01.2017.
 */

public interface ProfileTenantSettingsFilterPresenter extends BasePresenter {

    void changeFilterSettings(TenantFilterSettings tenantFilterSettings);

    interface View extends BaseView {
        void changeToProfileTenantSettingsActivity();
    }
}
