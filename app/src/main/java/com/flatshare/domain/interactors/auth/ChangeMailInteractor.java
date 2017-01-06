package com.flatshare.domain.interactors.auth;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 06/01/2017.
 */

public interface ChangeMailInteractor extends Interactor {

    interface Callback {
        void onChangeMailSuccess();
        void onChangeMailFailure(String errorMessage);
    }
}
