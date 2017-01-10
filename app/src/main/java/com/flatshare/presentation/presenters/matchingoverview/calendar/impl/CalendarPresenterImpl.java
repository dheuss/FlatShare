package com.flatshare.presentation.presenters.matchingoverview.calendar.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matchingoverview.calendar.CalendarPresenter;

/**
 * Created by Sandro on 07.01.17.
 */

public class CalendarPresenterImpl extends AbstractPresenter implements CalendarPresenter {

    private static final String TAG = "CalendarPresenter";

    private CalendarPresenter.View mView;

    public CalendarPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        mView = view;
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

}
