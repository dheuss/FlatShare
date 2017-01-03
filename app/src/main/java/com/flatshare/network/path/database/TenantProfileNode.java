package com.flatshare.network.path.database;

/**
 * Created by Arber on 23/12/2016.
 */
public class TenantProfileNode {


    private final String rootPath;
    private final String tenantFilterSettings;
    private final String apartmentsToShow;
    private final String matchedApartments;

    public TenantProfileNode(String rootPath, String tenantId) {
        this.rootPath = rootPath + tenantId + "/";
        this.tenantFilterSettings = this.rootPath + "tenant_filter_settings/";
        this.apartmentsToShow = this.rootPath + "apartments_to_show/";
        this.matchedApartments = this.rootPath + "matched_apartments/";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getTenantFilterSettings() {
        return tenantFilterSettings;
    }

    public String getApartmentsToShow() {
        return apartmentsToShow;
    }

    public String getMatchedApartments() {
        return matchedApartments;
    }

}
