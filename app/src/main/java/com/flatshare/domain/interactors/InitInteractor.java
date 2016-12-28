package com.flatshare.domain.interactors;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface InitInteractor extends Interactor {

    interface Callback {
        void onReceivedSuccess(PrimaryUserProfile primaryUserProfile, TenantUserProfile tenantUserProfile, ApartmentUserProfile apartmentUserProfile);
        void onReceivedFailure(String error);
    }

}
