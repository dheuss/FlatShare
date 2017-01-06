package com.flatshare.domain.interactors.auth;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by david on 06.01.2017.
 */

public interface ResetPasswordInteractor extends Interactor {

    interface Callback{
        void onResetPasswordSuccess();
        void onResetPasswordFailure(String errorMessage);
    }
}
