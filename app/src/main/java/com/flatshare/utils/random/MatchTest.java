package com.flatshare.utils.random;

import android.util.Log;

import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.predicates.TenantMatchFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 12/12/2016.
 */

public class MatchTest {

    TenantUserProfile tenantUserProfile;
    TenantFilterSettings tenantFilterSettings;

    private String TAG = "MatchTest";

//    ApartmentUserProfile apartmentUserProfile;
//    ApartmentFilterSettings apartmentFilterSettings;

    //    List<TenantUserProfile> tenantUserProfileList;
//    List<TenantFilterSettings> tenantFilterSettingsList;
    List<ApartmentUserProfile> apartmentUserProfileList;
    List<ApartmentFilterSettings> apartmentFilterSettingsList;

    public MatchTest() {
        this.tenantUserProfile = getTenantUserProfile();
        this.tenantFilterSettings = getTenantFilterSettings();
        this.apartmentUserProfileList = new ArrayList<>(ProfileGenerator.generateApartmentProfiles(100));


        init();
    }

    private void init() {
        Log.d(TAG, "Showing your tenant settings");
        Log.d(TAG, show(tenantUserProfile.getTenantFilterSettings()));

        Log.d(TAG, "Showing matches");

        long time1 = System.currentTimeMillis();

        Log.d(TAG, show(potentialApartmentMatches()));

        long time2 = System.currentTimeMillis();

        Log.d(TAG, "POJO: TIME IT TOOK FOR: " + apartmentUserProfileList.size() + " WAS: " + (time2 - time1) + " milliseconds found: " + potentialApartmentMatches().size());

    }

    public List<ApartmentUserProfile> potentialApartmentMatches() {

        TenantMatchFinder matchFinder = new TenantMatchFinder(this.tenantUserProfile, this.apartmentUserProfileList);

        return matchFinder.getMatches();
    }


    private String show(List<ApartmentUserProfile> apartmentUserProfilesList) {

        String result = "";
        for (int i = 0; i < apartmentUserProfilesList.size(); i++) {
            result += show(i, apartmentUserProfilesList.get(i));
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

    private String show(int i, ApartmentUserProfile apartmentUserProfile) {
        String result = i + " \n";

        result += "price: " + apartmentUserProfile.getPrice() + "\n";
        result += "area: " + apartmentUserProfile.getArea() + "\n";
        result += "pets? " + apartmentUserProfile.hasPets() + "\n";
        result += "internet? " + apartmentUserProfile.hasInternet() + "\n";
        result += "zweck-WG? " + apartmentUserProfile.isPurposeApartment() + "\n";
        result += "washingM? " + apartmentUserProfile.hasWashingMachine() + "\n";

        result += "\n\n\n";

        return result;
    }


    public TenantUserProfile getTenantUserProfile() {

        TenantUserProfile tenantUserProfile = new TenantUserProfile();

        tenantUserProfile.setSmoker(true);
        tenantUserProfile.setDurationOfStay(2);
        tenantUserProfile.setGender(0);
        tenantUserProfile.setAge(22);
        tenantUserProfile.setSmoker(false);


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


        tenantUserProfile.setTenantFilterSettings(tenantFilterSettings);

        return tenantUserProfile;
    }

    public TenantFilterSettings getTenantFilterSettings() {



        return tenantFilterSettings;
    }


}
