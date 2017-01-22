package com.flatshare.presentation.ui.activities.matchingoverview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.flatshare.R;

import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;
import com.flatshare.presentation.presenters.matchingoverview.impl.MatchingOverviewPresenterImpl;
import com.flatshare.presentation.ui.AbstractFragmentActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.calendar.CalendarActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

public class MatchingOverviewActivity extends AbstractFragmentActivity implements MatchingOverviewPresenter.View {

    private Button changeToCalendarButton;

    private ProgressBar progressBar;


    private static final String TAG = "MatchingOverviewActivity";

    private MatchingOverviewPresenter mPresenter;
    private TableLayout matchingOverview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matching_overview, container, false);

        bindView(view);

        Log.d(TAG, "inside onCreate(), creating presenter for this view");

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
                List<String> testList = new ArrayList<>();
                List<Integer> imageTest = new ArrayList<>();

                for (int i = 0; i<4; i++){
                    testList.add("bla 1");
                    imageTest.add(R.drawable.calendar_icon);
                }
                generateMatchingOverview(testList, imageTest);
            }
        });

        return view;
    }

    public void generateMatchingOverview(List<String> matchingTitleList, List<Integer> matchingImageList){

        for(int i = 0; i < matchingTitleList.size(); i++){
            TableRow row = new TableRow(this.getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView matchingText = new TextView(this.getActivity());
            ImageView matchingImage = new ImageView(this.getActivity());
            Button matchingCalendar = new Button(this.getActivity());
            Button deleteButton = new Button(this.getActivity());

            matchingImage.setImageResource(matchingImageList.get(i)); // TODO set image
            matchingText.setText(matchingTitleList.get(i));

            matchingCalendar.setVisibility(View.VISIBLE);
            matchingCalendar.setBackground(getResources().getDrawable(R.drawable.calendar_icon));
            matchingCalendar.setOnClickListener(myCalendarHandler); //TODO write Handler

            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setBackground(getResources().getDrawable(R.drawable.clear_icon));
            deleteButton.setOnClickListener(myDeleteHandler);

            row.addView(matchingImage);
            row.addView(matchingText);
            row.addView(matchingCalendar);
            row.addView(deleteButton);

            matchingOverview.addView(row, i);
        }

    }

    View.OnClickListener myCalendarHandler = new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), CalendarActivity.class));
        }
    };

    View.OnClickListener myDeleteHandler = new View.OnClickListener() {
        public void onClick(View v) {
            v.setVisibility(View.GONE);
            v.getRootView().setBackground(getResources().getDrawable(R.color.colorPrimary));
            //TODO element löschen und nachpben schieben
        }
    };

    public void bindView(View view){
        changeToCalendarButton = (Button)view.findViewById(R.id.change_to_calendar_button);
        matchingOverview = (TableLayout) view.findViewById(R.id.machingOverview);
    }

    @Override
    public void showError(String message) {

    }
}
