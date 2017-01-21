package com.flatshare.domain.interactors.media.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Arber on 28/12/2016.
 */
public class TenantDownloadInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "TenantProfileDownloadInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private DownloadCallback mCallback;

    private List<TenantProfile> profilesList;
    private int nrProfiles = 0;
    private List<Pair<TenantProfile, Bitmap>> tenantImageList;

    public TenantDownloadInteractorImpl(MainThread mainThread,
                                        DownloadCallback downloadCallback,
                                        List<TenantProfile> profilesList) {

        super(mainThread);
        this.mCallback = downloadCallback;
        this.profilesList = profilesList;
        tenantImageList = new ArrayList<>();
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
                mCallback.onTenantDownloadSuccess(tenantImageList);
            }
        });
    }


    @Override
    public void execute() {

        for (TenantProfile profile : profilesList) {
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
        tenantImageList.add(new Pair<TenantProfile, Bitmap>(tenantProfile, null));
        nrProfiles++;
        if (nrProfiles == profilesList.size()) {
            notifySuccess();
        }
    }

    private void querySucceeded(TenantProfile tenantProfile, byte[] bytes) {
        tenantImageList.add(new Pair<>(tenantProfile, getBitmap(bytes)));
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
