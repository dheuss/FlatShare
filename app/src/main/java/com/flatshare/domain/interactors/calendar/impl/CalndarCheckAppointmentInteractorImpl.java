package com.flatshare.domain.interactors.calendar.impl;

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

        final List<Long> appointmentList = matchEntry.getAppointmentsList();

        mDatabase.child(path).setValue(matchEntry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    notifySuccess(appointmentList);
                }else{
                    notifyError(databaseError.getMessage());
                }
            }
        });

    }

    private void notifyError(String errorMessage) {

    }

    private void notifySuccess(List<Long> appointmentList) {

    }
}
