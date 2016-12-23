package com.flatshare.network.paths.database;

/**
 * Created by Arber on 23/12/2016.
 */

public class Root implements Node {


    private final String users = "users/";
    private final String tenantProfiles= "tenant_profiles/";
    private final String apartmentProfiles = "apartment_profiles/";


    public UserProfileNode getUserProfileNode(String userId){
        return new UserProfileNode(users, userId);
    }

    public TenantProfileNode getTenantProfileNode(String tenantId){
        return new TenantProfileNode(tenantProfiles, tenantId);
    }

    public ApartmentProfileNode getApartmentProfileNode(String apartmentId){
        return new ApartmentProfileNode(apartmentProfiles, apartmentId);
    }

    public String getUsers() {
        return users;
    }

    public String getTenantProfiles() {
        return tenantProfiles;
    }

    public String getApartmentProfiles() {
        return apartmentProfiles;
    }
}
