package com.flatshare.domain.interactors.media.impl;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.datatypes.enums.MediaType;
import com.flatshare.domain.interactors.media.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;


/**
 * Created by Arber on 23/12/2016.
 */
public class UploadInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "UploadInt";
    public static final String DEFAULT_NAME = "default.jpg";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private UploadCallback mCallback;

    private MediaType mediaType;

    private byte[] data;
    private boolean isTenant;
    private String id;

    public UploadInteractorImpl(MainThread mainThread,
                                UploadCallback downloadCallback, boolean isTenant, MediaType mediaType, byte[] data, String id) {

        super(mainThread);
        this.mCallback = downloadCallback;
        this.isTenant = isTenant;
        this.mediaType = mediaType;
        this.data = data;
        this.id = id;
    }

    private void notifyError() {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError("some kind of error");
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
                mCallback.onUploadSuccess();
            }
        });
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void execute() {

        String childNode;

        if (isTenant) {
            if (mediaType == MediaType.IMAGE) {
                childNode = storageRoot.getTenants(this.id).getImagesPath();
            } else {
                childNode = storageRoot.getTenants(this.id).getVideosPath();
            }
        } else {
            childNode = storageRoot.getApartments(this.id).getImagesPath();
        }

        UploadTask uploadTask = mStorage.child(childNode + DEFAULT_NAME).putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                UploadInteractorImpl.this.notifyError();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                UploadInteractorImpl.this.notifySuccess();
            }
        });
    }

}