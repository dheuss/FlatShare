package com.flatshare.network.path.storage;

/**
 * Created by Arber on 28/12/2016.
 */
public class TenantsStorage {

    private final String rootPath = "tenants/";

    private final String images = "images/";
    private final String videos = "videos/";
    private final String tenantId;

    public TenantsStorage(String tenantId) {
        this.tenantId = tenantId + "/";
    }

    public String getImagesPath() {
        return rootPath + tenantId + images;
    }

    public String getVideosPath() {
        return rootPath + tenantId + videos;
    }
}
