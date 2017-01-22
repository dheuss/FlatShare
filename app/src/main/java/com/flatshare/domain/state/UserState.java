package com.flatshare.domain.state;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arber on 26/12/2016.
 */

public class UserState {

    // Singleton
    static final UserState INSTANCE = new UserState();

    private UserState() {

    }

    public static UserState getInstance() {
        return UserState.INSTANCE;
    }
    // Singleton Done

    private boolean loggedIn;

    private PrimaryUserProfile primaryUserProfile;
    private TenantProfile tenantProfile;
    private RoommateProfile roommateProfile;
    private ApartmentProfile apartmentProfile;

    public PrimaryUserProfile getPrimaryUserProfile() {
        return primaryUserProfile;
    }

    public void setPrimaryUserProfile(PrimaryUserProfile primaryUserProfile) {
        this.primaryUserProfile = primaryUserProfile;
    }

    public TenantProfile getTenantProfile() {
        return tenantProfile;
    }

    public void setTenantProfile(TenantProfile tenantProfile) {
        this.tenantProfile = tenantProfile;
    }

    public ApartmentProfile getApartmentProfile() {
        return apartmentProfile;
    }

    public void setApartmentProfile(ApartmentProfile apartmentProfile) {
        this.apartmentProfile = apartmentProfile;
    }

    public RoommateProfile getRoommateProfile() {
        return roommateProfile;
    }

    public void setRoommateProfile(RoommateProfile roommateProfile) {
        this.roommateProfile = roommateProfile;
    }

    public String getTenantId() {
        return getTenantProfile().getTenantId();
    }

    public String getRoommateId() {
        return getRoommateProfile().getRoommateId();
    }

    public String getApartmentId() {
        return getApartmentProfile().getApartmentId();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
