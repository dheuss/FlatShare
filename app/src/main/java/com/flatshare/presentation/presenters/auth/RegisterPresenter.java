package com.flatshare.presentation.presenters.auth;


import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 05/12/2016.
 */

public interface RegisterPresenter extends BasePresenter {

    void register(RegisterDataType registerDataType);

    interface View extends BaseView {
        void changeToProfileActivity();
    }

}
