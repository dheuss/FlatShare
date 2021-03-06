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

    @PropertyName("dryer")
    public int dryer;

    @PropertyName("balcony")
    public int balcony;

    @PropertyName("bathtub")
    public int bathtub;

    @PropertyName("tv_cable")
    public int tvCable;

    @PropertyName("size_from")
    public int sizeFrom;

    @PropertyName("size_to")
    public int sizeTo;

    public TenantFilterSettings() {
        this.apartmentLocation = new ApartmentLocation();
        this.priceFrom = Integer.MIN_VALUE;
        this.priceTo = Integer.MAX_VALUE;
        this.areaFrom = Integer.MIN_VALUE;
        this.areaTo = Integer.MAX_VALUE;
        this.sizeFrom = Integer.MIN_VALUE;
        this.sizeTo = Integer.MAX_VALUE;
        this.internet = 2;
        this.smokerApartment = 2;
        this.petsAllowed = 2;
        this.purposeApartment = 2;
        this.washingMachine = 2;
        this.dryer = 2;
        this.balcony = 2;
        this.bathtub = 2;
        this.tvCable = 2;
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
    public int getDryer() {
        return dryer;
    }

    @Exclude
    public void setDryer(int dryer) {
        this.dryer = dryer;
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
    public int getBalcony() {
        return balcony;
    }

    public void setBalcony(int balcony) {
        this.balcony = balcony;
    }

    @Exclude
    public int getTvCable() {
        return tvCable;
    }

    @Exclude
    public void setTvCable(int tvCable) {
        this.tvCable = tvCable;
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

    @Exclude
    public int getSizeFrom() {
        return sizeFrom;
    }

    @Exclude
    public void setSizeFrom(int sizeFrom) {
        this.sizeFrom = sizeFrom;
    }

    @Exclude
    public int getSizeTo() {
        return sizeTo;
    }

    @Exclude
    public void setSizeTo(int sizeTo) {
        this.sizeTo = sizeTo;
    }

    @Exclude
    public int getInternet() {
        return internet;
    }

    @Exclude
    public int getWashingMachine() {
        return washingMachine;
    }

    @Exclude
    public int getPurposeApartment() {
        return purposeApartment;
    }

    @Exclude
    public int getSmokerApartment() {
        return smokerApartment;
    }
}
