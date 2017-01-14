package com.flatshare.domain.interactors.matching;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 14/01/2017.
 */

public interface PMatchesListenerInteractor extends Interactor {

    interface Callback {

        void onFailure(String errorMessage);

        void onListenerUpdated(boolean listenerAttached);

        void onMatchCreated(String key);
    }
}
