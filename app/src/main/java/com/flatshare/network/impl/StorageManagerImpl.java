package com.flatshare.network.impl;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.network.StorageManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Arber on 11/12/2016.
 */

public class StorageManagerImpl implements StorageManager {

    private static final String TAG = "StorageManager";
    private StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://flatshare-5d4c6.appspot.com");


    private boolean uploadDataLocal(StorageReference storageRef, byte[] data) {

        UploadTask uploadTask = storageRef.putBytes(data);
        final AtomicBoolean uploaded = new AtomicBoolean(true);

// Register observers to listen for when the download is done or if it fails

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.w(TAG, "Could not upload File locally:", exception);
                uploaded.set(false);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                uploaded.set(true);
            }
        });

        return uploaded.get();
    }

    @Override
    public boolean uploadImage(String profileId, byte[] data) {
        return uploadDataLocal(storageRef.child(profileId + "/images"), data);
    }

    @Override
    public boolean uploadVideo(String profileId, byte[] data) {

        return uploadDataLocal(storageRef.child(profileId + "/videos"), data);

    }

    private byte[] downloadDataMemory(final StorageReference dataRef) {

        final byte[] b = null;

        final long ONE_MEGABYTE = 1024 * 1024;
        dataRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                System.arraycopy(bytes, 0, b, 0, bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // TODO: Handle any errors
                Log.w(TAG, "Could not download following file: " + dataRef.getPath());
            }
        });

        return b;
    }

    @Override
    public byte[] downloadImage(String profileId, String imgName) {
        return downloadDataMemory(storageRef.child(profileId + "/images/" + imgName));
    }

    @Override
    public byte[] downloadVideo(String profileId, String vidName) {
        return downloadDataMemory(storageRef.child(profileId + "/videos/" + vidName));

    }


}
