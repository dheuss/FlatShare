package com.flatshare.domain.interactors.matching;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 08/01/2017.
 */
public interface RoommateQRInteractor extends Interactor{

    interface Callback {

        void notifyError(String errorMessage);
        void onCodeRead();
    }
}
