package com.flatshare.domain.interactors.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.RoommateQRInteractor;
import com.flatshare.presentation.presenters.matching.impl.RoommateProfilePresenterImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Arber on 08/01/2017.
 */
public class RoommateQRInteractorImpl extends AbstractInteractor implements RoommateQRInteractor {


    private static final String TAG = "RoommateQRInt";

    private RoommateQRInteractor.Callback mCallback;
    private String roommateId;

    public RoommateQRInteractorImpl(MainThread mainThread,
                                  Callback callback, String roommateId) {

        super(mainThread);
        this.mCallback = callback;
        this.roommateId = roommateId;
    }


    @Override
    public void execute() {
        String path = databaseRoot.getRoommateProfileNode(this.roommateId).getApartmentId();
        mDatabase.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    // Do nothing
                } else { // Apartment ID was written
                    notifyCodeRead();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }


    private void notifyError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.notifyError(errorMessage);
            }
        });
    }

    private void notifyCodeRead() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onCodeRead();
            }
        });
    }
}
