package com.flatshare.domain.interactors.impl;

import android.net.Uri;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.enums.MediaType;
import com.flatshare.domain.interactors.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.storage.UploadTask;


/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class UploadInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "UploadInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private UploadCallback mCallback;

    private MediaType mediaType;

    private Uri uri;
    private boolean isTenant;

    public UploadInteractorImpl(MainThread mainThread,
                                UploadCallback downloadCallback, boolean isTenant, MediaType mediaType, Uri uri) {

        super(mainThread);
        this.mCallback = downloadCallback;
        this.isTenant = isTenant;
        this.mediaType = mediaType;
        this.uri = uri;
    }

    private void notifyError() {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onError("some kind of error"));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess() {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onUploadSuccess());
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void execute() {

        String childNode = "";

        String mediaName = "";


        if (isTenant) {
            childNode += storageRoot.getTenants().getRootPath();
            if (mediaType == MediaType.IMAGE) {
                childNode += storageRoot.getTenants().getImages();
            } else {
                childNode += storageRoot.getTenants().getVideos();
            }
            mediaName = userId;
        } else {
            childNode += storageRoot.getApartments().getImages();
            mediaName = uri.getLastPathSegment();
        }

        UploadTask uploadTask = mStorage.child(childNode + mediaName).putFile(uri);

        uploadTask.addOnFailureListener(exception -> notifyError()).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            notifySuccess();
        });
    }

}
