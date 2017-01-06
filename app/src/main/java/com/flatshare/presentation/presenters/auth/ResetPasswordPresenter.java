package com.flatshare.presentation.presenters.auth;

import com.flatshare.domain.datatypes.auth.ResetDataType;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 19.12.2016.
 */

public interface ResetPasswordPresenter extends BasePresenter {

    void reset(String email);

    interface View extends BaseView{
        void changeToLoginActivity();
    }
}
