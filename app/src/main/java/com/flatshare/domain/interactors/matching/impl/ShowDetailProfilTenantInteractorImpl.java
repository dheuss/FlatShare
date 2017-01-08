package com.flatshare.domain.interactors.matching.impl;

import com.flatshare.domain.MainThread;
import com.flatshare.domain.interactors.base.AbstractInteractor;
import com.flatshare.domain.interactors.matching.ShowDetailProfilApartmentInteractor;
import com.flatshare.domain.interactors.matching.ShowDetailProfilTenantInteractor;

/**
 * Created by david on 08.01.2017.
 */

public class ShowDetailProfilTenantInteractorImpl extends AbstractInteractor implements ShowDetailProfilApartmentInteractor {

    private static final String TAG = "ShowDetailProfilTenantInteractorImpl";

    private ShowDetailProfilApartmentInteractor.Callback mCallback;

    public ShowDetailProfilTenantInteractorImpl(MainThread mainThread,
                                                Callback callback) {
        super(mainThread);
        this.mCallback = callback;
    }

    @Override
    public void execute() {

    }
}
