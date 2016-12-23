package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.network.StorageTree;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class DownloadInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "DownloadInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private DownloadCallback mCallback;

    private boolean isImage;
    private String mediaName;

    private boolean isTenant;

    private MainThread mMainThread;

    public DownloadInteractorImpl(MainThread mainThread,
                                  DownloadCallback downloadCallback,
                                  boolean isTenant,
                                  boolean isImage,
                                  String mediaName) {

        super(mainThread);
        this.mCallback = downloadCallback;
        this.isTenant = isTenant;
        this.isImage = isImage;
        this.mediaName = mediaName;
    }

    private void notifyError(String errorMessage) {

        mMainThread.post(() -> mCallback.onError(errorMessage));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess(byte[] data) {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onDownloadSuccess(this.isImage, data));
    }


    @Override
    public void execute() {

        String userPath = isTenant ? StorageTree.TENANT_PATH : StorageTree.APARTMENT_PATH;
        String profileId = "TODO!!!!";
        String mediaPath = isImage ? StorageTree.IMAGES_PATH : StorageTree.VIDEOS_PATH;

        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.child(userPath + profileId + mediaPath).getBytes(ONE_MEGABYTE).addOnSuccessListener(
                bytes -> notifySuccess(bytes))
                .addOnFailureListener(
                        exception -> notifyError(exception.getMessage()));

    }
}
