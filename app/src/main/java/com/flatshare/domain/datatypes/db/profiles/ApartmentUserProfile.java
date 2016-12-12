package com.flatshare.domain.datatypes.db.profiles;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

import com.flatshare.domain.datatypes.db.common.ApartmentLocation;

/**
 * Created by Arber on 06/12/2016.
 */

public class ApartmentUserProfile extends UserProfile {

    @PropertyName("owner_user_id")
    public String ownerUserId;

    @PropertyName("roommate_ids")
    public List<String> roommateIds;

    @PropertyName("apartment_info")
    public String apartmentInfo;

    @PropertyName("price")
    public int price;

    @PropertyName("area")
    public int area;

    @PropertyName("apartment_location")
    public ApartmentLocation apartmentLocation;

    @PropertyName("internet")
    public boolean internet;

    @PropertyName("smoker_apartment")
    public boolean smokerApartment;

    @PropertyName("pets")
    public List<String> pets;

    @PropertyName("purpose_apartment")
    public boolean purposeApartment;

    @PropertyName("washing_machine")
    public boolean waschingMachine;

    @PropertyName("dryer")
    public boolean dryer;

    @PropertyName("balcony")
    public boolean balcony;

    @PropertyName("bathtub")
    public boolean bathtub;


    @PropertyName("tv_cable")
    public boolean tvCable;

    @PropertyName("languages")
    public List<String> languages;


    @PropertyName("matched_tenant_ids")
    public List<String> matchedTenantIds;


    public ApartmentUserProfile(String ownerUserId, List<String> roommateIds, String apartmentInfo, int price, int area, ApartmentLocation apartmentLocation, boolean internet, boolean smokerApartment, List<String> pets, boolean purposeApartment, boolean waschingMachine, boolean dryer, boolean balcony, boolean bathtub, boolean tvCable, List<String> languages) {
        this.ownerUserId = ownerUserId;
        this.roommateIds = roommateIds;
        this.apartmentInfo = apartmentInfo;
        this.price = price;
        this.area = area;
        this.apartmentLocation = apartmentLocation;
        this.internet = internet;
        this.smokerApartment = smokerApartment;
        this.pets = pets;
        this.purposeApartment = purposeApartment;
        this.waschingMachine = waschingMachine;
        this.dryer = dryer;
        this.balcony = balcony;
        this.bathtub = bathtub;
        this.tvCable = tvCable;
        this.languages = languages;

        this.matchedTenantIds = new ArrayList<>();
    }

    @Exclude
    public String getOwnerUserId() {
        return ownerUserId;
    }

    @Exclude
    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Exclude
    public List<String> getRoommateIds() {
        return roommateIds;
    }

    @Exclude
    public void setRoommateIds(List<String> roommateIds) {
        this.roommateIds = roommateIds;
    }

    @Exclude
    public String getApartmentInfo() {
        return apartmentInfo;
    }

    @Exclude
    public void setApartmentInfo(String apartmentInfo) {
        this.apartmentInfo = apartmentInfo;
    }

    @Exclude
    public int getPrice() {
        return price;
    }

    @Exclude
    public void setPrice(int price) {
        this.price = price;
    }

    @Exclude
    public int getArea() {
        return area;
    }

    @Exclude
    public void setArea(int area) {
        this.area = area;
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
    public boolean isInternet() {
        return internet;
    }

    @Exclude
    public void setInternet(boolean internet) {
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
    public List<String> getPets() {
        return pets;
    }

    @Exclude
    public void setPets(List<String> pets) {
        this.pets = pets;
    }

    @Exclude
    public boolean isPurposeApartment() {
        return purposeApartment;
    }

    @Exclude
    public void setPurposeApartment(boolean purposeApartment) {
        this.purposeApartment = purposeApartment;
    }

    @Exclude
    public boolean isWaschingMachine() {
        return waschingMachine;
    }

    @Exclude
    public void setWaschingMachine(boolean waschingMachine) {
        this.waschingMachine = waschingMachine;
    }

    @Exclude
    public boolean isDryer() {
        return dryer;
    }

    @Exclude
    public void setDryer(boolean dryer) {
        this.dryer = dryer;
    }

    @Exclude
    public boolean isBalcony() {
        return balcony;
    }

    @Exclude
    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    @Exclude
    public boolean isBathtub() {
        return bathtub;
    }

    @Exclude
    public void setBathtub(boolean bathtub) {
        this.bathtub = bathtub;
    }

    @Exclude
    public boolean isTvCable() {
        return tvCable;
    }

    @Exclude
    public void setTvCable(boolean tvCable) {
        this.tvCable = tvCable;
    }

    @Exclude
    public List<String> getLanguages() {
        return languages;
    }

    @Exclude
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @Exclude
    public List<String> getMatchedTenantIds() {
        return matchedTenantIds;
    }

    @Exclude
    public void setMatchedTenantIds(List<String> matchedTenantIds) {
        this.matchedTenantIds = matchedTenantIds;
    }
}
