package com.flatshare.domain.interactors.impl;

import android.util.Log;

import java.util.List;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.MatchingInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.predicates.ApartmentMatchFinder;
import com.flatshare.domain.predicates.TenantMatchFinder;
import com.flatshare.network.DatabaseTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class MatchingInteractorImpl extends AbstractInteractor implements MatchingInteractor {

    private static final String TAG = "MatchingInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private MatchingInteractor.Callback mCallback;

    public MatchingInteractorImpl(MainThread mainThread,
                                  Callback callback) {

        super(mainThread);
        this.mCallback = callback;
    }

    private void notifyTenantMatchesFound(List<TenantUserProfile> tenants) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(() -> mCallback.onTenantsFound(tenants));
    }

    private void notifyApartmentMatchesFound(List<ApartmentUserProfile> apartments) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(() -> mCallback.onApartmentsFound(apartments));
    }


    private void notifyNoMatchFound() {

        mMainThread.post(() -> mCallback.onNoMatchFound());
    }


    private void notifyError(String errorMessage) {
        mMainThread.post(() -> mCallback.notifyError(errorMessage));
    }

    @Override
    public void execute() {

        String uId = DatabaseTree.USER_ID;
        String uPath = DatabaseTree.USERS_PATH;

        mDatabase.child(uPath + uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {
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

    private void matchApartment(ApartmentUserProfile apUP) {

        String tPath = DatabaseTree.TENANT_PROFILES_PATH;

        mDatabase.child(tPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<List<TenantUserProfile>> t = new GenericTypeIndicator<List<TenantUserProfile>>() {
                };

                List<TenantUserProfile> tenants = dataSnapshot.getValue(t);

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

    private void matchTenant(TenantUserProfile tUP) {

        String aPath = DatabaseTree.APARTMENT_PROFILES_PATH;

        mDatabase.child(aPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<List<ApartmentUserProfile>> t = new GenericTypeIndicator<List<ApartmentUserProfile>>() {
                };

                List<ApartmentUserProfile> apartments = dataSnapshot.getValue(t);

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
        String apPath = DatabaseTree.APARTMENT_PROFILES_PATH;

        mDatabase.child(apPath + apartmentProfileId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {
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
        String tPath = DatabaseTree.TENANT_PROFILES_PATH;

        mDatabase.child(tPath + tenantProfileId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {
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
