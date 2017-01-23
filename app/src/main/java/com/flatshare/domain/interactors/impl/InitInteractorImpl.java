package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.InitInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    private void notifyApartmentFound() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onApartmentFound(primaryUserProfile, roommateProfile, apartmentProfile);
            }
        });
    }

    private void notifyRoommateFound() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRoommateFound(primaryUserProfile, roommateProfile);
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
                    Log.d(TAG, "onDataChange: " + primaryUserProfile.getClassificationId());
                    if (primaryUserProfile.getClassificationId() == ProfileType.TENANT.getValue()) {

                        getTenantUserProfile(primaryUserProfile.getTenantProfileId());
                    } else {
                        getRoommateProfile(primaryUserProfile.getRoommateProfileId());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

    private void getTenantUserProfile(final String tenantProfileId) {

        if (tenantProfileId != null && tenantProfileId != "") { // if tenant ID valid

            String path = databaseRoot.getTenantProfileNode(tenantProfileId).getRootPath();

            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> matchedApartments;
                    try {
                        GenericTypeIndicator<Map<String, String>> t = new GenericTypeIndicator<Map<String, String>>() {
                        };

                        matchedApartments = new ArrayList<>(dataSnapshot.child("matched_apartments").getValue(t).values());
                        tenantProfile.setMatchedApartments(matchedApartments);
                    } catch (NullPointerException npe) {
                        Log.d(TAG, "onDataChange: No matches found!");
                    }
                    tenantProfile = dataSnapshot.getValue(TenantProfile.class);

                    if (tenantProfile == null) { // no settings yet
                        notifyError("tenantProfile with ID: " + tenantProfileId + " does not exist!");
                    } else {
                        notifyTenantFound();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyError(databaseError.getMessage());
                }
            });
        } else { // tenantId was wrong
            notifyError("tenantID: " + tenantProfileId + " is invalid!");
        }
    }

    private void getRoommateProfile(final String roommateProfileId) {

        if (roommateProfileId != null && roommateProfileId != "") { // if roommate ID valid

            String path = databaseRoot.getRoommateProfileNode(roommateProfileId).getRootPath();

            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    roommateProfile = dataSnapshot.getValue(RoommateProfile.class);

                    if (roommateProfile == null) {
                        notifyError("roommateProfile with ID: " + roommateProfileId + " does not exist!");
                    } else {
                        getApartmentProfile(roommateProfile.getApartmentId());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyError(databaseError.getMessage());
                }
            });
        } else { // roommateId was wrong
            notifyError("roommateID: " + roommateProfileId + " is invalid!");
        }
    }

    private void getApartmentProfile(final String apartmentProfileId) {

        if (apartmentProfileId != null && apartmentProfileId != "") {
            String path = databaseRoot.getApartmentProfileNode(apartmentProfileId).getRootPath();

            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List<String> matchedTenants;

                    try {
                        GenericTypeIndicator<Map<String, String>> t = new GenericTypeIndicator<Map<String, String>>() {
                        };
                        matchedTenants = new ArrayList<>(dataSnapshot.child("matched_tenants").getValue(t).values());
                        apartmentProfile.setMatchedTenants(matchedTenants);
                    } catch (NullPointerException npe) {
                        Log.d(TAG, "onDataChange: No matches found!");
                    }

                    apartmentProfile = dataSnapshot.getValue(ApartmentProfile.class);

                    if (apartmentProfile == null) {
                        notifyError("apartmentProfile with ID: " + apartmentProfileId + " does not exist!");
                    } else {
                        notifyApartmentFound();
                    }
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
