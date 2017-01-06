package com.flatshare.domain.interactors;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 06/01/2017.
 */

public interface DeleteAccountInteractor extends Interactor {

    interface Callback {
        void onDeleteAccountSuccess();
        void onDeleteAccountFailure(String errorMessage);
    }
}
