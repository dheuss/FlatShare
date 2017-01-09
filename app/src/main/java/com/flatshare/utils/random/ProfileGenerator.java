package com.flatshare.utils.random;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 12/12/2016.
 */

public class ProfileGenerator {

    public static List<TenantProfile> generateTenantProfiles(int size) {
        List<TenantProfile> tenantProfiles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TenantProfile tenantProfile = new TenantProfile();
            tenantProfile.setAge((int) (20 + Math.random() * 10));
            tenantProfile.setFirstName("Tester" + i);
            tenantProfile.setDurationOfStay(i % 4);
            tenantProfile.setGender((int) (Math.random() * 2) == 0 ? 0 : 1);

            tenantProfile.setPets((int) (Math.random() * 2) == 1);
            tenantProfile.setSmoker(i % 4 == 0);

            TenantFilterSettings tenantFilterSettings = new TenantFilterSettings();


            tenantFilterSettings.setAreaFrom((int) (10 + Math.random() * 10));
            tenantFilterSettings.setAreaTo((int) (30 + Math.random() * 10));

            tenantFilterSettings.setPriceFrom((int) (300 + Math.random() * 30));
            tenantFilterSettings.setPriceTo((int) (400 + Math.random() * 30));

            tenantFilterSettings.setInternet((int) (Math.random() * 3));
            tenantFilterSettings.setPetsAllowed((int) (Math.random() * 3));
            tenantFilterSettings.setPurposeApartment((int) (Math.random() * 3));
            tenantFilterSettings.setSmokerApartment((int) (Math.random() * 3));

            tenantProfile.setTenantFilterSettings(tenantFilterSettings);

            tenantProfiles.add(tenantProfile);
        }

        return tenantProfiles;
    }



    public static List<ApartmentProfile> generateApartmentProfiles(int size) {
        List<ApartmentProfile> apartmentProfileList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ApartmentProfile apartmentProfile = new ApartmentProfile();

            apartmentProfile.setArea((int) (30 + Math.random() * 20));
            apartmentProfile.setPrice((int) (300 + Math.random() * 50));

            apartmentProfile.setInternet((int) (Math.random() * 2) == 1);
            apartmentProfile.setPurposeApartment((int) (Math.random() * 2) == 1);
            apartmentProfile.setSmokerApartment((int) (Math.random() * 2) == 1);
            apartmentProfile.setWashingMachine((int) (Math.random() * 2) == 1);


            apartmentProfile.setPets((int) (Math.random() * 2) == 1);



            ApartmentFilterSettings apartmentFilterSettings = new ApartmentFilterSettings();

            apartmentFilterSettings.setAgeFrom((int) (18 + (Math.random() * 5)));
            apartmentFilterSettings.setAgeTo((int) (25 + (Math.random() * 5)));

            apartmentFilterSettings.setPetsAllowed(2);
            apartmentFilterSettings.setGender(2);
            apartmentFilterSettings.setSmoker(2);

            apartmentProfile.setApartmentFilterSettings(apartmentFilterSettings);
            apartmentProfileList.add(apartmentProfile);

        }

        // TEST
//        ApartmentProfile a1 = new ApartmentProfile();
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
//        apartmentProfileList.add(a1);

        return apartmentProfileList;
    }

}