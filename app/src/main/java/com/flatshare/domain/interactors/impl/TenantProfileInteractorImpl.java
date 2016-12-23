package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.ProfileInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.network.DatabaseTree;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * */
public class TenantProfileInteractorImpl extends AbstractInteractor implements ProfileInteractor {

    private static final String TAG = "TenantProfileInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private ProfileInteractor.Callback mCallback;

    private TenantUserProfile tenantUserProfile;

    public TenantProfileInteractorImpl(
            MainThread mainThread,
            Callback callback, TenantUserProfile tenantUserProfile) {

        super(mainThread);
        this.mCallback = callback;
        this.tenantUserProfile = tenantUserProfile;
    }

    private void notifyError(String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onSentFailure(errorMessage));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onSentSuccess(0));
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void execute() {

        String tPath = DatabaseTree.TENANT_PROFILES_PATH;
        String usersPath = DatabaseTree.USERS_PATH;

        String tId = mDatabase.child(tPath).push().getKey();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(tPath + tId, this.tenantUserProfile);
        map.put(usersPath + DatabaseTree.USER_ID, tId);

        mDatabase.updateChildren(map, (databaseError, databaseReference) -> {
            if (databaseError != null) { // Error
                notifyError(databaseError.toException().getMessage());
            } else {
                notifySuccess();
            }
        });

    }
}
