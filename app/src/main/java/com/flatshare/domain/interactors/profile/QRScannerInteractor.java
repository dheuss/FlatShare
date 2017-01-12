package com.flatshare.domain.interactors.profile;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/01/2017.
 */
public interface QRScannerInteractor extends Interactor{

    interface Callback {

        void onFailure(String errorMessage);

        void onSuccess(String roommateId);
    }
}
