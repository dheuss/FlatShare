package com.flatshare.presentation.presenters;

import com.flatshare.domain.datatypes.auth.ChangeMailAddressDataType;
import com.flatshare.domain.datatypes.auth.ChangePasswordDataType;
import com.flatshare.domain.datatypes.auth.ResetDataType;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 29.12.2016.
 */

public interface SettingsPresenter extends BasePresenter{

    void reset(ResetDataType resetDataType);
    void changeMail(ChangeMailAddressDataType changeMailAddressDataType);
    void changePassword(ChangePasswordDataType changePasswordDataType);
    void removeUser();
    void logOut();

    interface View extends BaseView {

    }
}
