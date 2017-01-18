package com.flatshare.presentation.presenters.matchingoverview.calendar;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by Sandro on 07.01.17.
 */

public interface CalendarPresenter extends BasePresenter {

    void sendDateToTenant(List<String> dateList, List<String> timeList, String tenantID, String apartmentID);

    boolean checkForTenant();

    void sendBackFromTenant(String finalDate, String tenantID, String apartmentID);


    interface View extends BaseView {
        void datesSuccessfulToTenants();

        void showFinalDate(String finalDate);

        void setDatesFromWG(List<String> dateList, List<String> timeList);
    }
}
