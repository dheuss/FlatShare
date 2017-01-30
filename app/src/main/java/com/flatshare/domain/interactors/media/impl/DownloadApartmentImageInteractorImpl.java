package com.flatshare.domain.interactors.media.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by heussd on 30.01.17.
 */

public class DownloadApartmentImageInteractorImpl extends AbstractInteractor implements MediaInteractor{

    private static final String TAG = "DownloadApartmentImageInteractorImpl";

    private DownloadCallback mCallback;

    private ApartmentProfile apartmentProfile;

    private Bitmap apartmentImage;

    public DownloadApartmentImageInteractorImpl(MainThread mainThread, DownloadCallback downloadCallback, ApartmentProfile apartmentProfile) {
        super(mainThread);
        this.mCallback = downloadCallback;
        this.apartmentProfile = apartmentProfile;
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
                mCallback.onDownloadApartmentImageSucess(apartmentImage);
            }
        });
    }

    @Override
    public void execute() {
        Log.d(TAG, "execute: DOWNLOADAPARTMENTIMAGE");
        downloadProfilePicture(apartmentProfile);
    }

    private void downloadProfilePicture(final ApartmentProfile profile) {

        String mediaPath;
        final String id = profile.getId();

        final long size = 1024 * 1024; // one MB

        mediaPath = storageRoot.getApartments(id).getImagesPath();

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

    private void queryFailed(ApartmentProfile apartmentProfile) {
//        apartmentImageList.add(new Pair<ApartmentProfile, Bitmap>(apartmentProfile, null));
//        nrProfiles++;
//        if (nrProfiles == profilesList.size()) {
            notifySuccess();
//        }
    }

    private void querySucceeded(ApartmentProfile apartmentProfile, byte[] bytes) {
//        apartmentImageList.add(new Pair<>(apartmentProfile, getBitmap(bytes)));
//        nrProfiles++;
//        if (nrProfiles == profilesList.size()) {
        apartmentImage = getBitmap(bytes);
            notifySuccess();
//        }
    }

    private Bitmap getBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),
//                image.getHeight(), false)));
    }
}
