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
public class DownloadInteractorImpl extends AbstractInteractor implements MediaInteractor {

    private static final String TAG = "DownloadInt";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private DownloadCallback mCallback;

    private int mediaType;
    private String mediaName;

    private StorageRepository storageRepository;
    private boolean isTenant;

    public DownloadInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  DownloadCallback downloadCallback,boolean isTenant, int mediaType, String mediaName) {

        super(threadExecutor, mainThread);
        Log.d(TAG, "inside constructor");
        this.mCallback = downloadCallback;
        this.isTenant = isTenant;
        this.mediaType = mediaType;
        this.mediaName = mediaName;
        this.storageRepository = new StorageRepositoryImpl();
    }

    private void notifyError() {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(() -> mCallback.onError("some kind of error"));
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     */
    private void notifySuccess(byte[] data) {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(() -> mCallback.onDownloadSuccess(this.mediaType, data));
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void run() {
        Log.d(TAG, "inside run()");

        byte[] data = storageRepository.downloadMedia(this.isTenant, this.mediaType, this.mediaName);

        if(data == null){
            Log.w(TAG, "Download Failed!");
            notifyError();
        } else {
            notifySuccess(data);
        }

    }
}
