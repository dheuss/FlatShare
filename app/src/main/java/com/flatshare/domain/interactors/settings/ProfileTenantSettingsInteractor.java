package com.flatshare.domain.interactors.settings;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by david on 11.01.2017.
 */

public interface ProfileTenantSettingsInteractor extends Interactor {
    interface Callback {
        void onSentSuccess();//int classificationId
        void onSentFailure(String error);
    }
}
