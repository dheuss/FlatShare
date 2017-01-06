package com.flatshare.storage;

import com.flatshare.domain.interactors.matching.MatchingInteractor;
import com.flatshare.domain.interactors.matching.impl.MatchingInteractorImpl;
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
