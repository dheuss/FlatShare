package com.flatshare.network.impl;

import android.net.Uri;
import android.util.Log;

import com.flatshare.network.StorageManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Arber on 11/12/2016.
 */

public class StorageManagerImpl implements StorageManager {

    private static final String TAG = "StorageManager";
    private StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://flatshare-5d4c6.appspot.com");


    public StorageManagerImpl() {

    }

    private boolean uploadDataLocal(StorageReference storageRef, String filePath) {
        Uri file = Uri.fromFile(new File(filePath));

        UploadTask uploadTask = storageRef.putFile(file);
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

    public boolean uploadDataLocal(String userId, String filePath) {
        return uploadDataLocal(storageRef.child(userId + "/images"), filePath);
    }

    public boolean uploadVideoLocal(String userId, String filePath) {

        return uploadDataLocal(storageRef.child(userId + "/videos"), filePath);

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

    public byte[] downloadImage(String userId, String imgName) {
        return downloadDataMemory(storageRef.child(userId + "/images/" + imgName));
    }

    public byte[] downloadVideo(String userId, String vidName) {
        return downloadDataMemory(storageRef.child(userId + "/videos/" + vidName));

    }


}
