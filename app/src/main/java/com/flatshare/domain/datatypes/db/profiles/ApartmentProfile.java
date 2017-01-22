package com.flatshare.domain.datatypes.db.profiles;

import com.flatshare.domain.datatypes.db.common.ApartmentLocation;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 06/12/2016.
 */

public class ApartmentProfile extends UserProfile {

    @PropertyName("apartment_filter_settings")
    public ApartmentFilterSettings apartmentFilterSettings;

    @PropertyName("apartment_id")
    public String apartmentId;

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

//    @PropertyName("image_ids")
//    public List<String> imageIds;
//
//    @PropertyName("main_image_id")
//    public String mainImageId;

    @PropertyName("languages")
    public List<String> languages;

    @Exclude
    @PropertyName("matched_tenant_ids")
    public List<String> matchedTenantIds;

    @PropertyName("profile_done")
    public boolean done;

    @PropertyName("roomSize")
    public int roomSize;

    @PropertyName("apartmentSize")
    public int apartmentSize;

    public ApartmentProfile() {
        this.roommateIds = new ArrayList<>();
        this.apartmentLocation = new ApartmentLocation();
        this.languages = new ArrayList<>();
        this.matchedTenantIds = new ArrayList<>();
    }

    @Exclude
    public String getOwnerUserId() {
        return ownerUserId;
    }

    @Exclude
    public void setOwnerRoommateId(String ownerUserId) {
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

    @Exclude
    public String getApartmentId() {
        return apartmentId;
    }

    @Exclude
    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Exclude
    public boolean isDone() {
        return done;
    }

    @Exclude
    public void setDone(boolean done) {
        this.done = done;
    }

    @Exclude
    public int getRoomSize() {
        return roomSize;
    }

    @Exclude
    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    @Exclude
    public int getApartmentSize(){
        return apartmentSize;
    }

    @Exclude
    public void setApartmentSize(int apartmentSize){
        this.apartmentSize = apartmentSize;
    }

    @Exclude
    @Override
    public String getId() {
        return getApartmentId();
    }

    @Exclude
    @Override
    public int getType() {
        return ProfileType.APARTMENT.getValue();
    }

    @Exclude
    @Override
    public void setId(String id) {
        setApartmentId(id);
    }
}
