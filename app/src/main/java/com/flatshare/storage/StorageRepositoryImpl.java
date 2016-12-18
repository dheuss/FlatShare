package com.flatshare.storage;

import com.flatshare.domain.repository.StorageRepository;
import com.flatshare.network.StorageManager;
import com.flatshare.network.impl.StorageManagerImpl;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Arber on 10/12/2016.
 */

public class StorageRepositoryImpl implements StorageRepository {

    private StorageManager storageManager;
    private String userId;

    public StorageRepositoryImpl() {
        storageManager = new StorageManagerImpl();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public byte[] downloadMedia(int mediaType, String mediaName) {

        byte[] data = null;

        if (mediaType == 0) {
            data = storageManager.downloadImage(userId, mediaName);
        } else if (mediaType == 1) {
            data = storageManager.downloadVideo(userId, mediaName);
        } else {
            // ??
        }

        return data;
    }

    @Override
    public boolean uploadMedia(int mediaType, String mediaName, byte[] data) {
        if (mediaType == 0) { // image
            return storageManager.uploadImage(userId, data);
        } else if (mediaType == 1) { // photo
            return storageManager.uploadVideo(userId, data);
        } else {
            return false;
        }
    }
}
