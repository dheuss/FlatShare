package com.flatshare.network.impl;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import com.flatshare.domain.datatypes.db.DatabaseItem;
import com.flatshare.network.DatabaseManager;

/**
 * Created by Arber on 06/12/2016.
 */

public class DatabaseManagerImpl implements DatabaseManager {

    private static final String TAG = "DatabaseManager";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private DatabaseItem databaseItem;

    private String userId;
    private String tenantId;
    private String apartmentId;

    public DatabaseManagerImpl() {
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Override
    public void create(DatabaseItem databaseItem, String path) throws DatabaseException {

        mDatabase.child(path).setValue(databaseItem, (databaseError, databaseReference) -> {
            if (databaseError == null) { // Success!
                Log.d(TAG, "successfully created!");

            } else { // Failure
                Log.w(TAG, "FAILED!", databaseError.toException());
                throw databaseError.toException();
            }
        });
    }

    @Override
    public DatabaseItem read(String path, final Class<? extends DatabaseItem> databaseItemClass) throws DatabaseException {

        mDatabase.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                databaseItem = dataSnapshot.getValue(databaseItemClass);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                throw error.toException();
            }
        });

        DatabaseItem tempDatabaseItem = this.databaseItem;
        this.databaseItem = null;

        return tempDatabaseItem;
    }


    @Override
    public void update(DatabaseItem newDatabaseItem, String path) throws DatabaseException {

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(path, newDatabaseItem);

        mDatabase.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) { // FAILURE!
                Log.w(TAG, "update FAILED!", task.getException());
                throw new DatabaseException("cannot update...");
            } else { // SUCCESS
                Log.d(TAG, "successfully updated!");
            }
        });


//        mDatabase.child(path).setValue(newDatabaseItem, (databaseError, databaseReference) -> {
//            if (databaseError != null) { // FAILURE!
//                Log.w(TAG, "update FAILED!", databaseError.toException());
//                throw databaseError.toException();
//            } else { // SUCCESS
//                Log.d(TAG, "successfully updated!");
//            }
//        });

    }

    @Override
    public void delete(String path) throws DatabaseException {
        mDatabase.child(userId + path).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) { // FAILURE!
                Log.d(TAG, "delete FAILED!", databaseError.toException());
                throw databaseError.toException();
            } else { // SUCCESS
                Log.d(TAG, "successfully deleted!");
            }
        });
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void addJsonRoot(String path, String id) throws DatabaseException {
        mDatabase.child(path).setValue(id, (databaseError, databaseReference) -> {
            if (databaseError == null) { // Success!
                Log.d(TAG, "successfully created!");

            } else { // Failure
                Log.w(TAG, "FAILED!", databaseError.toException());
                throw databaseError.toException();
            }
        });
    }
}
