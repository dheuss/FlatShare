package com.flatshare.domain.interactors.profile;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 12/01/2017.
 */

public interface PrimaryProfileInteractor extends Interactor {

    interface Callback {

        void onProfileCreated(PrimaryUserProfile primaryUserProfile, UserProfile secondaryProfile);
        void onSentFailure(String error);

    }

}
