package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.MediaInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.repository.StorageRepository;
import com.flatshare.storage.StorageRepositoryImpl;


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

    private int mediaType;
    private String mediaName;
    private byte[] data;

    private StorageRepository storageRepository;
    private boolean isTenant;

    public UploadInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                UploadCallback downloadCallback,boolean isTenant, int mediaType, String mediaName, byte[] data) {

        super(threadExecutor, mainThread);
        Log.d(TAG, "inside constructor");
        this.mCallback = downloadCallback;
        this.isTenant = isTenant;
        this.mediaType = mediaType;
        this.mediaName = mediaName;
        this.data = data;
        this.storageRepository = new StorageRepositoryImpl();
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
    public void run() {
        Log.d(TAG, "inside run()");

        if(storageRepository.uploadMedia(isTenant, mediaType,mediaName,this.data)){
            notifySuccess();
        } else {
            Log.w(TAG, "Upload Failed!");
            notifyError();
        }

    }
}
