package com.flatshare.presentation.ui.activities.profile;

import android.os.Bundle;

import com.flatshare.R;
import com.flatshare.presentation.presenters.matching.RoommateWaitingPresenter;
import com.flatshare.presentation.presenters.matching.impl.RoommateWaitingPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class RoommateWaitingActivity extends AbstractActivity implements RoommateWaitingPresenter.View{

    private RoommateWaitingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: add some waiting Image


            mPresenter = new RoommateWaitingPresenterImpl(
                    MainThreadImpl.getInstance(),
                    this
            );
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_roommate_waiting;
    }

    @Override
    public void showError(String message) {
        //TODO:
    }
}
