package com.flatshare.domain.interactors.calendar.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.calendar.CalendarSendFinalInteractor;
import com.flatshare.presentation.presenters.matchingoverview.calendar.impl.CalendarPresenterImpl;

/**
 * Created by Sandro on 16.01.17.
 */
public class CalendarSendFinalInteractorImpl extends AbstractInteractor implements CalendarSendFinalInteractor {

    public CalendarSendFinalInteractorImpl(MainThread mMainThread, CalendarPresenterImpl calendarPresenter, String finalDate, String tenantID, String apartmentID) {
        super(mMainThread);
    }

    @Override
    public void execute() {

    }
}
