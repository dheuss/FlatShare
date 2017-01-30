package com.flatshare.domain.interactors.matchingoverview;


import android.graphics.Bitmap;

import com.flatshare.domain.datatypes.db.common.MatchEntry;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.base.Interactor;

import java.util.List;

public interface MatchesInteractor extends Interactor {


    interface Callback {
        void onApartmentMatchesFound(final List<Pair<ApartmentProfile, Bitmap>> apMatches);
        void onTenantMatchesFound(final List<Pair<TenantProfile, Bitmap>> tenMatches);

        void onFailure(String error);
    }
}
