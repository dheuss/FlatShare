package com.flatshare.storage;

import com.flatshare.domain.interactors.MatchingInteractor;
import com.flatshare.domain.interactors.impl.MatchingInteractorImpl;
import com.flatshare.domain.repository.MatchingRepository;

/**
 * Created by Arber on 10/12/2016.
 */

public class MatchingRepositoryImpl implements MatchingRepository {

    private MatchingInteractor matchingInteractor;

    public MatchingRepositoryImpl(MatchingInteractorImpl matchingInteractor) {
        this.matchingInteractor = matchingInteractor;
    }

}
