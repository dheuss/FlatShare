package com.flatshare.storage;

import com.flatshare.domain.repository.StorageRepository;
import com.flatshare.network.DatabaseManager;
import com.flatshare.network.StorageManager;
import com.flatshare.network.impl.DatabaseManagerImpl;
import com.flatshare.network.impl.StorageManagerImpl;

/**
 * Created by Arber on 10/12/2016.
 */

public class StorageRepositoryImpl implements StorageRepository {

    private StorageManager storageManager;
    private String tenantProfileId;
    private String apartmentProfileId;

    public StorageRepositoryImpl() {
        storageManager = new StorageManagerImpl();
        DatabaseManager databaseManager = new DatabaseManagerImpl();

        tenantProfileId = databaseManager.getTenantProfileId();
        apartmentProfileId = databaseManager.getApartmentProfileId();
    }

    @Override
    public byte[] downloadMedia(boolean isTenant, int mediaType, String mediaName) {

        byte[] data = null;

        if (mediaType == 0) { // image
            if (isTenant) { // tenant

                data = storageManager.downloadImage(tenantProfileId, mediaName);
            } else { // apartment

                data = storageManager.downloadImage(apartmentProfileId, mediaName);
            }
        } else if (mediaType == 1) { // video
            if (isTenant) {
                data = storageManager.downloadVideo(tenantProfileId, mediaName);

            } else {
                data = storageManager.downloadVideo(apartmentProfileId, mediaName);

            }
        }

        return data;
    }

    @Override
    public boolean uploadMedia(boolean isTenant, int mediaType, String mediaName, byte[] data) {

        if (mediaType == 0) { // image
            if (isTenant) { // tenant


                return storageManager.uploadImage(tenantProfileId, data);
            } else { // apartment

                return storageManager.uploadImage(apartmentProfileId, data);
            }
        } else if (mediaType == 1) { // video
            if (isTenant) {

                return storageManager.uploadVideo(tenantProfileId, data);
            } else {
                return storageManager.uploadVideo(tenantProfileId, data);
            }
        }
        return false;
    }
}
