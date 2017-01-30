package com.flatshare.presentation.presenters.matching;


import android.graphics.Bitmap;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.util.List;
/**
 * Created by Arber on 06/12/2016.
 */
public interface PotentialMatchingPresenter extends BasePresenter {

    void getPotentialMatches();

    void tenantSwipedApartment(String apartmentProfileId, boolean accepted);

    void roommateSwipedTenant(String tenantProfileId, boolean accepted);

    void getProfilePictures(List<TenantProfile> tenantProfiles, List<ApartmentProfile> apartmentProfiles);


    interface View extends BaseView {
        void showTenants(List<Pair<TenantProfile, Bitmap>> tenants);
        void showApartments(List<Pair<ApartmentProfile, Bitmap>> apartments);
    }
}
