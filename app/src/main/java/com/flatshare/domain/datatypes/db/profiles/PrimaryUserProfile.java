package com.flatshare.domain.datatypes.db.profiles;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

public class PrimaryUserProfile extends UserProfile{

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

    @Exclude
    @Override
    public String getId() {
        return null;
    }

    @Exclude
    @Override
    public int getType() {
        return -1;
    }

    @Exclude
    @Override
    public void setId(String id) {
        setId(id);
    }
}
