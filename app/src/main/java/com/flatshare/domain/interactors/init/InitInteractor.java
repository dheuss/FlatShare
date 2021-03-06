package com.flatshare.domain.interactors.init;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface InitInteractor extends Interactor {

    interface Callback {
        void onTenantFound(PrimaryUserProfile primaryUserProfile, TenantProfile tenantProfile);

        void onRoommateFound(PrimaryUserProfile primaryUserProfile, RoommateProfile roommateProfile);

        void onApartmentFound(PrimaryUserProfile primaryUserProfile, RoommateProfile roommateProfile, ApartmentProfile apartmentProfile);

        void onReceivedFailure(String error);
    }

}
