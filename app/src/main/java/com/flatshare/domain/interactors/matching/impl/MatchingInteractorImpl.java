package com.flatshare.domain.interactors.matching.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.interactors.matching.MatchingInteractor;
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
public class MatchingInteractorImpl extends AbstractInteractor implements MatchingInteractor {

    private static final String TAG = "MatchingInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private MatchingInteractor.Callback mCallback;

    private int classificationId;
    private TenantUserProfile tenantUserProfile;
    private ApartmentUserProfile apartmentUserProfile;

    public MatchingInteractorImpl(MainThread mainThread,
                                  Callback callback, int classificationId, TenantUserProfile tenantUserProfile, ApartmentUserProfile apartmentUserProfile) {

        super(mainThread);
        this.mCallback = callback;
        this.classificationId = classificationId;
        this.tenantUserProfile = tenantUserProfile;
        this.apartmentUserProfile = apartmentUserProfile;
    }

    private void notifyTenantMatchesFound(final List<TenantUserProfile> tenants) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTenantsFound(tenants);
            }
        });
    }

    private void notifyApartmentMatchesFound(final List<ApartmentUserProfile> apartments) {
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


        if (tenantUserProfile != null || apartmentUserProfile != null) {

            if (classificationId == 0) { // tenant is looking for matches
                matchTenant(tenantUserProfile);
            } else { // apartment is looking for matches
                matchApartment(apartmentUserProfile);
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
                            getApartment(pUP.getApartmentProfileId());
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

    private void matchApartment(final ApartmentUserProfile apUP) {

        String path = databaseRoot.getTenantProfiles();
        String testPath = "test/" + databaseRoot.getTenantProfiles();

        mDatabase.child(testPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, TenantUserProfile>> t = new GenericTypeIndicator<Map<String, TenantUserProfile>>() {
                };

                List<TenantUserProfile> tenants = new ArrayList<>(dataSnapshot.getValue(t).values());

                if (tenants == null) {
                    notifyError("No tenants were found in the database!");
                    return;
                }

                List<TenantUserProfile> potentialMatches = new ApartmentMatchFinder(apUP, tenants).getMatches();

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

    private void matchTenant(final TenantUserProfile tUP) {

        String path = databaseRoot.getApartmentProfiles();
        String testPath = "test/" + databaseRoot.getApartmentProfiles();

        mDatabase.child(testPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, ApartmentUserProfile>> t = new GenericTypeIndicator<Map<String, ApartmentUserProfile>>() {
                };

                List<ApartmentUserProfile> apartments = new ArrayList<>((dataSnapshot.getValue(t).values()));

                if (apartments == null) {
                    notifyError("No apartments were found in the database!");
                    return;
                }

                List<ApartmentUserProfile> potentialMatches = new TenantMatchFinder(tUP, apartments).getMatches();

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
                    ApartmentUserProfile apUP = dataSnapshot.getValue(ApartmentUserProfile.class);
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
                    TenantUserProfile tUP = dataSnapshot.getValue(TenantUserProfile.class);
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
