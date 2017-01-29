package com.flatshare.presentation.presenters.matching;

import com.flatshare.presentation.presenters.base.BasePresenter;

/**
 * Created by Arber on 29/01/2017.
 */

public interface MatchesPresenter extends BasePresenter {

    void setPotentialMatchesListener();

    interface View{

        void incrementBadgeCounter();
    }
}
