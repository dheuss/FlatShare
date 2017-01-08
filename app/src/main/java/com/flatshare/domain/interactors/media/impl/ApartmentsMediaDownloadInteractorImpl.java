package com.flatshare.domain.interactors.media.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 * Created by Arber on 17/12/2016.
 */
public class ApartmentsMediaDownloadInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "ApartmentsMediaDownloadInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private ApartmentDownloadCallback mCallback;

    private MainThread mMainThread;

    private String apartmentId;

    public ApartmentsMediaDownloadInteractorImpl(MainThread mainThread,
                                                 ApartmentDownloadCallback downloadCallback,
                                                 String apartmentId) {

        super(mainThread);
        this.mCallback = downloadCallback;
        this.apartmentId = apartmentId;
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
     * @param data
     */
    private void notifySuccess(final byte[] data) {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onDownloadSuccess(data);
            }
        });
    }


    @Override
    public void execute() {

        String path = storageRoot.getApartments(apartmentId).getImagesPath();

        String mediaName = "SOME_IMAGE_NAME";

        final long ONE_MEGABYTE = 1024 * 1024;
        mStorage.child(path + mediaName).getBytes(ONE_MEGABYTE).addOnSuccessListener(
                new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        ApartmentsMediaDownloadInteractorImpl.this.notifySuccess(bytes);
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                ApartmentsMediaDownloadInteractorImpl.this.notifyError(exception.getMessage());
                            }
                        });

    }
}
