package com.flatshare.network.impl;

import android.net.Uri;
import android.util.Log;

import com.flatshare.network.StorageManager;
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
        AtomicBoolean uploaded = new AtomicBoolean(true);

// Register observers to listen for when the download is done or if it fails

        uploadTask.addOnFailureListener(exception -> {
            Log.w(TAG, "Could not upload File locally:", exception);
            uploaded.set(false);
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            uploaded.set(true);
        });

        return uploaded.get();
    }

    @Override
    public boolean uploadImage(String userId, byte[] data) {
        return uploadDataLocal(storageRef.child(userId + "/images"), data);
    }

    @Override
    public boolean uploadVideo(String userId, byte[] data) {

        return uploadDataLocal(storageRef.child(userId + "/videos"), data);

    }

    private byte[] downloadDataMemory(StorageReference dataRef) {

        byte[] b = null;

        final long ONE_MEGABYTE = 1024 * 1024;
        dataRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            System.arraycopy(bytes, 0, b, 0, bytes.length);
        }).addOnFailureListener(exception -> {
            // TODO: Handle any errors
            Log.w(TAG, "Could not download following file: " + dataRef.getPath());
        });

        return b;
    }

    @Override
    public byte[] downloadImage(String userId, String imgName) {
        return downloadDataMemory(storageRef.child(userId + "/images/" + imgName));
    }

    @Override
    public byte[] downloadVideo(String userId, String vidName) {
        return downloadDataMemory(storageRef.child(userId + "/videos/" + vidName));

    }


}
