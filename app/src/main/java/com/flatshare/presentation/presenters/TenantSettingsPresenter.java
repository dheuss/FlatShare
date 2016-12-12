package com.flatshare.presentation.presenters;

import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 08/12/2016.
 */

public interface TenantSettingsPresenter extends BasePresenter {

    void sendFilterSettings(TenantFilterSettings tenantFilterSettings);

    interface View extends BaseView {
        void changeToMatchingActivity();
    }

}
