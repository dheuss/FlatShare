package com.flatshare.domain.interactors.profile;

import com.flatshare.domain.datatypes.db.filters.FilterSettings;
import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface FilterSettingsInteractor extends Interactor {

    interface Callback {
        void onSentSuccess(FilterSettings filterSettings);
        void onSentFailure(String error);
    }

}
