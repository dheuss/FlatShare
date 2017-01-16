package com.flatshare.domain.interactors.profile;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 16/01/2017.
 */

public interface UniqueNicknameInteractor extends Interactor {

    interface Callback {
        void nicknameResult(boolean unique);

        void onRequestFailure(String errorMessage);
    }
}
