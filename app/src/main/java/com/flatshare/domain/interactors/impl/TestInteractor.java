package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.utils.random.ProfileGenerator;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Arber on 03/01/2017.
 */
public class TestInteractor extends AbstractInteractor {

    public TestInteractor(MainThread mainThread) {
        super(mainThread);
    }

    @Override
    public void execute() {
        int size = 1000;

        String tPath = "test/" + databaseRoot.getTenantProfiles();
        String aPath = "test/" + databaseRoot.getApartmentProfiles();

        List<TenantProfile> tList = ProfileGenerator.generateTenantProfiles(size);
        List<ApartmentProfile> aList = ProfileGenerator.generateApartmentProfiles(size);


        for (int i = 0; i < size; i++) {
            addNode(aPath, aList.get(i));
            addNode(tPath, tList.get(i));
        }


    }

    private void addNode(String aPath, UserProfile node) {

        String id = mDatabase.child(aPath).push().getKey();

        mDatabase.child(aPath + id).setValue(node, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {

                } else {
                    Log.d("ERROR", "ERROR");
                }
            }
        });
    }
}
