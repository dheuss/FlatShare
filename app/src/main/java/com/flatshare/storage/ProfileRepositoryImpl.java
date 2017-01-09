package com.flatshare.storage;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.repository.ProfileRepository;
import com.flatshare.network.DatabaseManager;
import com.flatshare.network.impl.DatabaseManagerImpl;

/**
 * Created by Arber on 10/12/2016.
 */

public class ProfileRepositoryImpl implements ProfileRepository {

    private DatabaseManager databaseManager;

    private String userId;
    private String userPath;

    private String tenantProfilesPath;
    private String apartmentProfilesPath;


    public ProfileRepositoryImpl() {
        databaseManager = new DatabaseManagerImpl();


        userId = databaseManager.getUserId();
        userPath = "/user_profiles";

        tenantProfilesPath = "/tenant_profiles";
        apartmentProfilesPath = "/apartment_profiles";
    }


    @Override
    public boolean createPrimaryProfile(PrimaryUserProfile primaryUserProfile) {

        return databaseManager.create(primaryUserProfile, userPath + "/" + userId) == null;
    }

    @Override
    public boolean createTenantProfile(TenantProfile tenantProfile) {
        String tenantId = databaseManager.push(tenantProfile, tenantProfilesPath);
        if (tenantId == null) {
            return false;
        }
        return databaseManager.create(tenantId, userPath + "/" + userId + "/" + "tenant_profile_id") == null;
    }

    @Override
    public boolean createApartmentProfile(ApartmentProfile apartmentProfile) {

        String apartmentId = databaseManager.push(apartmentProfile, apartmentProfilesPath);

        String city = apartmentProfile.getApartmentLocation().getCity();
        String district = apartmentProfile.getApartmentLocation().getDistrict();
        int zipCode = apartmentProfile.getApartmentLocation().getZipCode();

        String locationPath = "apartment_locations/" + city + "/" + district + "/" + zipCode;

        if (apartmentId == null) {
            return false;
        }

        //TODO: TEST!!!
        String locationId = databaseManager.push(apartmentId, locationPath);

        if (locationId == null) {
            return false;
        }
        return databaseManager.create(apartmentId, userPath + "/" + userId + "/" + "apartment_profile_id") == null;

    }

    @Override
    public PrimaryUserProfile getPrimaryProfile() {
//        return (PrimaryUserProfile) databaseManager.readItem(userPath + "/" + userId, PrimaryUserProfile.class);
        return null;
    }
}
