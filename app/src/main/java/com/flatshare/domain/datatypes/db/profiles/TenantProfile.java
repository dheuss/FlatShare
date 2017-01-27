package com.flatshare.domain.datatypes.db.profiles;

import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

public class TenantProfile extends UserProfile {

    @PropertyName("tenant_filter_settings")
    public TenantFilterSettings tenantFilterSettings;

    @PropertyName("user_id")
    public String userId;

    @PropertyName("tenant_id")
    public String tenantId;

    @PropertyName("first_name")
    public String firstName;

    @PropertyName("email")
    public String email;

    @PropertyName("age")
    public int age;

    @PropertyName("gender")
    public int gender;

    @PropertyName("smoker")
    public boolean smoker;

    @PropertyName("occupation")
    public String occupation;

    @PropertyName("short_bio")
    public String shortBio;

    @PropertyName("pets")
    public boolean pets;

    @PropertyName("daily_token_consumed")
    public boolean dailyTokenConsumed;

    @PropertyName("apartments_to_show")
    public List<String> apartmentsToShow;

    @Exclude
    @PropertyName("matched_apartments")
    public List<String> matchedApartments;

    @PropertyName("timestamp")
    public long timestamp;

    @PropertyName("profile_done")
    public boolean done;

    public TenantProfile() {
        this.apartmentsToShow = new ArrayList<>();
        this.matchedApartments = new ArrayList<>();
    }

    @Exclude
    public String getUserId() {
        return userId;
    }

    @Exclude
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Exclude
    public String getFirstName() {
        return firstName;
    }

    @Exclude
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
    public boolean isSmoker() {
        return smoker;
    }

    @Exclude
    public void setSmoker(boolean smoker) {
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
    public String getShortBio() {
        return shortBio;
    }

    @Exclude
    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    @Exclude
    public boolean hasPets() {
        return pets;
    }

    @Exclude
    public void setPets(boolean pets) {
        this.pets = pets;
    }

    @Exclude
    public boolean isDailyTokenConsumed() {
        return dailyTokenConsumed;
    }

    @Exclude
    public void setDailyTokenConsumed(boolean dailyTokenConsumed) {
        this.dailyTokenConsumed = dailyTokenConsumed;
    }

    @Exclude
    public List<String> getApartmentsToShow() {
        return apartmentsToShow;
    }

    @Exclude
    public void setApartmentsToShow(List<String> apartmentsToShow) {
        this.apartmentsToShow = apartmentsToShow;
    }

    @Exclude
    public List<String> getMatchedApartments() {
        return matchedApartments;
    }

    @Exclude
    public void setMatchedApartments(List<String> matchedApartments) {
        this.matchedApartments = matchedApartments;
    }

    @Exclude
    public TenantFilterSettings getTenantFilterSettings() {
        return tenantFilterSettings;
    }

    @Exclude
    public void setTenantFilterSettings(TenantFilterSettings tenantFilterSettings) {
        this.tenantFilterSettings = tenantFilterSettings;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    @Exclude
    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getTenantId() {
        return tenantId;
    }

    @Exclude
    public long getTimestamp() {
        return timestamp;
    }

    @Exclude
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Exclude
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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
    @Override
    public String getId() {
        return getTenantId();
    }

    @Exclude
    @Override
    public int getType() {
        return ProfileType.TENANT.getValue();
    }

    @Exclude
    @Override
    public void setId(String id) {
        setTenantId(id);
    }
}
