package com.flatshare.presentation.presenters.matchingoverview.calendar.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.interactors.calendar.CalendarInitInteractor;
import com.flatshare.domain.interactors.calendar.CalendarSendFinalInteractor;
import com.flatshare.domain.interactors.calendar.impl.CalendarInitInteractorImpl;
import com.flatshare.domain.interactors.calendar.impl.CalendarSendFinalInteractorImpl;
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
    public void sendDateToTendant(List<String> dateList, List<String> timeList, String tenantID, String apartmentID) {
        CalendarInitInteractor calendarInteractor = new CalendarInitInteractorImpl(mMainThread, this, dateList, timeList, tenantID, apartmentID);
        calendarInteractor.execute();
    }

    @Override
    public void sendBackFromTenant(String finalDate, String tenantID, String apartmentID) {
        CalendarSendFinalInteractor calendarFinalInteractor = new CalendarSendFinalInteractorImpl(mMainThread, this, finalDate, tenantID, apartmentID);
        calendarFinalInteractor.execute();
    }

    @Override
    public boolean checkForTenant() {
        return userState.getPrimaryUserProfile().getClassificationId() == ProfileType.TENANT.getValue();
    }


}
