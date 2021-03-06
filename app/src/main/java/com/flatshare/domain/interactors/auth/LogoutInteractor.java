package com.flatshare.domain.interactors.auth;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 06/01/2017.
 */

public interface LogoutInteractor extends Interactor {

    interface Callback{
        void onLogoutSuccess();
        void onLogoutFail(String errorMessage);
    }
}
