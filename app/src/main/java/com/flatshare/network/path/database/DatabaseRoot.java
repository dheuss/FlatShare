package com.flatshare.network.path.database;

import com.flatshare.network.path.Node;

/**
 * Created by Arber on 23/12/2016.
 */

public class DatabaseRoot implements Node {


    private final String users = "users/";
    private final String tenantProfiles= "tenant_profiles/";
    private final String apartmentProfiles = "apartment_profiles/";
    private final String potentialMatches = "potential_matches/";
    private final String apartmentLocations = "apartment_locations/";



    public UserProfileNode getUserProfileNode(String userId){
        return new UserProfileNode(users, userId);
    }

    public TenantProfileNode getTenantProfileNode(String tenantId){
        return new TenantProfileNode(tenantProfiles, tenantId);
    }

    public ApartmentProfileNode getApartmentProfileNode(String apartmentId){
        return new ApartmentProfileNode(apartmentProfiles, apartmentId);
    }

    public ApartmentLocationsNode getApartmentLocationsNode(){
        return new ApartmentLocationsNode(apartmentLocations);
    }

    public PotentialMatchesNode getPotentialMatchesNode(String tenantId, String apartmentId){
        return new PotentialMatchesNode(potentialMatches, tenantId, apartmentId);
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

    public String getPotentialMatches() {
        return potentialMatches;
    }

    public String getApartmentLocations() {
        return apartmentLocations;
    }
}