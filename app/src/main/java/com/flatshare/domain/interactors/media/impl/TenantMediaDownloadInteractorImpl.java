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
    private static final String DEFAULT_NAME = "1";

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

        String mediaPath = "";

        final long ONE_MEGABYTE = 1024 * 1024;
        final long TEN_MEGABYTE = 10 * ONE_MEGABYTE;

        long size;

        if (isImage) {
            mediaPath = storageRoot.getTenants(this.tenantId).getImagesPath();
            size = ONE_MEGABYTE;
        } else {
            mediaPath = storageRoot.getTenants(this.tenantId).getVideosPath();
            size = TEN_MEGABYTE;
        }

        mStorage.child(mediaPath + DEFAULT_NAME).getBytes(size).addOnSuccessListener(
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
