package com.flatshare.network.path.database;

/**
 * Created by Arber on 26/12/2016.
 */
public class DistrictsNode {

    private final String rootPath;
    private final String zipCodes;

    public DistrictsNode(String rootPath, String district) {
        this.rootPath = rootPath + district;
        this.zipCodes = rootPath + "zip_codes/";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getZipCodes() {
        return zipCodes;
    }

    public ZipCodesNode getZipCodesNode(int zipCode) {
        return new ZipCodesNode(rootPath, zipCode);
    }
}
