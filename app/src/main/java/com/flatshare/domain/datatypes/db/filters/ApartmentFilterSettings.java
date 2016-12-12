package com.flatshare.domain.datatypes.db.filters;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

/**
 * Created by Arber on 06/12/2016.
 */

public class ApartmentFilterSettings extends FilterSettings {

    @PropertyName("age")
    public int age;

    @PropertyName("gender")
    public int gender;

    @PropertyName("smoker")
    public int smoker;

    @PropertyName("occupation")
    public String occupation;

    @PropertyName("duration_of_stay")
    public int durationOfStay;

    @PropertyName("pets_allowed")
    public int petsAllowed;

    public ApartmentFilterSettings() {
    }

    @Exclude
    public int getAge() {
        return age;
    }

    @Exclude
    public void setAge(int age) {
        this.age = age;
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
    public String getOccupation() {
        return occupation;
    }

    @Exclude
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Exclude
    public int getDurationOfStay() {
        return durationOfStay;
    }

    @Exclude
    public void setDurationOfStay(int durationOfStay) {
        this.durationOfStay = durationOfStay;
    }

    @Exclude
    public int getPetsAllowed() {
        return petsAllowed;
    }

    @Exclude
    public void setPetsAllowed(int petsAllowed) {
        this.petsAllowed = petsAllowed;
    }
}
