package com.flatshare.domain.interactors.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.common.PotentialMatchEntry;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.enums.DecisionType;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.SwipeInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Arber on 14/01/2017.
 */
public class ApartmentSwipeInteractorImpl extends AbstractInteractor implements SwipeInteractor {


    private static final String TAG = "ApartmentSwipeInt";
    private boolean accepted;
    private final String tenantProfileId;
    private final String roommateProfileId;
    private final String apartmentProfileId;
    private final int totalNrRoommates;
    private final boolean isOwner;

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private SwipeInteractor.Callback mCallback;

    public ApartmentSwipeInteractorImpl(MainThread mMainThread, Callback callback, String tenantProfileId, RoommateProfile roommateProfile, int totalNrRoommates, boolean accepted) {
        super(mMainThread);
        this.tenantProfileId = tenantProfileId;
        this.roommateProfileId = roommateProfile.getId();
        this.apartmentProfileId = roommateProfile.getApartmentId();
        this.totalNrRoommates = totalNrRoommates;
        this.isOwner = roommateProfile.isOwner();
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
                    updatePotentialMatchEntry(potentialMatchEntry, decision);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }

    private void createPotentialMatchEntry(String potentialMatchesPath, DecisionType decision) {
        PotentialMatchEntry potentialMatchEntry = new PotentialMatchEntry();
        if (decision == DecisionType.YES) {
            potentialMatchEntry.setNrRoommatesYes(1);
        } else {
            potentialMatchEntry.setNrRoommatesNo(1);
        }

        potentialMatchEntry.setTotalNrRoommates(totalNrRoommates);

        if (isOwner) {
            potentialMatchEntry.setOwnerDecision(decision.getValue());
        }

        mDatabase.child(potentialMatchesPath).setValue(potentialMatchEntry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) { // success
                    removeFromApartmentsToShow();
                } else { // error
                    notifyError(databaseError.getMessage());
                }
            }
        });
    }

    private void updatePotentialMatchEntry(PotentialMatchEntry potentialMatchEntry, DecisionType decision) {

        if (decision == DecisionType.YES) {
            potentialMatchEntry.setNrRoommatesYes(potentialMatchEntry.getNrRoommatesYes() + 1);
        } else {
            potentialMatchEntry.setNrRoommatesNo(potentialMatchEntry.getNrRoommatesNo() + 1);
        }

        if (isOwner) {
            potentialMatchEntry.setOwnerDecision(decision.getValue());
        }

        String potentialMatchesPath = databaseRoot.getPotentialMatchesNode(tenantProfileId, apartmentProfileId).getRootPath();

        mDatabase.child(potentialMatchesPath).setValue(potentialMatchEntry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) { // success
                    removeFromApartmentsToShow();
                } else { // error
                    notifyError(databaseError.getMessage());
                }
            }
        });
    }

    private void removeFromApartmentsToShow() {

        String tenantsToShowPath = databaseRoot.getRoommateProfileNode(roommateProfileId).getTenantsToShow();

        mDatabase.child(tenantsToShowPath).orderByValue().equalTo(tenantProfileId).getRef().removeValue(new DatabaseReference.CompletionListener() {
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
