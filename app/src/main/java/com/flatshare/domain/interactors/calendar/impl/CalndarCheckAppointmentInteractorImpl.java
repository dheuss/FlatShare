package com.flatshare.domain.interactors.calendar.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.calendar.CalendarCheckAppointmentInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sandro on 30.01.17.
 */

public class CalndarCheckAppointmentInteractorImpl extends AbstractInteractor implements CalendarCheckAppointmentInteractor {

    private static final String TAG = "CalndarCheckAppointmentInteractorImpl";

    private final String tenantID;
    private final String apartmentID;

    private Callback mCallback;

    public CalndarCheckAppointmentInteractorImpl(MainThread mMainThread, CalendarCheckAppointmentInteractor.Callback callback, String tenantId, String apartmentId) {
        super(mMainThread);
        this.tenantID = tenantId;
        this.apartmentID = apartmentId;
        this.mCallback = callback;
    }

    @Override
    public void execute() {

        String path = databaseRoot.getMatchesNode(tenantID, apartmentID).getRootPath();

        final MatchEntry matchEntry = new MatchEntry();

        long appointment = matchEntry.getAppointment();

        Log.d(TAG, "execute: Appointment: " + appointment);

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

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onAppointmentFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onAppointmentSuccess();
            }
        });
    }
}
