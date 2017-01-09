package com.flatshare.domain.interactors;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface InitInteractor extends Interactor {

    interface Callback {
        void onReceivedSuccess(PrimaryUserProfile primaryUserProfile, TenantProfile tenantProfile, ApartmentProfile apartmentProfile);
        void onReceivedFailure(String error);
    }

}
