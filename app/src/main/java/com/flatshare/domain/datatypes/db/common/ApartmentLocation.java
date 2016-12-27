package com.flatshare.domain.datatypes.db.common;

import com.google.firebase.database.PropertyName;

import com.flatshare.domain.datatypes.db.DatabaseItem;

/**
 * Created by Arber on 11/12/2016.
 */

public class ApartmentLocation implements DatabaseItem {

    @PropertyName("city")
    public String city;

    @PropertyName("district")
    public String district;

    @PropertyName("street")
    public String street;

    @PropertyName("street_number")
    public int streetNumber;

    @PropertyName("zip_code")
    public int zipCode;

    @PropertyName("house_nr")
    private String houseNr;

    public ApartmentLocation() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }
}
