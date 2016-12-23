package com.flatshare.presentation.presenters.impl;

import android.util.Log;

import com.flatshare.domain.MainThread;
import com.flatshare.presentation.presenters.MainPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;


/**
 * Created by Arber on 11/12/2016.
 */

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter {


    private static final String TAG = "MainPresenterImpl";

    private MainPresenter.View mView;
//    private MessageRepository  mMessageRepository;

    public MainPresenterImpl(MainThread mainThread,
                             View view) {
        super(mainThread);

        Log.d(TAG, "inside constructor");

        mView = view;
//        mMessageRepository = messageRepository;
    }

    @Override
    public void resume() {

        Log.d(TAG, "inside resume()");

        mView.showProgress();

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

        Log.d(TAG, "inside onError(String message)");

        mView.showError(message);
    }

//    @Override
//    public void onMessageRetrieved(String message) {
//
//        Log.d(TAG, "inside onMessageRetrieved(String message), callback???");
//
//        mView.hideProgress();
//        mView.displayWelcomeMessage(message);
//    }
//
//    @Override
//    public void onRetrievalFailed(String error) {
//
//        Log.d(TAG, "inside onRetrievalFailed(String error)");
//
//        mView.hideProgress();
//        onError(error);
//    }
}
