package com.flatshare.presentation.presenters.matchingoverview.calendar;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by Sandro on 07.01.17.
 */

public interface CalendarPresenter extends BasePresenter {

    void send(List<String> dateList, List<String> timeList);

    boolean checkForTendant();

    interface View extends BaseView {

    }
}
