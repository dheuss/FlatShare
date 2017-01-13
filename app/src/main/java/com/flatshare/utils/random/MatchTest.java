package com.flatshare.utils.random;

import android.util.Log;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.predicates.TenantMatchFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 12/12/2016.
 */

public class MatchTest {

    TenantProfile tenantProfile;
    TenantFilterSettings tenantFilterSettings;

    private String TAG = "MatchTest";

//    ApartmentProfile apartmentUserProfile;
//    ApartmentFilterSettings apartmentFilterSettings;

    //    List<TenantProfile> tenantUserProfileList;
//    List<TenantFilterSettings> tenantFilterSettingsList;
    List<ApartmentProfile> apartmentProfileList;
    List<ApartmentFilterSettings> apartmentFilterSettingsList;

    public MatchTest() {
        this.tenantProfile = getTenantProfile();
        this.tenantFilterSettings = getTenantFilterSettings();
        this.apartmentProfileList = new ArrayList<>(ProfileGenerator.generateApartmentProfiles(100));


        init();
    }

    private void init() {
        Log.d(TAG, "Showing your tenant settings");
        Log.d(TAG, show(tenantProfile.getTenantFilterSettings()));

        Log.d(TAG, "Showing matches");

        long time1 = System.currentTimeMillis();

        Log.d(TAG, show(potentialApartmentMatches()));

        long time2 = System.currentTimeMillis();

        Log.d(TAG, "POJO: TIME IT TOOK FOR: " + apartmentProfileList.size() + " WAS: " + (time2 - time1) + " milliseconds found: " + potentialApartmentMatches().size());

    }

    public List<ApartmentProfile> potentialApartmentMatches() {

        TenantMatchFinder matchFinder = new TenantMatchFinder(this.tenantProfile, this.apartmentProfileList);

        return matchFinder.getMatches();
    }


    private String show(List<ApartmentProfile> apartmentProfilesList) {

        String result = "";
        for (int i = 0; i < apartmentProfilesList.size(); i++) {
            result += show(i, apartmentProfilesList.get(i));
        }

        return result;
    }

    private String show(TenantFilterSettings tenantFilterSettings) {

        String result = "";

        result += "priceFrom: " + tenantFilterSettings.getPriceFrom() + "\n";
        result += "priceTo: " + tenantFilterSettings.getPriceTo() + "\n";

        result += "areaFrom: " + tenantFilterSettings.getAreaFrom() + "\n";
        result += "areaTo: " + tenantFilterSettings.getAreaTo() + "\n";

        result += "petsAllowed? " + tenantFilterSettings.getPetsAllowed() + "\n";
        result += "internet? " + tenantFilterSettings.wantsInternet() + "\n";
        result += "smokerApartment? " + tenantFilterSettings.wantsSmokerApartment() + "\n";
        result += "zweck-WG? " + tenantFilterSettings.wantsPurposeApartment() + "\n";
        result += "washingM? " + tenantFilterSettings.wantsWashingMachine() + "\n";

        result += "\n\n\n";

        return result;

    }

    private String show(int i, ApartmentProfile apartmentProfile) {
        String result = i + " \n";

        result += "price: " + apartmentProfile.getPrice() + "\n";
        result += "area: " + apartmentProfile.getArea() + "\n";
        result += "pets? " + apartmentProfile.hasPets() + "\n";
        result += "internet? " + apartmentProfile.hasInternet() + "\n";
        result += "zweck-WG? " + apartmentProfile.isPurposeApartment() + "\n";
        result += "washingM? " + apartmentProfile.hasWashingMachine() + "\n";
        result += "roomSize: " + apartmentProfile.getRoomSize() + "\n";
        result += "apartmentSize: " + apartmentProfile.getApartmentSize() + "\n";

        result += "\n\n\n";

        return result;
    }


    public TenantProfile getTenantProfile() {

        TenantProfile tenantProfile = new TenantProfile();

        tenantProfile.setSmoker(true);
        tenantProfile.setDurationOfStay(2);
        tenantProfile.setGender(0);
        tenantProfile.setAge(22);
        tenantProfile.setSmoker(false);


        TenantFilterSettings tenantFilterSettings = new TenantFilterSettings();

        tenantFilterSettings.setWashingMachine(2);
        tenantFilterSettings.setPurposeApartment(2);
        tenantFilterSettings.setPetsAllowed(2);
        tenantFilterSettings.setInternet(2);
        tenantFilterSettings.setSmokerApartment(2);

        tenantFilterSettings.setAreaFrom(20);
        tenantFilterSettings.setAreaTo(80);

        tenantFilterSettings.setPriceFrom(300);
        tenantFilterSettings.setPriceTo(500);


        tenantProfile.setTenantFilterSettings(tenantFilterSettings);

        return tenantProfile;
    }

    public TenantFilterSettings getTenantFilterSettings() {



        return tenantFilterSettings;
    }


}
