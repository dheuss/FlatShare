package com.flatshare.domain.interactors.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.datatypes.db.common.PotentialMatchEntry;
import com.flatshare.domain.datatypes.enums.DecisionType;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.PMatchesListenerInteractor;
import com.flatshare.presentation.presenters.matching.impl.PotentialMatchingPresenterImpl;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arber on 14/01/2017.
 */

public class PMatchesListenerInteractorImpl extends AbstractInteractor implements PMatchesListenerInteractor {

    private static final String TAG = "PMatchesListenerInt";

    private PMatchesListenerInteractor.Callback mCallback;
    private PotentialMatchEntry potentialMatchEntry = null;

    public PMatchesListenerInteractorImpl(MainThread mainThread,
                                          Callback callback) {

        super(mainThread);
        this.mCallback = callback;
    }


    private void notifyError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFailure(errorMessage);
            }
        });
    }

    private void notifyMatchCreated(final String key) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onMatchCreated(key);
            }
        });
    }

    @Override
    public void execute() {
        String potentialMatchesPath = databaseRoot.getPotentialMatches();
//        for (int i = 0; i < 100; i++)
//        mDatabase.child(potentialMatchesPath).removeEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        mDatabase.child(potentialMatchesPath).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                checkIfMatchPossible(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkIfMatchPossible(DataSnapshot dataSnapshot) {
        potentialMatchEntry = dataSnapshot.getValue(PotentialMatchEntry.class);

        String key = dataSnapshot.getKey();

        if (potentialMatchEntry == null) { // shouldn't happen but who knows..
            notifyError("There is no entry with key: " + key);
        } else {
            if (decisionCanBeMade()) {
                Log.d(TAG, "checkIfMatchPossible: Decision can be made");
                if (tenantAccepts() && majorityAccepts() && ownerAccepts()) { // match found
                    Log.d(TAG, "checkIfMatchPossible: Match FOUND");
                    createMatch(key);
                    deleteMatch(key);
                } else {// they seem to not like each other
                    Log.d(TAG, "checkIfMatchPossible: DELETE MATCH!");
                    deleteMatch(key);
                }
            }
        }
    }

    private void createMatch(final String key) {

        String path = databaseRoot.getMatches() + key;

        mDatabase.child(path).setValue(new MatchEntry(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) { // success
                    updateUsers(key);
                } else {
                    notifyError("Error on creating match: " + key);
                }
            }
        });

    }

    private void updateUsers(final String key) {
        String ids[] = key.split(":");
        String tenantPath = databaseRoot.getTenantProfileNode(ids[0]).getMatchedApartments();
        String apartmentPath = databaseRoot.getApartmentProfileNode(ids[1]).getMatchedTenants();

        String newKey = mDatabase.child(databaseRoot.getPotentialMatches() + key).push().getKey();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(tenantPath + newKey, ids[1]);
        childUpdates.put(apartmentPath + newKey, ids[0]);

        mDatabase.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    notifyMatchCreated(key);
                } else {
                    notifyError("Match could not be created: " + databaseError.getMessage());
                }
            }
        });

    }

    private void deleteMatch(String key) {

        String path = databaseRoot.getPotentialMatches() + key;

        mDatabase.child(path).removeValue();
    }

    private boolean tenantAccepts() {
        return potentialMatchEntry.getTenantDecision() == DecisionType.YES.getValue();
    }

    private boolean majorityAccepts() {
        int totalYes = potentialMatchEntry.getNrRoommatesYes();
        int totalRoommates = potentialMatchEntry.getTotalNrRoommates();
        int threshold = (totalRoommates + 1) / 2;
        return totalYes >= threshold;
    }

    private boolean ownerAccepts() {
        return potentialMatchEntry.getOwnerDecision() == DecisionType.YES.getValue();
    }

    private boolean decisionCanBeMade() {
        int ownerDecision = potentialMatchEntry.getOwnerDecision();
        int totalRoommates = potentialMatchEntry.getTotalNrRoommates();
        int totalYes = potentialMatchEntry.getNrRoommatesYes();
        int totalNo = potentialMatchEntry.getNrRoommatesNo();
        int tenantDecision = potentialMatchEntry.getTenantDecision();
        int threshold = (totalRoommates + 1) / 2;

        boolean majorityAnswered = totalYes >= threshold || totalNo >= threshold;
        boolean tenantAnswered = tenantDecision != DecisionType.PENDING.getValue();
        boolean ownerAnswered = ownerDecision != DecisionType.PENDING.getValue();

        return tenantAnswered && majorityAnswered && ownerAnswered;
    }

}
