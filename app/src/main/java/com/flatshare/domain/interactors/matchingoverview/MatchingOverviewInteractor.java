package com.flatshare.domain.interactors.matchingoverview;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by david on 15.01.2017.
 */

public interface MatchingOverviewInteractor extends Interactor {
    interface Callback {
        void onSentSuccess(); //int classificationId
        void onSentFailure(String error);
    }
}
