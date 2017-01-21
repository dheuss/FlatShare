package com.flatshare.domain.interactors.media.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 * Created by Arber on 28/12/2016.
 */
public class TenantMediaDownloadInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "TenantProfileDownloadInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private TenantDownloadCallback mCallback;

    private boolean isImage;

    private MainThread mMainThread;
    private String tenantId;

    public TenantMediaDownloadInteractorImpl(MainThread mainThread,
                                             TenantDownloadCallback downloadCallback,
                                             boolean isImage,
                                             String tenantId) {

        super(mainThread);
        this.mCallback = downloadCallback;
        this.isImage = isImage;
        this.tenantId = tenantId;
    }

    private void notifyError(final String errorMessage) {

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(errorMessage);
            }
        });
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess(final byte[] data) {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onDownloadSuccess(TenantMediaDownloadInteractorImpl.this.isImage, data);
            }
        });
    }


    @Override
    public void execute() {

        String mediaPath;

        final long KB_200 = 1024 * 200; // ~ 200 kb
        final long MB_10 = 50 * KB_200;

        long size;

        if (isImage) {
            mediaPath = storageRoot.getTenants(this.tenantId).getImagesPath();
            size = KB_200;
        } else {
            mediaPath = storageRoot.getTenants(this.tenantId).getVideosPath();
            size = MB_10;
        }

        mStorage.child(mediaPath + UploadInteractorImpl.DEFAULT_NAME).getBytes(size).addOnSuccessListener(
                new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        TenantMediaDownloadInteractorImpl.this.notifySuccess(bytes);
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                TenantMediaDownloadInteractorImpl.this.notifyError(exception.getMessage());
                            }
                        });

    }
}
