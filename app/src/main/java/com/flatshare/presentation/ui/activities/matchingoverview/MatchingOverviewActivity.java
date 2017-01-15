package com.flatshare.presentation.ui.activities.matchingoverview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.flatshare.R;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;
import com.flatshare.presentation.presenters.matchingoverview.impl.MatchingOverviewPresenterImpl;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;
import com.flatshare.presentation.ui.activities.matchingoverview.calendar.CalendarActivity;
import com.flatshare.threading.MainThreadImpl;

public class MatchingOverviewActivity extends AbstarctFragmentAcivity implements MatchingOverviewPresenter.View {

    private Button changeToCalendarButton;

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

        changeToCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CalendarActivity.class));
            }
        });

        return view;
    }

    public void bindView(View view){
        changeToCalendarButton = (Button)view.findViewById(R.id.change_to_calendar_button);
    }

    @Override
    public void showError(String message) {

    }
}
