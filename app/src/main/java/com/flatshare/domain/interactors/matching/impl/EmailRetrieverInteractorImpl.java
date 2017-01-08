package com.flatshare.domain.interactors.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.EmailRetrieverInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Arber on 06/01/2017.
 */

public class EmailRetrieverInteractorImpl extends AbstractInteractor implements EmailRetrieverInteractor {

    private static final String TAG = "EmailRetrieverInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private EmailRetrieverInteractor.Callback mCallback;

    public EmailRetrieverInteractorImpl(MainThread mainThread,
                                        Callback callback) {

        super(mainThread);
        this.mCallback = callback;
    }


    private void notifyError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.emailsRetrievedFailure(errorMessage);
            }
        });
    }

    private void notifySuccess(final Map<String, String> emailIdMap) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.emailsRetrievedSuccess(emailIdMap);
            }
        });
    }

    @Override
    public void execute() {
        String path = databaseRoot.getUsers();

        mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, PrimaryUserProfile>> profilesMap = new GenericTypeIndicator<Map<String, PrimaryUserProfile>>() {
                };

                Map<String, PrimaryUserProfile> idProfileMap = dataSnapshot.getValue(profilesMap);
                idProfileMap.remove(userId);
                Map<String, String> emailIdMap = new HashMap<>();

                for (Map.Entry<String, PrimaryUserProfile> entry : idProfileMap.entrySet()) {
                    String id = entry.getKey();
                    String email = entry.getValue().getEmail();
                    emailIdMap.put(email, id);
                }

                if (emailIdMap.size() == 0) {
                    notifyError("No emails found!");
                } else {
                    notifySuccess(emailIdMap);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

}
