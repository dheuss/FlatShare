package com.flatshare.domain.datatypes.predicates;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 13/12/2016.
 */
public class TenantMatchFinder extends MatchFinder {

    private TenantUserProfile t;
    private List<ApartmentUserProfile> matchedApartments;

    public TenantMatchFinder(TenantUserProfile tenantUserProfile, List<ApartmentUserProfile> apartmentUserProfileList) {

        t = tenantUserProfile;
        matchedApartments = new ArrayList<>();

        for (ApartmentUserProfile a : apartmentUserProfileList) {
            if (predicatesApply(a)) {
                matchedApartments.add(a);
            }
        }

    }

    public boolean predicatesApply(ApartmentUserProfile a) {

        TenantFilterSettings tf = t.getTenantFilterSettings();
        ApartmentFilterSettings af = a.getApartmentFilterSettings();


        return apMatchesTenantFilter(a, tf) && tenantMatchesApFilter(t, af);
    }


    public List<ApartmentUserProfile> getMatches() {
        return matchedApartments;
    }
}
