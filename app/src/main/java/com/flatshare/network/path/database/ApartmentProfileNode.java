package com.flatshare.network.path.database;

/**
 * Created by Arber on 23/12/2016.
 */
public class ApartmentProfileNode {


    private final String rootPath;

    private final String apartmentFilterSettings;
    private final String tenantsToShow;
    private final String matchedTenants;

    public ApartmentProfileNode(String rootPath, String apartmentId) {
        this.rootPath = rootPath + apartmentId;
        this.apartmentFilterSettings = rootPath + "apartment_filter_settings/";
        this.tenantsToShow = rootPath + "tenants_to_show/";
        this.matchedTenants = rootPath + "matched_tenants/";
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
}
