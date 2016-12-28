package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
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

    private void notifyError(String errorMessage) {

        mMainThread.post(() -> mCallback.onError(errorMessage));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     * @param data
     */
    private void notifySuccess(byte[] data) {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onDownloadSuccess(data));
    }


    @Override
    public void execute() {

        String path = storageRoot.getApartments(apartmentId).getImagesPath();

        String mediaName = "SOME_IMAGE_NAME";

        final long ONE_MEGABYTE = 1024 * 1024;
        mStorage.child(path + mediaName).getBytes(ONE_MEGABYTE).addOnSuccessListener(
                bytes -> notifySuccess(bytes))
                .addOnFailureListener(
                        exception -> notifyError(exception.getMessage()));

    }
}
