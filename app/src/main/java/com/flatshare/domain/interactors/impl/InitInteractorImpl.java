package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
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
    private RoommateProfile roommateProfile;
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
    private void notifyTenantFound() {

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTenantFound(primaryUserProfile, tenantProfile);
            }
        });
    }

    private void notifyRoommateFound() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRoommateFound(roommateProfile, apartmentProfile);
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

                // get primary profile
                primaryUserProfile = dataSnapshot.getValue(PrimaryUserProfile.class);

                if (primaryUserProfile == null) {
                    notifyError("No PrimaryProfile created!");
                } else { // if something found, get secondary profiles (either tenant or roommate)
                    getSecondaryUserProfiles(primaryUserProfile.getTenantProfileId(), primaryUserProfile.getRoommateProfileId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

    private void getSecondaryUserProfiles(String tenantProfileId, final String roommateProfileId) {

        if (tenantProfileId != null && tenantProfileId != "") { // if no tenant ID

            String path = databaseRoot.getTenantProfileNode(tenantProfileId).getRootPath();

            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    tenantProfile = dataSnapshot.getValue(TenantProfile.class);

                    if (tenantProfile == null) { // nothing found
                        getRoommateProfile(roommateProfileId, false);
                    } else {
                        getRoommateProfile(roommateProfileId, true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyError(databaseError.getMessage());
                }
            });
        } else {
            getRoommateProfile(roommateProfileId, false);
        }
    }

    private void getRoommateProfile(String roommateProfileId, final boolean tenantFound) {

        if (roommateProfileId != null && roommateProfileId != "") {
            String path = databaseRoot.getRoommateProfileNode(roommateProfileId).getRootPath();

            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    roommateProfile = dataSnapshot.getValue(RoommateProfile.class);
                    if (roommateProfile == null) {
                        if (tenantFound) {
                            notifyTenantFound();
                        } else {
                            notifyError("Neither tenantId, nor roommateId found!");
                        }
                    } else {
                        String apartmentProfileId = roommateProfile.getApartmentId();
                        getApartmentProfile(apartmentProfileId);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyError(databaseError.getMessage());
                }
            });
        } else { // only tenant could be found (no apartment)
            if (tenantFound) { // if tenant found => call callback
                notifyTenantFound();
            } else { // if neither tenant nor roommate found (there cannot be any apartment
                notifyError("Neither tenantId, nor roommateId found!");
            }
        }
    }

    private void getApartmentProfile(String apartmentProfileId) {

        if (apartmentProfileId != null && apartmentProfileId != "") {
            String path = databaseRoot.getApartmentProfileNode(apartmentProfileId).getRootPath();

            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    apartmentProfile = dataSnapshot.getValue(ApartmentProfile.class);

                    notifyRoommateFound();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyError(databaseError.getMessage());
                }
            });
        } else { // apartmentId does not exist => roommate without apartment

            notifyRoommateFound();

        }
    }
}
