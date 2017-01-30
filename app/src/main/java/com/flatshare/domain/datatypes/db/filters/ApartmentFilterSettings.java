package com.flatshare.domain.datatypes.db.filters;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
/**
 * Created by Arber on 06/12/2016.
 */
public class ApartmentFilterSettings extends FilterSettings {

    @PropertyName("age_from")
    public int ageFrom;

    @PropertyName("age_to")
    public int ageTo;

    @PropertyName("gender")
    public int gender;

    @PropertyName("smoker")
    public int smoker;

    @PropertyName("occupation")
    public String occupation;

    @PropertyName("pets_allowed")
    public int petsAllowed;

    public ApartmentFilterSettings() {
        this.ageFrom = Integer.MIN_VALUE;
        this.ageTo = Integer.MAX_VALUE;
        this.gender = 2;
        this.smoker = 2;
        this.petsAllowed = 2;
    }

    @Exclude
    public int getAgeFrom() {
        return ageFrom;
    }

    @Exclude
    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    @Exclude
    public int getAgeTo() {
        return ageTo;
    }

    @Exclude
    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    @Exclude
    public int getGender() {
        return gender;
    }

    @Exclude
    public void setGender(int gender) {
        this.gender = gender;
    }

    @Exclude
    public int getSmoker() {
        return smoker;
    }

    @Exclude
    public void setSmoker(int smoker) {
        this.smoker = smoker;
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
    public String getOccupation() {
        return occupation;
    }

    @Exclude
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
