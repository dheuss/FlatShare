package com.flatshare.presentation.presenters.matching;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 08/01/2017.
 */
public interface RoommateProfilePresenter extends BasePresenter{


    void listenToDB(String roommateId);

    interface View extends BaseView{

        void changeToSwipingActivity();
    }

}
