package com.flatshare.domain.interactors.media.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by heussd on 30.01.17.
 */

public class DownloadTenantImageInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "DownloadTenantImageInteractorImpl";

    private DownloadCallback mCallback;

    private int nrProfiles = 0;

    private TenantProfile tenantProfile;

    private Bitmap tenantImage;

    public DownloadTenantImageInteractorImpl(MainThread mainThread, DownloadCallback downloadCallback, TenantProfile tenantProfile) {
        super(mainThread);
        this.mCallback = downloadCallback;
        this.tenantProfile = tenantProfile;
    }

    @Override
    public void execute() {
        Log.d(TAG, "execute: DOWNLOADTENANTIMAGE");
        downloadProfilePicture(tenantProfile);
    }

    private void notifyError(final String errorMessage) {

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onDownloadError(errorMessage);
            }
        });
    }

    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onDownloadTenantImageSuccess(tenantImage);
            }
        });
    }

    private void downloadProfilePicture(final TenantProfile profile) {

        String mediaPath;
        final String id = profile.getId();

        final long size = 1024 * 1024; // one MB

        mediaPath = storageRoot.getTenants(id).getImagesPath();

        mStorage.child(mediaPath + UploadInteractorImpl.DEFAULT_NAME).getBytes(size).addOnSuccessListener(
                new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        querySucceeded(profile, bytes);
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                queryFailed(profile);
                            }
                        });
    }

    private void queryFailed(TenantProfile tenantProfile) {
        //apartmentImageList.add(new Pair<ApartmentProfile, Bitmap>(apartmentProfile, null));
        //nrProfiles++;
        //if (nrProfiles == profilesList.size()) {
            notifySuccess();
        //}
    }

    private void querySucceeded(TenantProfile tenantProfile, byte[] bytes) {
        //apartmentImageList.add(new Pair<>(apartmentProfile, getBitmap(bytes)));
        //nrProfiles++;
        //if (nrProfiles == profilesList.size()) {
        tenantImage  = getBitmap(bytes);
        Log.d(TAG, "querySucceeded: " + tenantImage);
        notifySuccess();
        //}
    }

    private Bitmap getBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),
//                image.getHeight(), false)));
    }
}
