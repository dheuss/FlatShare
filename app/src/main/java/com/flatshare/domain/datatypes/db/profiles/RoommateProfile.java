package com.flatshare.domain.datatypes.db.profiles;

import com.flatshare.domain.datatypes.enums.ProfileType;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 08/01/2017.
 */

public class RoommateProfile extends UserProfile {

    @PropertyName("roommate_id")
    public String roommateId;

    @PropertyName("nickname")
    public String nickname;

    @PropertyName("gender")
    public int gender;

    @PropertyName("owner")
    public boolean owner;

    @PropertyName("apartment_id")
    public String apartmentId;

    @PropertyName("potential_tenants")
    public List<String> potentialTenants;

    @PropertyName("timestamp")
    public long timestamp;

    @PropertyName("available")
    public boolean available;

    @PropertyName("profile_done")
    public boolean done;

    public RoommateProfile() {
        potentialTenants = new ArrayList<>();
        this.available = true;
    }

    @Exclude
    public String getRoommateId() {
        return roommateId;
    }

    @Exclude
    public void setRoommateId(String roommateId) {
        this.roommateId = roommateId;
    }

    @Exclude
    public boolean isOwner() {
        return owner;
    }

    @Exclude
    public void setOwner(boolean owner) {
        this.owner = owner;
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
    public List<String> getPotentialTenants() {
        return potentialTenants;
    }

    @Exclude
    public void setPotentialTenants(List<String> potentialTenants) {
        this.potentialTenants = potentialTenants;
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
    public boolean isAvailable() {
        return available;
    }

    @Exclude
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Exclude
    public String getNickname() {
        return nickname;
    }

    @Exclude
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
    public void setGender(int gender) {
        this.gender = gender;
    }

    @Exclude
    public int getGender() {
        return gender;
    }

    @Exclude
    @Override
    public String getId() {
        return getRoommateId();
    }

    @Exclude
    @Override
    public int getType() {
        return ProfileType.ROOMMATE.getValue();
    }
}
