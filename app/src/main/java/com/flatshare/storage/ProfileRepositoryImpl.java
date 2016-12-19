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

    DatabaseManager databaseManager;

    private String userPath;
    private String tenantProfilesPath;
    private String apartmentProfilesPath;

    private String tenantsIdListPath;
    private String apartmentsIdListPath;


    public ProfileRepositoryImpl() {
        databaseManager = new DatabaseManagerImpl();
        tenantProfilesPath = "/tenant_profiles";
        apartmentProfilesPath = "/apartment_profiles";

        tenantsIdListPath = "/tenants_list";
        apartmentsIdListPath = "/apartments_list";
    }

    @Override
    public boolean createPrimaryProfile(PrimaryUserProfile primaryUserProfile) {

        // create main profile
        databaseManager.create(primaryUserProfile, userPath);

        return false;
    }

    @Override
    public boolean createTenantProfile(TenantUserProfile tenantUserProfile) {
        String tenantId = databaseManager.push(tenantUserProfile, tenantProfilesPath);
        databaseManager.addIdToList(tenantId, tenantsIdListPath);
        return false;
    }

    @Override
    public boolean createApartmentProfile(ApartmentUserProfile apartmentUserProfile) {
        String apartmentId = databaseManager.push(apartmentUserProfile, apartmentProfilesPath);
        databaseManager.addIdToList(apartmentId, apartmentsIdListPath);
//        addToAllApartments(apartmentId);
        //TODO: add to the list of all apartments
        //TODO: add to the right zip code list
        return false;
    }
}
