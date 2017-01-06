package com.flatshare.domain.interactors.profile;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface ProfileInteractor extends Interactor {

    interface Callback {
        // TODO: CHANGE parameter type?
        void onSentSuccess(int classificationId);
        void onSentFailure(String error);
    }

}
