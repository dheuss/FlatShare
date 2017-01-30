package com.flatshare.domain.interactors.calendar;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Sandro on 30.01.17.
 */

public interface CalendarCheckAppointmentInteractor extends Interactor {
    interface Callback {

        void onAppointmentFailure(String errorMessage);

        void onAppointmentSuccess();
    }
}
