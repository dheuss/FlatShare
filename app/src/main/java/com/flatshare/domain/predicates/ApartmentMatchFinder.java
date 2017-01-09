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
public class ApartmentMatchFinder extends MatchFinder {

    private ApartmentProfile a;
    private List<TenantProfile> matchedTenants;

    public ApartmentMatchFinder(ApartmentProfile apartmentProfile, List<TenantProfile> tenantProfileList) {

        a = apartmentProfile;
        matchedTenants = new ArrayList<>();

        for (TenantProfile t : tenantProfileList) {
            if (predicatesApply(t)) {
                matchedTenants.add(t);
            }
        }

    }

    private boolean predicatesApply(TenantProfile t) {
        ApartmentFilterSettings af = a.getApartmentFilterSettings();
        TenantFilterSettings tf = t.getTenantFilterSettings();


        return tenantMatchesApFilter(t, af) && apMatchesTenantFilter(a, tf);
    }


    public List<TenantProfile> getMatches() {
        return matchedTenants;
    }
}
