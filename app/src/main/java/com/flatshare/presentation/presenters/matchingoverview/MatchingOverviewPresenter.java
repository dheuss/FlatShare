package com.flatshare.presentation.presenters.matchingoverview;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 15.01.2017.
 */

public interface MatchingOverviewPresenter extends BasePresenter {

    void deleteMatch();

    interface View extends BaseView {

    }
}
