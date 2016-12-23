package com.flatshare.domain.interactors.base;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.network.StorageTree;
import com.flatshare.network.paths.database.Root;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 *
 * Created by Arber on 06/12/2016.
 */
public abstract class AbstractInteractor implements Interactor {

    private static final String TAG = "AbstractInteractor";
    protected MainThread mMainThread;

    protected DatabaseReference mDatabase;
    protected final String userId;

    protected Root root;

    protected StorageReference storageRef;

    public AbstractInteractor(MainThread mainThread) {

        Log.d(TAG, "inside constructor");
        this.mDatabase = FirebaseDatabase.getInstance().getReference();

        this.userId= FirebaseAuth.getInstance().getCurrentUser().getUid() + "/";
        root = new Root();

        this.storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(StorageTree.URL);
        mMainThread = mainThread;
    }

}
