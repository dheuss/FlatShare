package com.flatshare.domain.interactors.calendar.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.calendar.CalendarCheckAppointmentInteractor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;


/**
 * Created by Sandro on 30.01.17.
 */

public class CalendarCheckAppointmentInteractorImpl extends AbstractInteractor implements CalendarCheckAppointmentInteractor {

    private static final String TAG = "CalendarCheckAppointmentInteractorImpl";

    private final String tenantID;
    private final String apartmentID;

    private CalendarCheckAppointmentInteractor.Callback mCallback;

    public CalendarCheckAppointmentInteractorImpl(MainThread mMainThread, CalendarCheckAppointmentInteractor.Callback callback, String tenantId, String apartmentId) {
        super(mMainThread);
        this.tenantID = tenantId;
        this.apartmentID = apartmentId;
        this.mCallback = callback;
    }

    @Override
    public void execute() {

        String path = databaseRoot.getMatchesNode(tenantID, apartmentID).getRootPath();

        final MatchEntry matchEntry = new MatchEntry();





        mDatabase.child(path).setValue(matchEntry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.d(TAG, "execute: Appointment: " + matchEntry.getAppointmentsList());
                final List<Long> appointmentsList = matchEntry.getAppointmentsList();
                if (databaseError == null) {
                    notifySuccess(appointmentsList);
                } else {
                    notifyError(databaseError.getMessage());
                }
            }
        });

    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onAppointmentFailure(errorMessage);
            }
        });
    }

    private void notifySuccess(final List<Long> appointmentsList) {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onAppointmentSuccess(appointmentsList);
            }
        });
    }
}
