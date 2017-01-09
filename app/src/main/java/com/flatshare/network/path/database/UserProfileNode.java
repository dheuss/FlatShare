package com.flatshare.network.path.database;

/**
 * Created by Arber on 23/12/2016.
 */
public class UserProfileNode {

    private final String rootPath;
    private final String classificationId;
    private final String tenantProfileId;
    private final String roommateProfileId;

    public UserProfileNode(String rootPath, String userId) {
        this.rootPath = rootPath + userId + "/";
        this.classificationId = this.rootPath + "classification_id/";
        this.tenantProfileId = this.rootPath + "tenant_profile_id/";
        this.roommateProfileId = this.rootPath + "roommate_profile_id/";

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

    public String getRoommateProfileId() {
        return roommateProfileId;
    }
}
