package com.flatshare.domain.interactors.profile.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.profile.QRScannerInteractor;
import com.flatshare.presentation.presenters.profile.QRScannerPresenter;
import com.flatshare.presentation.presenters.profile.impl.QRScannerPresenterImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Arber on 10/01/2017.
 */
public class QRScannerInteractorImpl extends AbstractInteractor implements QRScannerInteractor {

    private static final String TAG = "QRScannerInt";

    private QRScannerInteractor.Callback mCallback;
    private String roommateId;

    public QRScannerInteractorImpl(MainThread mMainThread, Callback callback, String roommateId) {
        super(mMainThread);
        this.mCallback = callback;
        this.roommateId = roommateId;
    }


    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFailure(errorMessage);
            }
        });
    }


    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     * @param roommateId
     */
    private void notifySuccess(final String roommateId) {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSuccess(roommateId);
            }
        });
    }

    @Override
    public void execute() {
        String path = databaseRoot.getRoommateProfileNode(this.roommateId).getAvailable();

        mDatabase.child(path).setValue(false, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    notifySuccess(roommateId);
                } else {
                    notifyError(databaseError.getMessage());
                }
            }
        });

    }
}
