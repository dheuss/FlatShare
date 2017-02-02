package com.flatshare.domain.interactors.matching;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 11/01/2017.
 */

public interface RoommateWaitingInteractor extends Interactor {

    interface Callback {

        void notifyError(String errorMessage);
        void onApartmentReady(ApartmentProfile apartmentProfile);
    }

}
