package com.flatshare.network.path.database;

/**
 * Created by Arber on 26/12/2016.
 */
public class PotentialMatchesNode {

    private final String rootPath;
    private final String tenantYes;
    private final String apartmentYes;
    private final String timeoutInDays;


    public PotentialMatchesNode(String rootPath, String tenantId, String apartmentId) {
        this.rootPath = rootPath + tenantId + "." + apartmentId;
        this.tenantYes = rootPath + "tenant_yes/";
        this.apartmentYes= rootPath + "apartment_yes/";
        this.timeoutInDays= rootPath + "timeout_in_days/";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getTenantYes() {
        return tenantYes;
    }

    public String getApartmentYes() {
        return apartmentYes;
    }

    public String getTimeoutInDays() {
        return timeoutInDays;
    }
}
