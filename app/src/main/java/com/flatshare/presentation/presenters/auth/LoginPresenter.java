package com.flatshare.presentation.presenters.auth;

import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 05/12/2016.
 */

public interface LoginPresenter extends BasePresenter {

    void login(LoginDataType loginDataType);
    void loginGoogle();
    void loginFacebook();

    interface View extends BaseView {
        void changeToProfileActivity();
        void changeToMatchingActivity();

        void notifyRoommateGenerateQR(String roommateId);
    }

}
