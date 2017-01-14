package com.flatshare.domain.interactors.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.PotentialMatchEntry;
import com.flatshare.domain.datatypes.enums.DecisionType;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.SwipeInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Arber on 07/01/2017.
 */

public class TenantSwipeInteractorImpl extends AbstractInteractor implements SwipeInteractor {


    private static final String TAG = "TenantSwipeInt";
    private boolean accepted;
    private final String tenantProfileId;
    private final String apartmentProfileId;

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private SwipeInteractor.Callback mCallback;

    public TenantSwipeInteractorImpl(MainThread mMainThread, Callback callback, String tenantProfileId, String apartmentProfileId, boolean accepted) {
        super(mMainThread);
        this.mCallback = callback;
        this.tenantProfileId = tenantProfileId;
        this.apartmentProfileId = apartmentProfileId;
        this.accepted = accepted;
    }


    private void notifyError(String errorMessage) {
        //TODO: notify view somehow?
    }

    private void notifySuccess() {
        // TODO ??
    }

    @Override
    public void execute() {

        String potentialMatchesPath = databaseRoot.getPotentialMatchesNode(tenantProfileId, apartmentProfileId).getRootPath();

        if (accepted) {
            updatePotentialMatch(potentialMatchesPath, DecisionType.YES);
        } else {
            updatePotentialMatch(potentialMatchesPath, DecisionType.NO);
        }

    }

    private void updatePotentialMatch(final String potentialMatchesPath, final DecisionType decision) {

        mDatabase.child(potentialMatchesPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PotentialMatchEntry potentialMatchEntry = dataSnapshot.getValue(PotentialMatchEntry.class);
                if (potentialMatchEntry == null) { // if doesn't exist => create it
                    createPotentialMatchEntry(potentialMatchesPath, decision);
                } else {
                    updatePotentialMatchEntry(decision);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                TenantSwipeInteractorImpl.this.notifyError(databaseError.getMessage());
            }
        });
    }

    private void createPotentialMatchEntry(String potentialMatchesPath, DecisionType decision) {
        PotentialMatchEntry potentialMatchEntry = new PotentialMatchEntry();
        potentialMatchEntry.setTenantDecision(decision.getValue());

        mDatabase.child(potentialMatchesPath).setValue(potentialMatchEntry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) { // success
                    removeFromApartmentsToShow();
                } else { // error
                    TenantSwipeInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });
    }

    private void updatePotentialMatchEntry(DecisionType decision) {

        String tenantDecisionPath = databaseRoot.getPotentialMatchesNode(tenantProfileId, apartmentProfileId).getTenantDecision();

        mDatabase.child(tenantDecisionPath).setValue(decision.getValue(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) { // success
                    removeFromApartmentsToShow();
                } else { // error
                    TenantSwipeInteractorImpl.this.notifyError(databaseError.getMessage());
                }
            }
        });
    }

    private void removeFromApartmentsToShow() {

        String apartmentsToShowPath = databaseRoot.getTenantProfileNode(tenantProfileId).getApartmentsToShow();

        mDatabase.child(apartmentsToShowPath).orderByValue().equalTo(apartmentProfileId).getRef().removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) { // success
                    notifySuccess();
                } else { // error
                    notifyError(databaseError.getMessage());
                }
            }
        });
    }


}
