package com.flatshare.presentation.presenters.matching;


import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.util.List;

public interface PotentialMatchingPresenter extends BasePresenter {

    void getPotentialMatches();

    void tenantSwipedApartment(String apartmentProfileId, boolean accepted);

    void roommateSwipedTenant(String tenantProfileId, boolean accepted);

    void setPotentialMatchesListener();

    interface View extends BaseView {
        void showTenants(List<TenantProfile> tenants);
        void showApartments(List<ApartmentProfile> apartments);

        void updateListener(boolean listenerAttached);
    }
}
