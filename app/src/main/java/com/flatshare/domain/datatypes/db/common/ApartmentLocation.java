package com.flatshare.domain.datatypes.db.common;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import com.flatshare.domain.datatypes.db.DatabaseItem;

/**
 * Created by Arber on 11/12/2016.
 */

public class ApartmentLocation implements DatabaseItem {

    @PropertyName("city")
    public String city;

    @PropertyName("state")
    public String state;

    @PropertyName("country")
    public String country;

    @PropertyName("district")
    public String district;

    @PropertyName("street")
    public String street;

    @PropertyName("zip_code")
    public int zipCode;

    @PropertyName("house_nr")
    private String houseNr;

    public ApartmentLocation() {
        this.city = "Munich";
        this.state = "Bavaria";
        this.country = "Germany";
        this.district = "Schwabing";
        this.street = "someStreet";
        this.zipCode = 80805;
        this.houseNr = "16";
    }

    @Exclude
    public String getCity() {
        return city;
    }

    @Exclude
    public void setCity(String city) {
        this.city = city;
    }

    @Exclude
    public String getDistrict() {
        return district;
    }

    @Exclude
    public void setDistrict(String district) {
        this.district = district;
    }

    @Exclude
    public String getStreet() {
        return street;
    }

    @Exclude
    public void setStreet(String street) {
        this.street = street;
    }

    @Exclude
    public int getZipCode() {
        return zipCode;
    }

    @Exclude
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Exclude
    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    @Exclude
    public String getHouseNr() {
        return houseNr;
    }

    @Exclude
    public String getState() {
        return state;
    }

    @Exclude
    public void setState(String state) {
        this.state = state;
    }

    @Exclude
    public String getCountry() {
        return country;
    }

    @Exclude
    public void setCountry(String country) {
        this.country = country;
    }
}