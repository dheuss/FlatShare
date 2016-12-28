package com.flatshare.network.path.database;

/**
 * Created by Arber on 26/12/2016.
 */
public class ApartmentLocationsNode {

    private final String rootPath;
    private final String cities;


    public ApartmentLocationsNode(String rootPath) {
        this.rootPath = rootPath;
        this.cities = this.rootPath + "cities/";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getCities() {
        return cities;
    }

    public CitiesNode getCitiesNode(String city){
        return new CitiesNode(cities, city);
    }
}
