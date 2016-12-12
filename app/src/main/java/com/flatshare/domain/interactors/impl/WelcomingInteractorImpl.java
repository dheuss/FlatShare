package com.flatshare.domain.interactors.impl;

import android.util.Log;

import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;
import com.flatshare.domain.interactors.WelcomingInteractor;
import com.flatshare.domain.interactors.base.AbstractInteractor;


/**
 * TODO: was just an example, delete it or replace it with MatchingInteractor
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class WelcomingInteractorImpl extends AbstractInteractor implements WelcomingInteractor {

    private static final String TAG = "WelcomingInteractorImpl";

    /**
     * The Callback is responsible for talking to the UI on the main thread
     */
    private WelcomingInteractor.Callback mCallback;

    public WelcomingInteractorImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   Callback callback) {

        super(threadExecutor, mainThread);
        Log.d(TAG, "inside constructor");
        mCallback = callback;
//        mMessageRepository = messageRepository;
    }

    private void notifyError() {
        Log.d(TAG, "inside notifyError()");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Nothing to welcome you with :(");
            }
        });
    }

    /**
     * callback method that posts message received into the main UI, through mainThread.post!!!
     *
     * @param msg
     */
    private void postMessage(final String msg) {
        Log.d(TAG, "inside postMessage(String msg)");

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onMessageRetrieved(msg);
            }
        });
    }

    /**
     * contains the business logic for this use case (Interactor), SHOULD ALWAYS CALL EXECUTE NOT START!!!!
     */
    @Override
    public void run() {
        Log.d(TAG, "inside run()");

        // retrieve the message
//        final String message = mMessageRepository.getWelcomeMessage();

        // check if we have failed to retrieve our message
//        if (message == null || message.length() == 0) {
//
//            // notify the failure on the main thread
//            notifyError();
//
//            return;
//        }
//
//        // we have retrieved our message, notify the UI on the main thread
//        postMessage(message);
    }
}
