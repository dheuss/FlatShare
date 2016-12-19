package com.flatshare.storage;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
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
    public boolean createTenantProfile(TenantUserProfile tenantUserProfile) {
        String tenantId = databaseManager.push(tenantUserProfile, tenantProfilesPath);
        if (tenantId == null) {
            return false;
        }
        return databaseManager.create(tenantId, userPath + "/" + userId + "/" + "tenant_profile_id") == null;
    }

    @Override
    public boolean createApartmentProfile(ApartmentUserProfile apartmentUserProfile) {

        String apartmentId = databaseManager.push(apartmentUserProfile, apartmentProfilesPath);

        String city = apartmentUserProfile.getApartmentLocation().getCity();
        String district = apartmentUserProfile.getApartmentLocation().getDistrict();
        int zipCode = apartmentUserProfile.getApartmentLocation().getZipCode();

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
}
