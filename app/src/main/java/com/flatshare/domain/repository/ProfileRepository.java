package com.flatshare.domain.repository;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;

/**
 * Created by Arber on 10/12/2016.
 */

public interface ProfileRepository {

    boolean createPrimaryProfile(PrimaryUserProfile isTenant);

    boolean createTenantProfile(TenantUserProfile tenantUserProfile);

    boolean createApartmentProfile(ApartmentUserProfile apartmentUserProfile);
}
