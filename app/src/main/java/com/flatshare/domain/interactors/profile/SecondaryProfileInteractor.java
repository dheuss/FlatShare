package com.flatshare.domain.interactors.profile;

import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface SecondaryProfileInteractor extends Interactor {

    interface Callback {

        void onSentFailure(String error);
        void onProfileCreated(UserProfile profile);

    }

}
