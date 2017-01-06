package com.flatshare.domain.interactors.media.impl;

import android.net.Uri;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.enums.MediaType;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.firebase.storage.UploadTask;


/**
 * Created by Arber on 23/12/2016.
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
    private String id;

    public UploadInteractorImpl(MainThread mainThread,
                                UploadCallback downloadCallback, boolean isTenant, MediaType mediaType, Uri uri, String id) {

        super(mainThread);
        this.mCallback = downloadCallback;
        this.isTenant = isTenant;
        this.mediaType = mediaType;
        this.uri = uri;
        this.id = id;
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

        String childNode;

        String mediaName;


        if (isTenant) {
            if (mediaType == MediaType.IMAGE) {
                childNode = storageRoot.getTenants(this.id).getImagesPath();
            } else {
                childNode = storageRoot.getTenants(this.id).getVideosPath();
            }
            mediaName = userId;
        } else {
            childNode = storageRoot.getApartments(this.id).getImagesPath();
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
