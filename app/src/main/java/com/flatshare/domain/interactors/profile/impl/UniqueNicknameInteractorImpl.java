package com.flatshare.domain.interactors.profile.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.profile.UniqueNicknameInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arber on 16/01/2017.
 */

public class UniqueNicknameInteractorImpl extends AbstractInteractor implements UniqueNicknameInteractor {

    private static final String TAG = "UniqueNicknameInt";
    private Callback mCallback;
    private String nickname;

    public UniqueNicknameInteractorImpl(MainThread mainThread, Callback callback, String nickname) {
        super(mainThread);
        this.mCallback = callback;
        this.nickname = nickname;
    }

    private void notifyError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRequestFailure(errorMessage);
            }
        });
    }

    private void notifyNicknameRequest(final boolean unique) {

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.nicknameResult(unique);
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

                if(idProfileMap.isEmpty()){
                    notifyNicknameRequest(true);
                    return;
                }

                for (Map.Entry<String, RoommateProfile> entry : idProfileMap.entrySet()) {
                    if(entry.getValue().getNickname() != null) {
                        if (entry.getValue().getNickname().equals(nickname)) {
                            notifyNicknameRequest(false);
                            return;
                        }
                    }
                }
                notifyNicknameRequest(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }
}
