package com.flatshare.domain.state;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 26/12/2016.
 */

public class UserState {

    private PrimaryUserProfile primaryUserProfile;

    private TenantUserProfile tenantUserProfile;
    private ApartmentUserProfile apartmentUserProfile;

    public PrimaryUserProfile getPrimaryUserProfile() {
        return primaryUserProfile;
    }

    public void setPrimaryUserProfile(PrimaryUserProfile primaryUserProfile) {
        this.primaryUserProfile = primaryUserProfile;
    }

    public TenantUserProfile getTenantUserProfile() {
        return tenantUserProfile;
    }

    public void setTenantUserProfile(TenantUserProfile tenantUserProfile) {
        this.tenantUserProfile = tenantUserProfile;
    }

    public ApartmentUserProfile getApartmentUserProfile() {
        return apartmentUserProfile;
    }

    public void setApartmentUserProfile(ApartmentUserProfile apartmentUserProfile) {
        this.apartmentUserProfile = apartmentUserProfile;
    }

    public String getTenantId(){
        return getTenantUserProfile().getTenantId();
    }

    public String getApartmentId(){
        return getApartmentUserProfile().getApartmentId();
    }

    public String getApartmentMainImageId(){
        return getApartmentUserProfile().getMainImageId();
    }

    public List<String> getApartmentImageIds(){
        return new ArrayList<>(getApartmentUserProfile().getImageIds());
    }

}
