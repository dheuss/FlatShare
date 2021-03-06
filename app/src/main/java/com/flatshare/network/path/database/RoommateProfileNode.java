package com.flatshare.network.path.database;

/**
 * Created by Arber on 08/01/2017.
 */
public class RoommateProfileNode {

    private final String rootPath;
    private final String apartmentId;
    private final String tenantsToShow;
    private final String timestamp;
    private final String available;
    private final String done;
//    private final String acceptedTenants;


    public RoommateProfileNode(String rootPath, String roommateId) {
        this.rootPath = rootPath + roommateId + "/";
        this.apartmentId = this.rootPath + "apartment_id/";
        this.tenantsToShow = this.rootPath + "tenants_to_show/";
        this.timestamp = this.rootPath + "timestamp/";
        this.available = this.rootPath + "available/";
        this.done = this.rootPath + "done/";
//        this.acceptedTenants = this.rootPath + "accepted_tenants/";
    }

    public String getRootPath() {
        return rootPath;
    }

//    public String getAcceptedTenants() {
//        return acceptedTenants;
//    }

    public String getTenantsToShow() {
        return tenantsToShow;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public String getAvailable() {
        return available;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDone() {
        return done;
    }
}
