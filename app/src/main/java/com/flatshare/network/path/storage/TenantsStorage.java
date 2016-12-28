package com.flatshare.network.path.storage;

/**
 * Created by Arber on 28/12/2016.
 */
public class TenantsStorage {

    private final String rootPath = "tenants/";

    private final String images = "images/";
    private final String videos = "videos/";

    public String getImages() {
        return images;
    }

    public String getVideos() {
        return videos;
    }

    public String getRootPath() {
        return rootPath;
    }
}
