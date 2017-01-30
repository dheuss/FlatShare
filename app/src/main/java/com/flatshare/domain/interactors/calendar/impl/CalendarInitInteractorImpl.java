package com.flatshare.domain.interactors.calendar.impl;

import android.text.method.SingleLineTransformationMethod;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.calendar.CalendarInitInteractor;
import com.flatshare.domain.interactors.matchingoverview.MatchingOverviewInteractor;
import com.flatshare.presentation.presenters.matchingoverview.calendar.impl.CalendarPresenterImpl;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sandro on 16.01.17.
 */
public class CalendarInitInteractorImpl extends AbstractInteractor implements CalendarInitInteractor {

    private static final String TAG = "CalendarInteractorImpl";
    private final List<String> dateList, timeList;
    private final String tenantID, apartmentID;

    private CalendarInitInteractor.Callback mCallback;

    public CalendarInitInteractorImpl(MainThread mMainThread, Callback callback, List<String> dateList, List<String> timeList, String tenantID, String apartmentID) {
        super(mMainThread);
        this.mCallback = callback;
        this.dateList = dateList;
        this.timeList = timeList;
        this.tenantID = tenantID;
        this.apartmentID = apartmentID;

    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentSuccess();
            }
        });
    }

    @Override
    public void execute() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<Long> appointmentList = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++) {
            try {
                Date date = df.parse(dateList.get(i) + timeList.get(i));
                appointmentList.add(date.getTime());
            } catch (ParseException e) {
                Log.d(TAG, "execute: ", e);
            }
        }

        String path = databaseRoot.getMatchesNode(tenantID, apartmentID).getRootPath();

        MatchEntry matchEntry = new MatchEntry();

        matchEntry.setAppointmentsList(appointmentList);

        mDatabase.child(path).setValue(matchEntry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    notifySuccess();
                } else {
                    notifyError(databaseError.getMessage());
                }
            }
        });

    }
}
