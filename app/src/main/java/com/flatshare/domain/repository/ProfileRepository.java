package com.flatshare.domain.repository;

import com.google.firebase.database.DatabaseException;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;

/**
 * Created by Arber on 10/12/2016.
 */

public interface ProfileRepository {

    void createPrimaryProfile(PrimaryUserProfile isTenant) throws DatabaseException;

    void createTenantProfile(TenantUserProfile tenantUserProfile) throws DatabaseException;

    void createApartmentProfile(ApartmentUserProfile apartmentUserProfile) throws DatabaseException;
}
