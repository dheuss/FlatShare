package com.flatshare.network.path.database;

/**
 * Created by Arber on 23/12/2016.
 */
public class ApartmentProfileNode {


    private final String rootPath;

    private final String apartmentFilterSettings;
    private final String tenantsToShow;
    private final String matchedTenants;
    private final String timestamp;

    public ApartmentProfileNode(String rootPath, String apartmentId) {
        this.rootPath = rootPath + apartmentId + "/";
        this.apartmentFilterSettings = this.rootPath + "apartment_filter_settings/";
        this.tenantsToShow = this.rootPath + "tenants_to_show/";
        this.matchedTenants = this.rootPath + "matched_tenants/";
        this.timestamp = this.rootPath + "timestamp/";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getApartmentFilterSettings() {
        return apartmentFilterSettings;
    }

    public String getTenantsToShow() {
        return tenantsToShow;
    }

    public String getMatchedTenants() {
        return matchedTenants;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
