package com.flatshare.domain.interactors;


import com.flatshare.domain.interactors.base.Interactor;

public interface WelcomingInteractor extends Interactor {

    interface Callback {
        void onMessageRetrieved(String message);
        void onRetrievalFailed(String error);
    }
}
