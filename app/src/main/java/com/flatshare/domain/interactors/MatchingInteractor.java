package com.flatshare.domain.interactors;

import java.util.List;

import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface MatchingInteractor extends Interactor{

    interface Callback {
        void onMatchFound(List<UserProfile> userProfiles); // TODO: return someProfile?
        void onNoMatchFound();
    }
}
