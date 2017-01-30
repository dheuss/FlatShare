package com.flatshare.domain.interactors.matchingoverview;

import android.media.Image;

import com.flatshare.domain.interactors.base.Interactor;
import com.flatshare.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by david on 15.01.2017.
 */

public interface MatchingOverviewInteractor extends Interactor {
    interface Callback {
        void onSentSuccess();
        void onSentFailure(String error);
    }
}
