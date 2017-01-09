package com.flatshare.domain.datatypes.db.profiles;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

/**
 * Created by Arber on 11/12/2016.
 */

public class PrimaryUserProfile {

    @PropertyName("classification_id")
    public int classificationId;

    @PropertyName("tenant_profile_id")
    public String tenantProfileId;

    @PropertyName("roommate_profile_id")
    public String roommateProfileId;

    @PropertyName("email_address")
    public String email;

    public PrimaryUserProfile() {
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
    public String getRoommateProfileId() {
        return roommateProfileId;
    }

    @Exclude
    public void setRoommateProfileId(String roommateProfileId) {
        this.roommateProfileId = roommateProfileId;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    @Exclude
    public void setEmail(String email) {
        this.email = email;
    }

}
