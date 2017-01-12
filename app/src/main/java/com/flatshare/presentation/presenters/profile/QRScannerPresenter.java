package com.flatshare.presentation.presenters.profile;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 10/01/2017.
 */

public interface QRScannerPresenter extends BasePresenter {

    void addRoomate(String roommateId);

    interface View extends BaseView {

        void displayReadCode(String roommateId);
    }
}
