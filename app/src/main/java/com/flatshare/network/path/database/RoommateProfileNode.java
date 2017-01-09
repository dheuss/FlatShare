package com.flatshare.network.path.database;

/**
 * Created by Arber on 08/01/2017.
 */
public class RoommateProfileNode {

    private final String rootPath;
    private final String apartmentId;
    private final String potentialTenants;
//    private final String acceptedTenants;


    public RoommateProfileNode(String rootPath, String roommateId) {
        this.rootPath = rootPath + roommateId + "/";
        this.apartmentId = this.rootPath + "apartment_id/";
        this.potentialTenants = this.rootPath + "potential_tenants/";
//        this.acceptedTenants = this.rootPath + "accepted_tenants/";
    }

    public String getRootPath() {
        return rootPath;
    }

//    public String getAcceptedTenants() {
//        return acceptedTenants;
//    }

    public String getPotentialTenants() {
        return potentialTenants;
    }

    public String getApartmentId() {
        return apartmentId;
    }
}
