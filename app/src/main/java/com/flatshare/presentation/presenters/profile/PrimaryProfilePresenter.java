package com.flatshare.presentation.presenters.profile;

import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 08/12/2016.
 */

public interface PrimaryProfilePresenter extends BasePresenter {

    void sendProfile(PrimaryUserProfile primaryUserProfile);

    void createRoommateProfile();

    interface View extends BaseView {
        void changeToTenantProfile();
        void changeToApartmentProfile();
        void changeToRoomateQR(String roommateId);
    }

}
