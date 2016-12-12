package com.flatshare.domain.datatypes.db.profiles;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

/**
 * Created by Arber on 11/12/2016.
 */

public class PrimaryUserProfile extends UserProfile {

    @PropertyName("classification_id")
    public int classificationId;

    @PropertyName("profile_id")
    public String profileId;

    public PrimaryUserProfile(int classificationId) {
        this.classificationId = classificationId;
    }

    @Exclude
    public int getClassificationId() {
        return classificationId;
    }

    @Exclude
    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    @Exclude
    public String getProfileId() {
        return profileId;
    }

    @Exclude
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
