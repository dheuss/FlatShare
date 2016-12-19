package com.flatshare.network;

import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.domain.datatypes.auth.ResetDataType;

/**
 * Created by Arber on 06/12/2016.
 */

public interface AuthenticationManager {

    //or boolean
    void login(LoginDataType loginDataType);

    void register(RegisterDataType signUpDataType);

    void reset (ResetDataType resetDataType);

    void logOut();

    interface LoginCallback {
        void onLoginSuccessful();

        void onLoginFailed(String error);

    }

    interface RegisterCallBack {
        void onRegisterSuccessful();

        void onRegisterFailed(String error);

    }

    interface ResetCallBack {
        void onResetSuccessful();

        void onResetFailed(String error);
    }
}
