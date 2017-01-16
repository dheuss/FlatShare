package com.flatshare.presentation.presenters.matchingoverview.calendar.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matchingoverview.calendar.CalendarPresenter;

import java.util.List;

/**
 * Created by Sandro on 07.01.17.
 */

public class CalendarPresenterImpl extends AbstractPresenter implements CalendarPresenter {

    private static final String TAG = "CalendarPresenter";

    private CalendarPresenter.View mView;
    private UserState userState;

    public CalendarPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        mView = view;
        userState = UserState.getInstance();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String error) {
        mView.showError(error);
    }

    @Override
    public void send(List<String> dateList, List<String> timeList) {

    }

    @Override
    public boolean checkForTenant() {
        //TODO bist du tendant - aus FIrebase holen
        //userState.getPrimaryUserProfile().pro;
        return false;
    }

    @Override
    public void sendBackFromTendant(String finalDate) {
        //TODO call back with the final date
    }

}
