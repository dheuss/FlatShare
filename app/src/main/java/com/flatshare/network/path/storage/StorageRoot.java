package com.flatshare.network.path.storage;

import com.flatshare.network.path.Node;

/**
 * Created by Arber on 23/12/2016.
 */

public class StorageRoot implements Node {

    private final String url = "gs://flatshare-5d4c6.appspot.com";
    private final String tenants = "tenants/";
    private final String apartments = "apartments/";
    private final String images = "images/";
    private final String videos = "videos/";

    public String getUrl() {
        return url;
    }

    public String getTenants() {
        return tenants;
    }

    public String getApartments() {
        return apartments;
    }

    public String getImages() {
        return images;
    }

    public String getVideos() {
        return videos;
    }
}
