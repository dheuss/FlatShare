package com.flatshare.domain.datatypes.db.profiles;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

/**
 * Created by Arber on 11/12/2016.
 */

public class PrimaryUserProfile extends UserProfile {

    @PropertyName("classification_id")
    public int classificationId;

    @PropertyName("tenant_profile_id")
    public String tenantProfileId;

    @PropertyName("apartment_profile_id")
    public String apartmentProfileId;

    public PrimaryUserProfile(int classificationId) {
        this.classificationId = classificationId;
    }

    @Exclude
    public int getClassificationId() {
        return classificationId;
    }

    @Exclude
    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    @Exclude
    public String getTenantProfileId() {
        return tenantProfileId;
    }

    @Exclude
    public void setTenantProfileId(String tenantProfileId) {
        this.tenantProfileId = tenantProfileId;
    }

    @Exclude
    public String getApartmentProfileId() {
        return apartmentProfileId;
    }

    @Exclude
    public void setApartmentProfileId(String apartmentProfileId) {
        this.apartmentProfileId = apartmentProfileId;
    }
}
