package com.flatshare.domain.interactors.calendar.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.calendar.CalendarInitInteractor;
import com.flatshare.presentation.presenters.matchingoverview.calendar.impl.CalendarPresenterImpl;

import java.util.List;

/**
 * Created by Sandro on 16.01.17.
 */
public class CalendarInitInteractorImpl extends AbstractInteractor implements CalendarInitInteractor {

    public CalendarInitInteractorImpl(MainThread mMainThread, CalendarPresenterImpl calendarPresenter, List<String> dateList, List<String> timeList, String tenantID, String apartmentID) {
        super(mMainThread);
    }

    @Override
    public void execute() {

    }
}
