package com.flatshare.network.path.database;

/**
 * Created by Arber on 26/12/2016.
 */
public class ZipCodesNode {

    private final String rootPath;
//    private final String apartmentIds;

    public ZipCodesNode(String rootPath, int zipCode) {
        this.rootPath = rootPath + zipCode;

    }

    public String getRootPath() {
        return rootPath;
    }
}
