package com.flatshare.domain.interactors.matchingoverview.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matchingoverview.MatchesInteractor;
import com.flatshare.domain.interactors.media.impl.UploadInteractorImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchesInteractorImpl extends AbstractInteractor implements MatchesInteractor {

    private static final String TAG = "MatchesInteractorImpl";
    private static final String DELIMITER = ":";
    private final TenantProfile tenantProfile;
    private final ApartmentProfile apartmentProfile;

    private MatchesInteractor.Callback mCallback;

    public MatchesInteractorImpl(MainThread mainThread, Callback callback, TenantProfile tenantProfile, ApartmentProfile apartmentProfile) {
        super(mainThread);

        mCallback = callback;
        this.tenantProfile = tenantProfile;
        this.apartmentProfile = apartmentProfile;
    }

    @Override
    public void execute() {

        String matchesPath = databaseRoot.getMatches();
        mDatabase.child(matchesPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, MatchEntry>> t = new GenericTypeIndicator<Map<String, MatchEntry>>() {
                };

                if (dataSnapshot.getValue(t) == null) {
                    notifyError("No Matches found in MatchesInteractor");
                    return;
                }
                Map<String, MatchEntry> matchEntryMap = new HashMap<String, MatchEntry>(dataSnapshot.getValue(t));

                if (matchEntryMap.size() <= 0) {
                    notifyError("No Matches found in MatchesInteractor");
                    return;
                }

                if (tenantProfile == null) {
                    String apId = apartmentProfile.getId();
                    List<String> tenantIds = new ArrayList<>();

                    for (Map.Entry<String, MatchEntry> entry : matchEntryMap.entrySet()) {

                        String currentApId = entry.getKey().split(DELIMITER)[1];

                        if (apId.equals(currentApId)) {
                            tenantIds.add(entry.getKey().split(DELIMITER)[0]);
                        }
                    }
                    getTenantProfiles(tenantIds);
                } else {
                    String tenId = tenantProfile.getId();
                    List<String> apartmentIds = new ArrayList<>();

                    for (Map.Entry<String, MatchEntry> entry : matchEntryMap.entrySet()) {

                        String currentTenantId = entry.getKey().split(DELIMITER)[0];

                        if (tenId.equals(currentTenantId)) {
                            apartmentIds.add(entry.getKey().split(DELIMITER)[1]);
                        }
                    }
                    getApartmentProfiles(apartmentIds);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notifyError(databaseError.getMessage());
            }
        });

    }

    private void getApartmentProfiles(final List<String> apartmentIds) {

        final long size = 1024 * 1024;

        final List<Pair<ApartmentProfile, Bitmap>> apartmentsList = Collections.synchronizedList(new ArrayList<Pair<ApartmentProfile, Bitmap>>());

        final AtomicInteger counter = new AtomicInteger(0);

        if (apartmentIds.size() <= 0) {
            notifyError("ApartmentsIdList is empty!");
            return;
        }

        for (final String id : apartmentIds) {
            String apartmentPath = databaseRoot.getApartmentProfileNode(id).getRootPath();
            final String imagePath = storageRoot.getApartments(id).getImagesPath();

            mDatabase.child(apartmentPath).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null) {
                        final Pair<ApartmentProfile, Bitmap> apartmentImagePair = new Pair<ApartmentProfile, Bitmap>(dataSnapshot.getValue(ApartmentProfile.class), null);
                        mStorage.child(imagePath + UploadInteractorImpl.DEFAULT_NAME).getBytes(size).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                            @Override
                            public void onComplete(@NonNull Task<byte[]> task) {
                                if (task.isSuccessful()) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length);
                                    apartmentImagePair.setRight(bitmap);
                                    apartmentsList.add(apartmentImagePair);
                                } else {
                                    apartmentImagePair.setRight(null);
                                    apartmentsList.add(apartmentImagePair);
                                }
                            }
                        });
                    }

                    if (counter.incrementAndGet() == apartmentIds.size()) {
                        notifyApMatchesFound(apartmentsList);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "onCancelled: No apartment found with id: " + id);
                    if (counter.incrementAndGet() == apartmentIds.size()) {
                        notifyApMatchesFound(apartmentsList);
                    }
                }
            });


        }
    }

    private void getTenantProfiles(final List<String> tenantIds) {
        final long size = 1024 * 1024;

        final List<Pair<TenantProfile, Bitmap>> tenantList = Collections.synchronizedList(new ArrayList<Pair<TenantProfile, Bitmap>>());

        final AtomicInteger counter = new AtomicInteger(0);

        if (tenantIds.size() <= 0) {
            notifyError("TenantsIdList is empty!");
            return;
        }

        for (final String id : tenantIds) {
            String tenantPath = databaseRoot.getTenantProfileNode(id).getRootPath();
            final String imagePath = storageRoot.getTenants(id).getImagesPath();

            mDatabase.child(tenantPath).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null) {
                        final Pair<TenantProfile, Bitmap> tenantImagePair = new Pair<TenantProfile, Bitmap>(dataSnapshot.getValue(TenantProfile.class), null);
                        mStorage.child(imagePath + UploadInteractorImpl.DEFAULT_NAME).getBytes(size).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                            @Override
                            public void onComplete(@NonNull Task<byte[]> task) {
                                if (task.isSuccessful()) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length);
                                    tenantImagePair.setRight(bitmap);
                                    tenantList.add(tenantImagePair);
                                } else {
                                    tenantImagePair.setRight(null);
                                    tenantList.add(tenantImagePair);
                                }
                            }
                        });

                        if (counter.incrementAndGet() == tenantIds.size()) {
                            notifyTenMatchesFound(tenantList);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "onCancelled: No apartment found with id: " + id);
                    if (counter.incrementAndGet() == tenantIds.size()) {
                        notifyTenMatchesFound(tenantList);
                    }
                }
            });
        }
    }

    private void notifyTenMatchesFound(final List<Pair<TenantProfile, Bitmap>> tenantsList) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTenantMatchesFound(tenantsList);
            }
        });
    }

    private void notifyApMatchesFound(final List<Pair<ApartmentProfile, Bitmap>> apartmentsList) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onApartmentMatchesFound(apartmentsList);
            }
        });
    }

    private void notifyError(final String errorMessage) {
        Log.d(TAG, "inside notifyError()");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFailure(errorMessage);
            }
        });
    }

}
