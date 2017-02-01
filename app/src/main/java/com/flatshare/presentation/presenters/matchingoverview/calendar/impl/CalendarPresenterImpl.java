package com.flatshare.presentation.presenters.matchingoverview.calendar.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.interactors.calendar.CalendarCheckAppointmentInteractor;
import com.flatshare.domain.interactors.calendar.CalendarInitInteractor;
import com.flatshare.domain.interactors.calendar.CalendarSendFinalInteractor;
import com.flatshare.domain.interactors.calendar.impl.CalendarCheckAppointmentInteractorImpl;
import com.flatshare.domain.interactors.calendar.impl.CalendarInitInteractorImpl;
import com.flatshare.domain.interactors.calendar.impl.CalendarSendFinalInteractorImpl;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.base.AbstractPresenter;
import com.flatshare.presentation.presenters.matchingoverview.calendar.CalendarPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sandro on 07.01.17.
 */

public class CalendarPresenterImpl extends AbstractPresenter implements CalendarPresenter, CalendarInitInteractor.Callback, CalendarSendFinalInteractor.Callback, CalendarCheckAppointmentInteractor.Callback {

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
    public void sendDateToTenant(List<String> dateList, List<String> timeList, String tenantID, String apartmentID) {
        CalendarInitInteractor calendarInteractor = new CalendarInitInteractorImpl(mMainThread, this, dateList, timeList, tenantID, apartmentID);
        calendarInteractor.execute();
    }

    @Override
    public void sendBackFromTenant(String finalDate, String tenantID, String apartmentID) {
        CalendarSendFinalInteractor calendarFinalInteractor = new CalendarSendFinalInteractorImpl(mMainThread, this, finalDate, tenantID, apartmentID);
        calendarFinalInteractor.execute();
    }

    @Override
    public void checkForAppointment(String tenantId, String apartmentId) {
        mView.showProgress();
        CalendarCheckAppointmentInteractor appointmentInteractor = new CalendarCheckAppointmentInteractorImpl(mMainThread, this, tenantId, apartmentId);
        appointmentInteractor.execute();

    }

    @Override
    public boolean checkForTenant() {
        return userState.getPrimaryUserProfile().getClassificationId() == ProfileType.TENANT.getValue();
    }


    @Override
    public void onSentFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }

    @Override
    public void onSentSuccess() {
        mView.hideProgress();
        if (checkForTenant()) {
            MatchEntry matchEntry = new MatchEntry();
            List<Long> appointmentList = matchEntry.getAppointmentsList();
            Log.d(TAG, "onSentSuccess: appointment " + appointmentList);
            List<String> dateList = new ArrayList<>();
            List<String> timeList = new ArrayList<>();
            for (int i = 0; i < appointmentList.size(); i++) {
                Date date = new Date(appointmentList.get(i));
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateText = df.format(date);
                String[] partsDateTime = dateText.split(" ");
                dateList.add(partsDateTime[0]);
                timeList.add(partsDateTime[1]);
            }
            mView.setDatesFromWG(dateList, timeList);
        } else {
            mView.datesSuccessfulToTenants();
        }
    }

    @Override
    public void onSentFinalFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }

    @Override
    public void onSentFinalSuccess() {
        MatchEntry matchEntry = new MatchEntry();
        long appointment = matchEntry.getAppointment();
        Date date = new Date(appointment);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateText = df.format(date);
        mView.showFinalDate(dateText);
    }

    @Override
    public void onAppointmentFailure(String errorMessage) {
        mView.hideProgress();
        onError(errorMessage);
    }

    @Override
    public void onAppointmentSuccess() {
        mView.hideProgress();
        MatchEntry matchEntry = new MatchEntry();
        List<Long> appointmentList = matchEntry.getAppointmentsList();
        Log.d(TAG, "onSentSuccess: appointment " + appointmentList);
        List<String> dateList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        for (int i = 0; i < appointmentList.size(); i++) {
            Date date = new Date(appointmentList.get(i));
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dateText = df.format(date);
            String[] partsDateTime = dateText.split(" ");
            dateList.add(partsDateTime[0]);
            timeList.add(partsDateTime[1]);
        }
        mView.setDatesFromWG(dateList, timeList);
    }
}
