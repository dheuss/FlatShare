package com.flatshare.storage;

import com.flatshare.domain.interactors.matching.PotentialMatchingInteractor;
import com.flatshare.domain.interactors.matching.impl.PotentialMatchingInteractorImpl;
import com.flatshare.domain.repository.MatchingRepository;

/**
 * Created by Arber on 10/12/2016.
 */

public class MatchingRepositoryImpl implements MatchingRepository {

    private PotentialMatchingInteractor potentialMatchingInteractor;

    public MatchingRepositoryImpl(PotentialMatchingInteractorImpl matchingInteractor) {
        this.potentialMatchingInteractor = matchingInteractor;
    }

}
