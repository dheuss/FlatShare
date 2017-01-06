package com.flatshare.presentation.presenters.chat.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.presentation.presenters.chat.ChatPresenter;
import com.flatshare.presentation.presenters.base.AbstractPresenter;

/**
 * Created by david on 28.12.2016.
 */

public class ChatPresenterImpl extends AbstractPresenter implements ChatPresenter{

    private ChatPresenter.View mView;

    public ChatPresenterImpl(MainThread mainThread, View view) {
        super(mainThread);
        mView = view;
    }

    @Override
    public void resume() {

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

    }
}
