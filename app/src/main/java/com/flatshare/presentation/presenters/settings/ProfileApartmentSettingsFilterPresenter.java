package com.flatshare.presentation.presenters.settings;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 15.01.2017.
 */

public interface ProfileApartmentSettingsFilterPresenter extends BasePresenter {

    void changeApartmentFilterSettings(ApartmentFilterSettings apartmentFilterSettings);

    interface View extends BaseView {
        void changeToProfileApartmentSettingsActivity();
    }
}
