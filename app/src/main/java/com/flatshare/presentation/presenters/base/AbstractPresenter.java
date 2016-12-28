package com.flatshare.presentation.presenters.base;


import com.flatshare.domain.MainThread;
import com.flatshare.domain.state.UserState;


/**
 * Created by Arber on 06/12/2016.
 */
public abstract class AbstractPresenter {

    private static final String TAG = "AbstractPresenter";

    protected MainThread mMainThread;

    protected UserState userState;

    public AbstractPresenter(MainThread mainThread) {

        mMainThread = mainThread;
        userState = new UserState();
    }
}
