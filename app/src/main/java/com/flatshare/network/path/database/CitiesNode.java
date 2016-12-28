package com.flatshare.network.path.database;

/**
 * Created by Arber on 26/12/2016.
 */
public class CitiesNode {

    private final String rootPath;
    private final String districts;

    public CitiesNode(String rootPath, String city) {
        this.rootPath = rootPath + city;
        this.districts = this.rootPath + "districts/";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getDistricts() {
        return districts;
    }

    public DistrictsNode getDistrictsNode(String district) {
        return new DistrictsNode(rootPath, district);
    }

}
