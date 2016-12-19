package com.flatshare.presentation.presenters;

import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 05/12/2016.
 */

public interface LoginPresenter extends BasePresenter {

    void login(LoginDataType loginDataType);

    interface View extends BaseView {
        void changeToProfileActivity();
    }

}
