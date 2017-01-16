package com.flatshare.presentation.ui.activities.matchingoverview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;
import com.flatshare.presentation.presenters.matchingoverview.impl.MatchingOverviewPresenterImpl;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;
import com.flatshare.presentation.ui.activities.matchingoverview.calendar.CalendarActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

public class MatchingOverviewActivity extends AbstarctFragmentAcivity implements MatchingOverviewPresenter.View {

    private Button changeToCalendarButton;

    private ProgressBar progressBar;
    private List<String> testList = new ArrayList<>();


    private static final String TAG = "MatchingOverviewActivity";

    private MatchingOverviewPresenter mPresenter;
    private TableLayout machingOverview;

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
                //startActivity(new Intent(getActivity(), CalendarActivity.class));

                for (int i = 0; i<4; i++){
                    testList.add("bla 1");

                }
                generateMatchingOverview(testList);
            }
        });

        return view;
    }

    public void generateMatchingOverview(List<String> matchingList){
        for(int i = 0; i < matchingList.size(); i++){
            TableRow row = new TableRow(this.getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            System.out.println("Sandro: " + matchingList.get(i));
            TextView machtingText = new TextView(this.getActivity()) ;
            machtingText.setText(matchingList.get(i));
            row.addView(machtingText);
            machingOverview.addView(row, i);
        }

    }



    public void bindView(View view){
        changeToCalendarButton = (Button)view.findViewById(R.id.change_to_calendar_button);
        machingOverview = (TableLayout) view.findViewById(R.id.machingOverview);
    }

    @Override
    public void showError(String message) {

    }
}
