package com.flatshare.domain.interactors.calendar.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.calendar.CalendarCheckAppointmentInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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

        mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MatchEntry matchEntry = dataSnapshot.getValue(MatchEntry.class);

                if (matchEntry == null) {
                    Log.d(TAG, "onDataChange: Match Entry Appointment is null");
                    notifyError("");
                    return;
                }

                final List<Long> appointmentsList = matchEntry.getAppointmentsList();

                if (appointmentsList == null) {
                    Log.d(TAG, "onDataChange: AppointmentList is null");
                    notifyError("You have no Appointment right now.");
                    return;
                } else {
                    notifySuccess(appointmentsList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
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
