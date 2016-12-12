package com.flatshare.presentation.presenters;


import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        void displayWelcomeMessage(String msg);
    }
}
