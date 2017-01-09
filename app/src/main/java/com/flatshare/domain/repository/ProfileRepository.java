package com.flatshare.domain.repository;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;

/**
 * Created by Arber on 10/12/2016.
 */

public interface ProfileRepository {

    boolean createPrimaryProfile(PrimaryUserProfile isTenant);

    boolean createTenantProfile(TenantProfile tenantProfile);

    boolean createApartmentProfile(ApartmentProfile apartmentProfile);

    PrimaryUserProfile getPrimaryProfile();

//    String getTenantProfileId();

//    String getApartmentProfileId();
}
