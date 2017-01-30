package com.flatshare.network;

import com.flatshare.domain.datatypes.auth.ChangeMailAddressDataType;
import com.flatshare.domain.datatypes.auth.ChangePasswordDataType;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.domain.datatypes.auth.ResetDataType;
/**
 * Created by david on 06/12/2016.
 */

public interface AuthenticationManager {

    void login(LoginDataType loginDataType);
    void register(RegisterDataType signUpDataType);
    void reset (ResetDataType resetDataType);
    void changeMail(ChangeMailAddressDataType changeMailAddressDataType);
    void changePassword(ChangePasswordDataType changePasswordDataType);
    void removeUser();
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

    interface ChangeMailCallBack {
        void onChangeMailSuccessful();
        void onChangedMailFailed(String error);
    }

    interface ChangePasswordCallBack {
        void onChangePasswordSuccessful();
        void onChangePasswordFailed(String error);
    }

    interface RemoveUserCallBack {
        void onRemoveUserSuccessful();
        void onRemoveUserFailed(String error);
    }

    interface LogOutCallBack {
        void onLogOutSuccessful();
        void onLogOutFailed(String error);
    }
}
