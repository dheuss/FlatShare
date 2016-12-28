package com.flatshare.network.path.storage;

import com.flatshare.network.path.Node;

/**
 * Created by Arber on 23/12/2016.
 */

public class StorageRoot implements Node {

    public static final String PROFILE = "profile";
    private final String url = "gs://flatshare-5d4c6.appspot.com";
    private final String tenants = "tenants/";

    public TenantsStorage getTenants() {
        return new TenantsStorage();
    }

    public ApartmentsStorage getApartments() {
        return new ApartmentsStorage();
    }

    public String getUrl() {
        return url;
    }
}
