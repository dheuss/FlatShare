package com.flatshare.domain.predicates;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 13/12/2016.
 */
public class TenantMatchFinder extends MatchFinder {

    private TenantProfile t;
    private List<ApartmentProfile> matchedApartments;

    public TenantMatchFinder(TenantProfile tenantProfile, List<ApartmentProfile> apartmentProfileList) {

        t = tenantProfile;
        matchedApartments = new ArrayList<>();

        for (ApartmentProfile a : apartmentProfileList) {
            if (predicatesApply(a)) {
                matchedApartments.add(a);
            }
        }

    }

    public boolean predicatesApply(ApartmentProfile a) {

        TenantFilterSettings tf = t.getTenantFilterSettings();
        ApartmentFilterSettings af = a.getApartmentFilterSettings();


        return apMatchesTenantFilter(a, tf) && tenantMatchesApFilter(t, af);
    }


    public List<ApartmentProfile> getMatches() {
        return matchedApartments;
    }
}
