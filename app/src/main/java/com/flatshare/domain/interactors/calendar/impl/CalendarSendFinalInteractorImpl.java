package com.flatshare.domain.interactors.calendar.impl;

import android.icu.text.DateFormat;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.calendar.CalendarSendFinalInteractor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Sandro on 16.01.17.
 */
public class CalendarSendFinalInteractorImpl extends AbstractInteractor implements CalendarSendFinalInteractor {

    private static final String TAG = "CalendarInteractorImpl";
    private final String finalDate;
    private final String tenantID;
    private final String apartmentID;


    private CalendarSendFinalInteractor.Callback mCallback;

    public CalendarSendFinalInteractorImpl(MainThread mMainThread, Callback callback, String finalDate, String tenantID, String apartmentID) {
        super(mMainThread);
        this.mCallback = callback;
        this.finalDate = finalDate;
        this.tenantID = tenantID;
        this.apartmentID= apartmentID;

    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentFinalFailure(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSentFinalSuccess();
            }
        });
    }

    @Override
    public void execute() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        long appointment = 0;
        try {
            Date date = df.parse(finalDate);
            appointment = date.getTime();
        } catch (ParseException e) {
            Log.d(TAG, "execute: ", e);
        }

        String path = databaseRoot.getMatchesNode(tenantID, apartmentID).getRootPath();

        final MatchEntry matchEntry = new MatchEntry();

        matchEntry.setAppointment(appointment);

        mDatabase.child(path).setValue(matchEntry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    notifySuccess();
                }else{
                    notifyError(databaseError.getMessage());
                }
            }
        });

    }
}
