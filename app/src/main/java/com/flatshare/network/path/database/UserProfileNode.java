package com.flatshare.network.path.database;

/**
 * Created by Arber on 23/12/2016.
 */
public class UserProfileNode {

    private final String rootPath;
    private final String classificationId;
    private final String tenantProfileId;
    private final String apartmentProfileId;

    public UserProfileNode(String rootPath, String userId) {
        this.rootPath = rootPath + userId + "/";
        this.classificationId = this.rootPath + "classification_id/";
        this.tenantProfileId = this.rootPath + "tenant_profile_id/";
        this.apartmentProfileId = this.rootPath + "apartment_profile_id/";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public String getTenantProfileId() {
        return tenantProfileId;
    }

    public String getApartmentProfileId() {
        return apartmentProfileId;
    }
}
