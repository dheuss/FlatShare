package com.flatshare.domain.interactors.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.ShowDetailProfilApartmentInteractor;

/**
 * Created by david on 08.01.2017.
 */

public class ShowDetailProfilApartmentInteractorImpl extends AbstractInteractor implements ShowDetailProfilApartmentInteractor {

    private static final String TAG = "ShowDetailProfilApartmentInteractorImpl";

    private ShowDetailProfilApartmentInteractor.Callback mCallback;

    public ShowDetailProfilApartmentInteractorImpl(MainThread mainThread,
                                                   Callback callback) {
        super(mainThread);
        this.mCallback = callback;
    }

    @Override
    public void execute() {

    }
}
