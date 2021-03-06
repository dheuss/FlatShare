package com.flatshare.presentation.presenters.profile;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 08/01/2017.
 */
public interface RoommateQRPresenter extends BasePresenter{


    void listenToDB(String roommateId);

    void checkIfApartmentProfileCreated();

    interface View extends BaseView{

        void onQRCodeRead();

        void changeToWaitingActivity();
    }

}
