package com.flatshare.domain.interactors.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.SwipeInteractor;

/**
 * Created by Arber on 07/01/2017.
 */

public class SwipeInteractorImpl extends AbstractInteractor implements SwipeInteractor {


    private static final String TAG = "SwipeInt";
    private boolean accept;
    private String thisId;
    private int otherType;
    private String otherId;

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private SwipeInteractor.Callback mCallback;

    public SwipeInteractorImpl(MainThread mainThread,
                               Callback callback, String thisId, UserProfile profile, boolean accept) {

        super(mainThread);
        this.mCallback = callback;
        this.thisId = thisId;
        this.otherType = profile.getType();
        this.otherId = profile.getId();
        this.accept = accept;
    }


    private void notifyError(String errorMessage) {
        //TODO: notify view somehow?
    }

    private void notifySuccess() {
        // TODO ??
    }

    @Override
    public void execute() {

        String potentialMatchesPath = null;
        String profilesToShowPath = null;

        if (accept) {
            if (otherType == 0) { // its a tenant
                potentialMatchesPath = databaseRoot.getPotentialMatchesNode(otherId, thisId).getTenantYes();
                profilesToShowPath = databaseRoot.getApartmentProfileNode(thisId).getTenantsToShow();
            } else if (otherType == 1) { // its an apartment
                potentialMatchesPath = databaseRoot.getPotentialMatchesNode(thisId, otherId).getApartmentYes();
                profilesToShowPath = databaseRoot.getTenantProfileNode(thisId).getApartmentsToShow();
            } else { // its something unexpected..
                notifyError("Wrong profile type: " + otherType);
            }

            insertIntoPotentialMatches(potentialMatchesPath);
        }

        removeProfileToShow(profilesToShowPath);

    }

    private void insertIntoPotentialMatches(String potentialMatchesPath) {
        mDatabase.child(potentialMatchesPath).setValue(true, (databaseError, databaseReference) -> {
            if (databaseError == null) { // success
                notifySuccess();
            } else { // error
                notifyError(databaseError.getMessage());
            }
        });
    }


    private void removeProfileToShow(String profilesToShowPath) {
        mDatabase.child(profilesToShowPath).orderByValue().equalTo(otherId).getRef().removeValue((databaseError, databaseReference) -> {
            if (databaseError == null) { // success
                notifySuccess();
            } else { // error
                notifyError(databaseError.getMessage());
            }
        });
    }

}
