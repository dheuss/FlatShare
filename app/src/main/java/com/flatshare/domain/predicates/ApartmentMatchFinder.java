package com.flatshare.domain.predicates;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Arber on 13/12/2016.
 */
public class ApartmentMatchFinder extends MatchFinder {

    private ApartmentUserProfile a;
    private List<TenantUserProfile> matchedTenants;

    public ApartmentMatchFinder(ApartmentUserProfile apartmentUserProfile, List<TenantUserProfile> tenantUserProfileList) {

        a = apartmentUserProfile;
        matchedTenants = new ArrayList<>();

        for (TenantUserProfile t : tenantUserProfileList) {
            if (predicatesApply(t)) {
                matchedTenants.add(t);
            }
        }

    }

    private boolean predicatesApply(TenantUserProfile t) {
        ApartmentFilterSettings af = a.getApartmentFilterSettings();
        TenantFilterSettings tf = t.getTenantFilterSettings();


        return tenantMatchesApFilter(t, af) && apMatchesTenantFilter(a, tf);
    }


    public List<TenantUserProfile> getMatches() {
        return matchedTenants;
    }
}
