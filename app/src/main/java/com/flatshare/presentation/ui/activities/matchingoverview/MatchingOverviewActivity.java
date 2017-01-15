package com.flatshare.presentation.ui.activities.matchingoverview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.flatshare.R;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;
import com.flatshare.presentation.presenters.matchingoverview.impl.MatchingOverviewPresenterImpl;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;
import com.flatshare.threading.MainThreadImpl;

public class MatchingOverviewActivity extends AbstarctFragmentAcivity implements MatchingOverviewPresenter.View {

    private ProgressBar progressBar;

    private static final String TAG = "MatchingOverviewActivity";

    private MatchingOverviewPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matching_overview, container, false);

        bindView(view);

        Log.d(TAG, "inside onCreate(), creating presenter fr this view");

        mPresenter = new MatchingOverviewPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        return view;
    }

    public void bindView(View view){

    }

    @Override
    public void showError(String message) {

    }
}
