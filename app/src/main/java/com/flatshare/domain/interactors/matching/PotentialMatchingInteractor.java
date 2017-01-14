package com.flatshare.domain.interactors.matching;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.base.Interactor;

import java.util.List;

/**
 * Created by Arber on 10/12/2016.
 */

public interface PotentialMatchingInteractor extends Interactor{

    interface Callback {
        void onNoMatchFound();
        void notifyError(String errorMessage);

        void onTenantsFound(List<TenantProfile> tenants);

        void onApartmentsFound(List<ApartmentProfile> apartments);
    }
}
