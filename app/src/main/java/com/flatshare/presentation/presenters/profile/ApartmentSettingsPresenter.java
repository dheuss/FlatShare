package com.flatshare.presentation.presenters.profile;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 08/12/2016.
 */

public interface ApartmentSettingsPresenter extends BasePresenter {

    void sendFilterSettings(ApartmentFilterSettings apartmentFilterSettings);

    interface View extends BaseView {
        void changeToMatchingActivity();
    }
}
