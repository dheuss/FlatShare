package com.flatshare.domain.interactors.matching;

import com.flatshare.domain.interactors.base.Interactor;

import java.util.Map;

/**
 * Created by Arber on 06/01/2017.
 */
public interface EmailRetrieverInteractor extends Interactor{

    interface Callback {
        void emailsRetrievedSuccess(Map<String, String> emailIdMap);
        void emailsRetrievedFailure(String errorMessage);
    }
}
