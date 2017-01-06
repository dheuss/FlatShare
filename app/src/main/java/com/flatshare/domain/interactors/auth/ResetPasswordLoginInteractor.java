package com.flatshare.domain.interactors.auth;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by david on 06.01.2017.
 */

public interface ResetPasswordLoginInteractor extends Interactor{

    interface Callback{
        void onResetPasswordLoginSuccess();
        void onResetPasswordLoginFailure(String errorMessage);
    }
}
