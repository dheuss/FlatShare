package com.flatshare.domain.datatypes.db.filters;

import com.flatshare.domain.datatypes.db.common.ApartmentLocation;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

/**
 * Created by Arber on 06/12/2016.
 */

public class TenantFilterSettings extends FilterSettings {

    @PropertyName("apartment_location")
    public ApartmentLocation apartmentLocation;

    @PropertyName("price_from")
    public int priceFrom;

    @PropertyName("price_to")
    public int priceTo;

    @PropertyName("area_from")
    public int areaFrom;

    @PropertyName("area_to")
    public int areaTo;

    @PropertyName("internet")
    public int internet;

    @PropertyName("smoker_apartment")
    public int smokerApartment;

    @PropertyName("pets_allowed")
    public int petsAllowed;

    @PropertyName("purpose_apartment")
    public int purposeApartment;

    @PropertyName("washing_machine")
    public int washingMachine;

//    @PropertyName("dryer")
//    public int dryer;

//    @PropertyName("balcony")
//    public int balcony;

//    @PropertyName("bathtub")
//    public int bathtub;

//    @PropertyName("tv_cable")
//    public int tvCable;

    public TenantFilterSettings() {
    }

    @Exclude
    public ApartmentLocation getApartmentLocation() {
        return apartmentLocation;
    }

    @Exclude
    public void setApartmentLocation(ApartmentLocation apartmentLocation) {
        this.apartmentLocation = apartmentLocation;
    }

    @Exclude
    public int getPriceFrom() {
        return priceFrom;
    }

    @Exclude
    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    @Exclude
    public int getPriceTo() {
        return priceTo;
    }

    @Exclude
    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }

    @Exclude
    public int getAreaFrom() {
        return areaFrom;
    }

    @Exclude
    public void setAreaFrom(int areaFrom) {
        this.areaFrom = areaFrom;
    }

    @Exclude
    public int getAreaTo() {
        return areaTo;
    }

    @Exclude
    public void setAreaTo(int areaTo) {
        this.areaTo = areaTo;
    }

    @Exclude
    public int wantsInternet() {
        return internet;
    }

    @Exclude
    public void setInternet(int internet) {
        this.internet = internet;
    }

    @Exclude
    public int wantsSmokerApartment() {
        return smokerApartment;
    }

    @Exclude
    public void setSmokerApartment(int smokerApartment) {
        this.smokerApartment = smokerApartment;
    }

    @Exclude
    public int getPetsAllowed() {
        return petsAllowed;
    }

    @Exclude
    public void setPetsAllowed(int petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    @Exclude
    public int wantsPurposeApartment() {
        return purposeApartment;
    }

    @Exclude
    public void setPurposeApartment(int purposeApartment) {
        this.purposeApartment = purposeApartment;
    }

    @Exclude
    public int wantsWashingMachine() {
        return washingMachine;
    }

    @Exclude
    public void setWashingMachine(int washingMachine) {
        this.washingMachine = washingMachine;
    }

}
