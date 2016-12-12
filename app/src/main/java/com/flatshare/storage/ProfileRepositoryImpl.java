package com.flatshare.storage;

import com.google.firebase.database.DatabaseException;

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

    DatabaseManager databaseManager;

    private String userPath;
    private String tenantProfilesPath;
    private String apartmentProfilesPath;

    private String userId;
    private String tenantId;
    private String apartmentId;


    public ProfileRepositoryImpl() {
        databaseManager = new DatabaseManagerImpl();

        userId = databaseManager.getUserId();
        tenantId = userId.substring(0, userId.length() / 2);
        apartmentId = userId.substring(userId.length() / 2, userId.length());

        userPath = "users/" + userId + "/";
        tenantProfilesPath = "/tenant_profiles/"; // TODO: make sure its an even number
        apartmentProfilesPath = "/apartment_profiles/";
    }

    @Override
    public void createPrimaryProfile(PrimaryUserProfile primaryUserProfile) throws DatabaseException {

        // create main profile
        databaseManager.create(primaryUserProfile, userPath);

        // add empty tenant profile TODO: remove, maybe redundant
//        databaseManager.addJsonRoot(tenantProfilesPath + tenantId, "empty");

        // add empty apartment profile TODO: remove, maybe redundant
//        databaseManager.addJsonRoot(apartmentProfilesPath + apartmentId, "empty");
    }

    @Override
    public void createTenantProfile(TenantUserProfile tenantUserProfile) throws DatabaseException {
        databaseManager.create(tenantUserProfile, tenantProfilesPath + tenantId);
        //TODO: add to the list of all tenants
    }

    @Override
    public void createApartmentProfile(ApartmentUserProfile apartmentUserProfile) throws DatabaseException {
        databaseManager.create(apartmentUserProfile, apartmentProfilesPath + apartmentId);

//        addToAllApartments(apartmentId);
        //TODO: add to the list of all apartments
        //TODO: add to the right zip code list
    }
}
