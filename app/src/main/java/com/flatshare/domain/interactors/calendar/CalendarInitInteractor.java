package com.flatshare.domain.interactors.calendar;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Sandro on 16.01.17.
 */

public interface CalendarInitInteractor extends Interactor {

    interface Callback {

        void onSentFailure(String errorMessage);

        void onSentSuccess();
    }
}
