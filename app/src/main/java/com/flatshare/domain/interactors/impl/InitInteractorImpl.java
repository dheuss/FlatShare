package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.InitInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Arber on 28/12/2016.
 */
public class InitInteractorImpl extends AbstractInteractor implements InitInteractor {

    private static final String TAG = "InitInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private Callback mCallback;

    private PrimaryUserProfile primaryUserProfile;
    private TenantProfile tenantProfile;
    private ApartmentProfile apartmentProfile;

    public InitInteractorImpl(
            MainThread mainThread,
            Callback callback) {

        super(mainThread);
        this.mCallback = callback;
    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onReceivedFailure(errorMessage);
            }
        });
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onReceivedSuccess(primaryUserProfile, tenantProfile, apartmentProfile);
            }
        });
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void execute() {

        String path = databaseRoot.getUsers() + userId;

        mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                primaryUserProfile = dataSnapshot.getValue(PrimaryUserProfile.class);

                if (primaryUserProfile == null) {
                    notifyError("No PrimaryProfile created!");
                } else {
                    getSecondaryUserProfiles(primaryUserProfile.getTenantProfileId(), primaryUserProfile.getRoommateProfileId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

    private void getSecondaryUserProfiles(String tenantProfileId, final String apartmentProfileId) {

        if (tenantProfileId != null && tenantProfileId != "") {
            String path = databaseRoot.getTenantProfileNode(tenantProfileId).getRootPath();

            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tenantProfile = dataSnapshot.getValue(TenantProfile.class);
                    if (tenantProfile == null) { // nothing found
                        getApartmentProfile(apartmentProfileId, false);
                    } else {
                        getApartmentProfile(apartmentProfileId, true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyError(databaseError.getMessage());
                }
            });
        } else {
            getApartmentProfile(apartmentProfileId, false);
        }
    }

    private void getApartmentProfile(String apartmentProfileId, final boolean tenantFound) {

        if (apartmentProfileId != null && apartmentProfileId != "") {
            String path = databaseRoot.getApartmentProfileNode(apartmentProfileId).getRootPath();

            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    apartmentProfile = dataSnapshot.getValue(ApartmentProfile.class);
                    if (apartmentProfile == null) {
                        if (tenantFound) {
                            notifySuccess();
                            return;
                        } else {
                            notifyError("Neither tenantId, nor apartmentId found!");
                            return;
                        }
                    }
                    notifySuccess();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyError(databaseError.getMessage());
                }
            });
        } else {
            if (tenantFound) {
                notifySuccess();
            } else {
                notifyError("Neither tenantId, nor apartmentId found!");
            }
        }
    }
}
