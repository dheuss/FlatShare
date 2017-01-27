package com.flatshare.presentation.presenters.matchingoverview;

import android.graphics.Bitmap;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by david on 15.01.2017.
 */

public interface MatchingOverviewPresenter extends BasePresenter {

    void getPotentialMatches();

    void userDeleteApartment(String apartmentProfileId);

    void setPotentialMatchesListener();

    void getProfilePictures(List<TenantProfile> tenantProfiles, List<ApartmentProfile> apartmentProfiles);

    interface View extends BaseView {

        void showTenants(List<Pair<TenantProfile, Bitmap>> tenants);

        void showApartments(List<Pair<ApartmentProfile, Bitmap>> apartments);

        void successfulDeleted();
    }
}
