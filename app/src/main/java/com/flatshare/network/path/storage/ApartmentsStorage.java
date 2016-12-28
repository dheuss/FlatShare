package com.flatshare.network.path.storage;

/**
 * Created by Arber on 28/12/2016.
 */
public class ApartmentsStorage {

    private final String rootPath = "apartments/";

    private final String images = "images/";
//    private final String videos = "videos/";

    public String getImages() {
        return images;
    }

    public String getRootPath() {
        return rootPath;
    }
}
