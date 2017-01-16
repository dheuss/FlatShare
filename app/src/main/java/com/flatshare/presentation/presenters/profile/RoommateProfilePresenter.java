package com.flatshare.presentation.presenters.profile;

import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 16/01/2017.
 */

public interface RoommateProfilePresenter extends BasePresenter {

    void sendProfile(RoommateProfile roommateProfile);
    void checkNicknameUnique(String nickname);

    interface View extends BaseView {
        void changeToApartmentProfileActivity();

        void onNicknameError(String error);

        void changeToRoommateQRActivity(String id);

        void onNicknameUnique();
    }

}
