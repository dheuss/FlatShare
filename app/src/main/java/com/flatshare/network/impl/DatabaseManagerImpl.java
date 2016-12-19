package com.flatshare.network.impl;

import android.util.Log;

import com.flatshare.domain.datatypes.db.DatabaseItem;
import com.flatshare.network.DatabaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Arber on 06/12/2016.
 */

public class DatabaseManagerImpl implements DatabaseManager {

    private static final String TAG = "DatabaseManager";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private DatabaseItem databaseItem;

//    private String userId;

    public DatabaseManagerImpl() {
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

    }

    @Override
    public boolean create(Object item, String path) {

        AtomicBoolean itemCreated = new AtomicBoolean(false);

        mDatabase.child(path).setValue(item, (databaseError, databaseReference) -> {
            if (databaseError == null) { // Success!
                Log.d(TAG, "successfully created!");
                itemCreated.set(true);
            } else { // Failure
                Log.w(TAG, "FAILED!", databaseError.toException());
                itemCreated.set(false);
            }
        });

        return itemCreated.get();
    }

    @Override
    public DatabaseItem readItem(String path, final Class<? extends DatabaseItem> databaseItemClass) {


//                addValueEventListener
        mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                databaseItem = dataSnapshot.getValue(databaseItemClass);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                databaseItem = null;
            }
        });

        DatabaseItem tempDatabaseItem = this.databaseItem;
        this.databaseItem = null;

        return tempDatabaseItem;
    }

    @Override
    public List<String> readIds(String path) {

        List<String> ids = new ArrayList<>();

//                addValueEventListener
        mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot id: dataSnapshot.getChildren()) {
                    ids.add(id.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                databaseItem = null;
            }
        });

        return ids;
    }


    @Override
    public boolean update(Object newItem, String path) {

        AtomicBoolean itemUpdated = new AtomicBoolean(false);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(path, newItem);

        mDatabase.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) { // FAILURE!
                Log.w(TAG, "update FAILED!", task.getException());
                itemUpdated.set(false);
            } else { // SUCCESS
                Log.d(TAG, "successfully updated!");
                itemUpdated.set(true);
            }
        });

        return itemUpdated.get();

    }

    @Override
    public boolean delete(String path) {

        AtomicBoolean itemDeleted = new AtomicBoolean(false);

        mDatabase.child(path).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) { // FAILURE!
                Log.d(TAG, "delete FAILED!", databaseError.toException());
                itemDeleted.set(false);
            } else { // SUCCESS
                Log.d(TAG, "successfully deleted!");
                itemDeleted.set(true);
            }
        });

        return itemDeleted.get();
    }

    @Override
    public String push(Object item, String path) {
        String key = mDatabase.child(path).push().getKey();

        boolean created = create(item, path + "/" + key);

        return created ? key : null;

    }

}
