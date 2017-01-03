package com.flatshare.utils.random;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 12/12/2016.
 */

public class ProfileGenerator {

    public static List<TenantUserProfile> generateTenantProfiles(int size) {
        List<TenantUserProfile> tenantUserProfiles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TenantUserProfile tenantUserProfile = new TenantUserProfile();
            tenantUserProfile.setAge((int) (20 + Math.random() * 10));
            tenantUserProfile.setFirstName("Tester" + i);
            tenantUserProfile.setDurationOfStay(i % 4);
            tenantUserProfile.setGender((int) (Math.random() * 2) == 0 ? 0 : 1);

            tenantUserProfile.setPets((int) (Math.random() * 2) == 1);
            tenantUserProfile.setSmoker(i % 4 == 0);

            TenantFilterSettings tenantFilterSettings = new TenantFilterSettings();


            tenantFilterSettings.setAreaFrom((int) (10 + Math.random() * 10));
            tenantFilterSettings.setAreaTo((int) (30 + Math.random() * 10));

            tenantFilterSettings.setPriceFrom((int) (300 + Math.random() * 30));
            tenantFilterSettings.setPriceTo((int) (400 + Math.random() * 30));

            tenantFilterSettings.setInternet((int) (Math.random() * 3));
            tenantFilterSettings.setPetsAllowed((int) (Math.random() * 3));
            tenantFilterSettings.setPurposeApartment((int) (Math.random() * 3));
            tenantFilterSettings.setSmokerApartment((int) (Math.random() * 3));

            tenantUserProfile.setTenantFilterSettings(tenantFilterSettings);

            tenantUserProfiles.add(tenantUserProfile);
        }

        return tenantUserProfiles;
    }



    public static List<ApartmentUserProfile> generateApartmentProfiles(int size) {
        List<ApartmentUserProfile> apartmentUserProfileList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ApartmentUserProfile apartmentUserProfile = new ApartmentUserProfile();

            apartmentUserProfile.setArea((int) (30 + Math.random() * 20));
            apartmentUserProfile.setPrice((int) (300 + Math.random() * 50));

            apartmentUserProfile.setInternet((int) (Math.random() * 2) == 1);
            apartmentUserProfile.setPurposeApartment((int) (Math.random() * 2) == 1);
            apartmentUserProfile.setSmokerApartment((int) (Math.random() * 2) == 1);
            apartmentUserProfile.setWashingMachine((int) (Math.random() * 2) == 1);


            apartmentUserProfile.setPets((int) (Math.random() * 2) == 1);



            ApartmentFilterSettings apartmentFilterSettings = new ApartmentFilterSettings();

            apartmentFilterSettings.setAgeFrom((int) (18 + (Math.random() * 5)));
            apartmentFilterSettings.setAgeTo((int) (25 + (Math.random() * 5)));

            apartmentFilterSettings.setPetsAllowed(2);
            apartmentFilterSettings.setGender(2);
            apartmentFilterSettings.setSmoker(2);

            apartmentUserProfile.setApartmentFilterSettings(apartmentFilterSettings);
            apartmentUserProfileList.add(apartmentUserProfile);

        }

        // TEST
//        ApartmentUserProfile a1 = new ApartmentUserProfile();
//        a1.setArea(30);
//        a1.setPets(false);
//        a1.setPrice(400);
//        a1.setInternet(true);
//        a1.setPurposeApartment(false);
//        a1.setSmokerApartment(false);
//        a1.setWashingMachine(true);
//
//        ApartmentFilterSettings a1Set = new ApartmentFilterSettings();
//        a1Set.setGender(0);
//        a1Set.setAgeFrom(20);
//        a1Set.setAgeTo(25);
//        a1Set.setSmoker(2);
//        a1Set.setPetsAllowed(2);
//
//        a1.setApartmentFilterSettings(a1Set);
//
//        apartmentUserProfileList.add(a1);

        return apartmentUserProfileList;
    }

}