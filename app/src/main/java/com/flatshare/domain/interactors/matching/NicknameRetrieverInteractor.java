package com.flatshare.domain.interactors.matching;

import com.flatshare.domain.interactors.base.Interactor;

import java.util.Map;

/**
 * Created by Arber on 06/01/2017.
 */
public interface NicknameRetrieverInteractor extends Interactor{

    interface Callback {
        void nicknamesRetrievedSuccess(Map<String, String> emailIdMap);
        void nicknamesRetrievedFailure(String errorMessage);
    }
}
