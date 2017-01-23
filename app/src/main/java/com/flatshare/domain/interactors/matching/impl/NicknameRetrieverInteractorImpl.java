package com.flatshare.domain.interactors.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.NicknameRetrieverInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Arber on 06/01/2017.
 */

public class NicknameRetrieverInteractorImpl extends AbstractInteractor implements NicknameRetrieverInteractor {

    private static final String TAG = "NicknameRetrieverInt";
    private String roommateId;

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private NicknameRetrieverInteractor.Callback mCallback;

    public NicknameRetrieverInteractorImpl(MainThread mainThread,
                                           Callback callback, String roommateId) {

        super(mainThread);
        this.mCallback = callback;
        this.roommateId = roommateId;
    }


    private void notifyError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.nicknamesRetrievedFailure(errorMessage);
            }
        });
    }

    private void notifySuccess(final Map<String, String> nicknameIdMap) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.nicknamesRetrievedSuccess(nicknameIdMap);
            }
        });
    }

    @Override
    public void execute() {
        String path = databaseRoot.getRoommateProfiles();

        mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, RoommateProfile>> profilesMap = new GenericTypeIndicator<Map<String, RoommateProfile>>() {
                };

                Map<String, RoommateProfile> idProfileMap = dataSnapshot.getValue(profilesMap);
                idProfileMap.remove(roommateId);
                Map<String, String> nicknameIdMap = new HashMap<>();

                for (Map.Entry<String, RoommateProfile> entry : idProfileMap.entrySet()) {

                    if (entry.getValue().isAvailable()) {
                        String id = entry.getKey();
                        String nickname = entry.getValue().getNickname();

                        if(id != null && nickname != null && !id.equals("") && !nickname.equals("")) {
                            nicknameIdMap.put(nickname, id);
                        }
                    }
                }

                if (nicknameIdMap.size() == 0) {
                    notifyError("No nicknames found!");
                } else {
                    notifySuccess(nicknameIdMap);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

}
