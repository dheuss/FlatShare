package com.flatshare.domain.interactors.base;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.network.path.database.DatabaseRoot;
import com.flatshare.network.path.storage.StorageRoot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Arber on 06/12/2016.
 */
public abstract class AbstractInteractor implements Interactor {

    private static final String TAG = "AbstractInteractor";

    protected MainThread mMainThread;

    // Database/Storage
    protected DatabaseReference mDatabase;
    protected StorageReference mStorage;

    protected String userId;

    // Paths in Database/Storage
    protected DatabaseRoot databaseRoot;
    protected StorageRoot storageRoot;

    public AbstractInteractor(MainThread mainThread) {

        Log.d(TAG, "inside constructor");

        this.mDatabase = FirebaseDatabase.getInstance().getReference();

        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseRoot = new DatabaseRoot();
        storageRoot = new StorageRoot();
        this.mStorage = FirebaseStorage.getInstance().getReferenceFromUrl(storageRoot.getUrl());

        mMainThread = mainThread;
    }

}
