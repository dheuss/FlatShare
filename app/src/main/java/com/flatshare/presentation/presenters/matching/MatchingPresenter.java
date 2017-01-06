package com.flatshare.presentation.presenters.matching;


import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.util.List;

public interface MatchingPresenter extends BasePresenter {

    void getMatches();

    interface View extends BaseView {

        void showTenants(List<TenantUserProfile> tenants);

        void showApartments(List<ApartmentUserProfile> apartments);
    }
}
