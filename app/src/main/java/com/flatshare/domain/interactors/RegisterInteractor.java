package com.flatshare.domain.interactors;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 06/01/2017.
 */

public interface RegisterInteractor extends Interactor {

    interface Callback {
        void onRegisterSuccess();
        void onRegisterFailure(String errorMessage);
    }
}
