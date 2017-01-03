package com.flatshare.domain.predicates;


import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;

/**
 * Created by Arber on 13/12/2016.
 */
class MatchFinder {

    public boolean apMatchesTenantFilter(ApartmentUserProfile a, TenantFilterSettings tf) {
        return (a.getArea() >= tf.getAreaFrom() && a.getArea() <= tf.getAreaTo())
                && (a.getPrice() >= tf.getPriceFrom() && a.getPrice() <= tf.getPriceTo());
//                && ((tf.getPetsAllowed() == 2) || (!a.hasPets() && tf.getPetsAllowed() == 0) || (a.hasPets() && tf.getPetsAllowed() == 1))
//                && ((tf.wantsInternet() == 2) || (!a.hasInternet() && tf.wantsInternet() == 0) || (a.hasInternet() && tf.wantsInternet() == 1))
//                && ((tf.wantsWashingMachine() == 2) || (!a.hasWashingMachine() && tf.wantsPurposeApartment() == 0) || (a.hasWashingMachine() && tf.wantsPurposeApartment() == 1))
//                && ((tf.wantsSmokerApartment() == 2) || (!a.isSmokerApartment() && tf.wantsSmokerApartment() == 0) || (a.isSmokerApartment() && tf.wantsSmokerApartment() == 1))
//                && ((tf.wantsPurposeApartment() == 2) || (!a.isPurposeApartment() && tf.wantsPurposeApartment() == 0) || (a.isPurposeApartment() && tf.wantsPurposeApartment() == 1));
    }

    public boolean tenantMatchesApFilter(TenantUserProfile t, ApartmentFilterSettings af) {
        return (t.getAge() >= af.getAgeFrom() && t.getAge() <= af.getAgeTo())
                && ((af.getGender() == 2) || (t.getGender() == af.getGender()))
                && ((af.getSmoker() == 2) || (!t.isSmoker() && af.getSmoker() == 0) || (t.isSmoker() && af.getSmoker() == 1))
                && ((af.getPetsAllowed() == 2) || (!t.hasPets() && af.getPetsAllowed() == 0) || (t.hasPets() && af.getPetsAllowed() == 1));
    }
}
