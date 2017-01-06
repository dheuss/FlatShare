package com.flatshare.domain.interactors.matching;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.interactors.base.Interactor;

import java.util.List;

/**
 * Created by Arber on 10/12/2016.
 */

public interface MatchingInteractor extends Interactor{

    interface Callback {
        void onNoMatchFound();
        void notifyError(String errorMessage);

        void onTenantsFound(List<TenantUserProfile> tenants);

        void onApartmentsFound(List<ApartmentUserProfile> apartments);
    }
}
