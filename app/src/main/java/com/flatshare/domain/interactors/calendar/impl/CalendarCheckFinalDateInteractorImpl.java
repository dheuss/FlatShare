package com.flatshare.domain.interactors.calendar.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.calendar.CalendarCheckFinalDateInteractor;
import com.flatshare.domain.interactors.calendar.CalendarSendFinalInteractor;
import com.flatshare.presentation.presenters.matchingoverview.calendar.impl.CalendarPresenterImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sandro on 01.02.17.
 */

public class CalendarCheckFinalDateInteractorImpl extends AbstractInteractor implements CalendarCheckFinalDateInteractor {

    private static final String TAG = "CalCheckFinalDateInt";
    private Callback mCallback;
    private String tenantId;
    private String apartmentId;

    public CalendarCheckFinalDateInteractorImpl(MainThread mainThread, Callback callback, String tenantId, String apartmentId) {
        super(mainThread);
        this.mCallback = callback;
        this.tenantId = tenantId;
        this.apartmentId = apartmentId;
    }

    @Override
    public void execute() {

        final String path = databaseRoot.getMatchesNode(tenantId, apartmentId).getRootPath();

        Log.d(TAG, "execute: trying to add value event listener");

        mDatabase.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: ");

                if (dataSnapshot.getValue() == null) {
                    Log.d(TAG, "onDataChange: WaitingListener found null as value!");
                } else { // Profile was created
                    MatchEntry matchEntry = dataSnapshot.getValue(MatchEntry.class);

                    if(matchEntry.isAppointmentSet()){
                        removeValueListener(path);
                        notifySuccess(matchEntry.getAppointment());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Finale Date Listener could not be set");
            }
        });

    }

    private void notifySuccess(final long appointment) {
        Log.d(TAG, "notifySuccess: APARTMENT READY!!!");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onAppointmentSet(appointment);
            }
        });
    }

    private void removeValueListener(String path) {
        mDatabase.child(path).removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: read about it...
                Log.d(TAG, "onDataChange: removed listener");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
