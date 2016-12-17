package com.flatshare.domain.datatypes.db.profiles;

import com.flatshare.domain.datatypes.db.common.ApartmentLocation;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.List;

/**
 * Created by Arber on 06/12/2016.
 */

public class ApartmentUserProfile extends UserProfile {

    @PropertyName("apartment_filter_settings")
    public ApartmentFilterSettings apartmentFilterSettings;

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
    public boolean pets;

    @PropertyName("purpose_apartment")
    public boolean purposeApartment;

    @PropertyName("washing_machine")
    public boolean washingMachine;

//    @PropertyName("dryer")
//    public boolean dryer;

//    @PropertyName("balcony")
//    public boolean balcony;

//    @PropertyName("bathtub")
//    public boolean bathtub;

//    @PropertyName("tv_cable")
//    public boolean tvCable;

    @PropertyName("languages")
    public List<String> languages;

    @PropertyName("matched_tenant_ids")
    public List<String> matchedTenantIds;

    public ApartmentUserProfile() {
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
    public boolean hasInternet() {
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
    public boolean hasPets() {
        return pets;
    }

    @Exclude
    public void setPets(boolean hasPets) {
        this.pets = hasPets;
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
    public boolean hasWashingMachine() {
        return washingMachine;
    }

    @Exclude
    public void setWashingMachine(boolean washingMachine) {
        this.washingMachine = washingMachine;
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

    @Exclude
    public ApartmentFilterSettings getApartmentFilterSettings() {
        return apartmentFilterSettings;
    }

    @Exclude
    public void setApartmentFilterSettings(ApartmentFilterSettings apartmentFilterSettings) {
        this.apartmentFilterSettings = apartmentFilterSettings;
    }
}
