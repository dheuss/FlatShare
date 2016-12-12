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
    public boolean smokerApartment;

    @PropertyName("pets_allowed")
    public int petsAllowed;

    @PropertyName("purpose_apartment")
    public int purposeApartment;

    @PropertyName("washing_machine")
    public int waschingMachine;

    @PropertyName("dryer")
    public int dryer;

    @PropertyName("balcony")
    public int balcony;

    @PropertyName("bathtub")
    public int bathtub;

    @PropertyName("tv_cable")
    public int tvCable;

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
    public int getInternet() {
        return internet;
    }

    @Exclude
    public void setInternet(int internet) {
        this.internet = internet;
    }

    @Exclude
    public boolean isSmokerApartment() {
        return smokerApartment;
    }

    @Exclude
    public void setSmokerApartment(boolean smokerApartment) {
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
    public int getPurposeApartment() {
        return purposeApartment;
    }

    @Exclude
    public void setPurposeApartment(int purposeApartment) {
        this.purposeApartment = purposeApartment;
    }

    @Exclude
    public int getWaschingMachine() {
        return waschingMachine;
    }

    @Exclude
    public void setWaschingMachine(int waschingMachine) {
        this.waschingMachine = waschingMachine;
    }

    @Exclude
    public int getDryer() {
        return dryer;
    }

    @Exclude
    public void setDryer(int dryer) {
        this.dryer = dryer;
    }

    @Exclude
    public int getBalcony() {
        return balcony;
    }

    @Exclude
    public void setBalcony(int balcony) {
        this.balcony = balcony;
    }

    @Exclude
    public int getBathtub() {
        return bathtub;
    }

    @Exclude
    public void setBathtub(int bathtub) {
        this.bathtub = bathtub;
    }

    @Exclude
    public int getTvCable() {
        return tvCable;
    }

    @Exclude
    public void setTvCable(int tvCable) {
        this.tvCable = tvCable;
    }
}
