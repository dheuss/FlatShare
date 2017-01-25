package com.flatshare.presentation.presenters.matchingoverview;

import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by david on 15.01.2017.
 */

public interface MatchingOverviewPresenter extends BasePresenter {

    void deleteMatch();

    interface View extends BaseView {

        void generateMatchingOverview(List<String> matchingTitleList, List<Integer> matchingImageList);
    }
}
