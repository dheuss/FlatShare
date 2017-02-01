package com.flatshare.domain.interactors.calendar;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Sandro on 01.02.17.
 */

public interface CalendarCheckFinalDateInteractor extends Interactor {

    interface Callback {

        void onAppointmentSet(long appointment);
    }
}
