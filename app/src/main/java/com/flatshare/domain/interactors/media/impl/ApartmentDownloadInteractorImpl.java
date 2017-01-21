package com.flatshare.domain.interactors.media.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Arber on 17/12/2016.
 */
public class ApartmentDownloadInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "ApartmentProfileDownloadInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private DownloadCallback mCallback;

    private List<ApartmentProfile> profilesList;
    private int nrProfiles = 0;
    private List<Pair<ApartmentProfile, Bitmap>> apartmentImageList;

    public ApartmentDownloadInteractorImpl(MainThread mainThread,
                                           DownloadCallback downloadCallback,
                                           List<ApartmentProfile> profilesList) {

        super(mainThread);
        this.mCallback = downloadCallback;
        this.profilesList = profilesList;
        apartmentImageList = new ArrayList<>();
    }

    private void notifyError(final String errorMessage) {

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onDownloadError(errorMessage);
            }
        });
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onApartmentDownloadSuccess(apartmentImageList);
            }
        });
    }


    @Override
    public void execute() {

        for (ApartmentProfile profile : profilesList) {
            downloadProfilePicture(profile);
        }

//        if (isImage) {
//            mediaPath = storageRoot.getTenants(this.tenantId).getImagesPath();
//            size = KB_200;
//        } else {
//            mediaPath = storageRoot.getTenants(this.tenantId).getVideosPath();
//            size = MB_10;
//        }


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
        apartmentImageList.add(new Pair<ApartmentProfile, Bitmap>(apartmentProfile, null));
        nrProfiles++;
        if (nrProfiles == profilesList.size()) {
            notifySuccess();
        }
    }

    private void querySucceeded(ApartmentProfile apartmentProfile, byte[] bytes) {
        apartmentImageList.add(new Pair<>(apartmentProfile, getBitmap(bytes)));
        nrProfiles++;
        if (nrProfiles == profilesList.size()) {
            notifySuccess();
        }
    }

    private Bitmap getBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),
//                image.getHeight(), false)));
    }
}
