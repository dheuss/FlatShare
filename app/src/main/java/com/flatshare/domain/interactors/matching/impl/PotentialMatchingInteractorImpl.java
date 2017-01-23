package com.flatshare.domain.interactors.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.matching.PotentialMatchingInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.predicates.ApartmentMatchFinder;
import com.flatshare.domain.predicates.TenantMatchFinder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Arber on 20/12/2016.
 */
public class PotentialMatchingInteractorImpl extends AbstractInteractor implements PotentialMatchingInteractor {

    private static final String TAG = "MatchingInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private PotentialMatchingInteractor.Callback mCallback;

    private int classificationId;
    private TenantProfile tenantProfile;
    private ApartmentProfile apartmentProfile;

    public PotentialMatchingInteractorImpl(MainThread mainThread,
                                           Callback callback, int classificationId, TenantProfile tenantProfile, ApartmentProfile apartmentProfile) {

        super(mainThread);
        this.mCallback = callback;
        this.classificationId = classificationId;
        this.tenantProfile = tenantProfile;
        this.apartmentProfile = apartmentProfile;
    }

    private void notifyTenantMatchesFound(final List<TenantProfile> tenants) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTenantsFound(tenants);
            }
        });
    }

    private void notifyApartmentMatchesFound(final List<ApartmentProfile> apartments) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onApartmentsFound(apartments);
            }
        });
    }


    private void notifyNoMatchFound() {

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onNoMatchFound();
            }
        });
    }


    private void notifyError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.notifyError(errorMessage);
            }
        });
    }

    @Override
    public void execute() {

        if (tenantProfile != null || apartmentProfile != null) {

            if (classificationId == 0) { // tenant is looking for matches
                matchTenant(tenantProfile);
            } else { // apartment is looking for matches
                matchApartment(apartmentProfile);
            }
        } else {

            mDatabase.child(databaseRoot.getUserProfileNode(userId).getRootPath()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        notifyError("No PrimaryProfile found!");
                    } else {
                        PrimaryUserProfile pUP = dataSnapshot.getValue(PrimaryUserProfile.class);
                        int cId = pUP.getClassificationId();
                        if (cId == 0) {
                            getTenant(pUP.getTenantProfileId());
                        } else {
                            getApartment(pUP.getRoommateProfileId());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyError(databaseError.getMessage());
                }
            });
        }

    }

    private void matchApartment(final ApartmentProfile apUP) {

        //String path = databaseRoot.getTenantProfiles();
        String testPath = "test/" + databaseRoot.getTenantProfiles();

        mDatabase.child(testPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                GenericTypeIndicator<Map<String, TenantProfile>> t = new GenericTypeIndicator<Map<String, TenantProfile>>() {
                };

                if (dataSnapshot.getValue(t) == null) {
                    notifyError("No apartments were found in the database!");
                    return;
                }

                List<TenantProfile> tenants = new ArrayList<>((dataSnapshot.getValue(t).values()));

                List<TenantProfile> potentialMatches = new ApartmentMatchFinder(apUP, tenants).getMatches();

                if (potentialMatches.size() == 0) {
                    notifyNoMatchFound();
                } else {
                    notifyTenantMatchesFound(potentialMatches);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

    private void matchTenant(final TenantProfile tUP) {

        //String path = databaseRoot.getApartmentProfiles();
        String testPath = "test/" + databaseRoot.getApartmentProfiles();

        mDatabase.child(testPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, ApartmentProfile>> t = new GenericTypeIndicator<Map<String, ApartmentProfile>>() {
                };

                if (dataSnapshot.getValue(t) == null) {
                    notifyError("No apartments were found in the database!");
                    return;
                }

                List<ApartmentProfile> apartments = new ArrayList<>((dataSnapshot.getValue(t).values()));

                List<ApartmentProfile> potentialMatches = new TenantMatchFinder(tUP, apartments).getMatches();

                if (potentialMatches.size() == 0) {
                    notifyNoMatchFound();
                } else {
                    notifyApartmentMatchesFound(potentialMatches);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

    private void getApartment(String apartmentProfileId) {
        String apPath = databaseRoot.getApartmentProfiles();

        mDatabase.child(apPath + apartmentProfileId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    notifyError("No ApartmentProfile found!");
                } else {
                    ApartmentProfile apUP = dataSnapshot.getValue(ApartmentProfile.class);
                    matchApartment(apUP);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }

    private void getTenant(String tenantProfileId) {

        mDatabase.child(databaseRoot.getTenantProfileNode(tenantProfileId).getRootPath()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    notifyError("No ApartmentProfile found!");
                } else {
                    TenantProfile tUP = dataSnapshot.getValue(TenantProfile.class);
                    matchTenant(tUP);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });
    }

}
