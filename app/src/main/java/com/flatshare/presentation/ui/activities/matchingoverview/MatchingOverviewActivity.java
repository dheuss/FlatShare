package com.flatshare.presentation.ui.activities.matchingoverview;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

import com.flatshare.R;

import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;
import com.flatshare.presentation.presenters.matchingoverview.impl.MatchingOverviewPresenterImpl;
import com.flatshare.presentation.ui.AbstractFragmentActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.calendar.CalendarActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

public class MatchingOverviewActivity extends AbstractFragmentActivity implements MatchingOverviewPresenter.View {

    private ProgressBar progressBar;


    private static final String TAG = "MatchingOverviewActivity";

    private MatchingOverviewPresenter mPresenter;
    private TableLayout matchingOverview;
    private int elementHeight;
    private int elementWidth;

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


        List<String> testList = new ArrayList<>();
        List<Integer> imageTest = new ArrayList<>();

        for (int i = 0; i < 15; i++) {

            testList.add("Match: " + i);
            imageTest.add(R.drawable.tenant_default);
        }
        generateMatchingOverview(testList, imageTest);


        return view;
    }

    public void generateMatchingOverview(List<String> matchingTitleList, List<Integer> matchingImageList) {

        for (int i = 0; i < matchingTitleList.size(); i++) {

            TableRow row = new TableRow(this.getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView matchingText = new TextView(this.getActivity());
            ImageView matchingImage = new ImageView(this.getActivity());
            Button matchingCalendar = new Button(this.getActivity());
            Button deleteButton = new Button(this.getActivity());

            matchingImage.setImageResource(matchingImageList.get(i)); // TODO set image
            //matchingImage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams., ViewGroup.LayoutParams.MATCH_PARENT));

            matchingText.setText(matchingTitleList.get(i));
            //matchingText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            matchingCalendar.setVisibility(View.VISIBLE);
            matchingCalendar.setBackground(getResources().getDrawable(R.drawable.calendar_icon));
            matchingCalendar.setOnClickListener(myCalendarHandler);
            //matchingCalendar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setBackground(getResources().getDrawable(R.drawable.clear_icon));
            deleteButton.setOnClickListener(myDeleteHandler);
            //deleteButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            int buttonWidth = 120;
            int buttonHeight =120;


            //Toast.makeText(this.getActivity(), "Sandro "  + buttonWidth, Toast.LENGTH_SHORT).show();


            row.addView(matchingImage);
            row.addView(matchingText);
            row.addView(matchingCalendar);
            row.addView(deleteButton);

            TableRow.LayoutParams paramsImage = (TableRow.LayoutParams)matchingImage.getLayoutParams();
            paramsImage.setMargins(20,0,20,0);
            paramsImage.width = buttonWidth;
            paramsImage.height = buttonHeight;
            matchingCalendar.setLayoutParams(paramsImage);

            TableRow.LayoutParams paramsText = (TableRow.LayoutParams)matchingText.getLayoutParams();
            paramsText.setMargins(20,0,20,0);
            paramsImage.width = buttonWidth;
            paramsImage.height = buttonHeight;
            matchingText.setLayoutParams(paramsText);

            TableRow.LayoutParams paramsCalendar = (TableRow.LayoutParams)matchingCalendar.getLayoutParams();
            paramsCalendar.setMargins(20,0,20,0);
            paramsImage.width = buttonWidth;
            paramsImage.height = buttonHeight;
            matchingCalendar.setLayoutParams(paramsCalendar);

            matchingOverview.addView(row, i);
        }

    }

    View.OnClickListener myCalendarHandler = new View.OnClickListener() {
        public void onClick(View v) {
            //TODO übergeben welcher Match übergeben wird
            startActivity(new Intent(getActivity(), CalendarActivity.class));
        }
    };

    View.OnClickListener myDeleteHandler = new View.OnClickListener() {
        public void onClick(View v) {
            for (int i = 0; i < matchingOverview.getChildCount(); i++) {
                View view = matchingOverview.getChildAt(i);

                if (i < matchingOverview.getChildCount() - 1) {
                    View viewNext = matchingOverview.getChildAt(i + 1);

                    if (view instanceof TableRow) {
                        TableRow row = (TableRow) view;
                        TableRow rowNext = (TableRow) viewNext;

                        for (int j = 0; j < row.getChildCount(); j++) {
                            View elementView = row.getChildAt(j);

                            if (elementView instanceof Button) {
                                Button currentButton = (Button) elementView;

                                View buttonViewNext = rowNext.getChildAt(3);
                                Button buttonNext = (Button) buttonViewNext;

                                View calendarButtonNextView = rowNext.getChildAt(2);
                                Button nextCalendarButton = (Button) calendarButtonNextView;

                                View textViewNext = rowNext.getChildAt(1);
                                TextView nextDateText = (TextView) textViewNext;

                                View textView = row.getChildAt(1);
                                TextView currentDateText = (TextView) textView;

                                View imageViewNext = rowNext.getChildAt(0);
                                ImageView nextImageView = (ImageView) imageViewNext;

                                View imageView = row.getChildAt(0);
                                ImageView currentImageView = (ImageView) imageView;

                                if (currentButton == v) {
                                    //TODO semd gelöschtest zu firebase
                                    //dateList.remove(i);
                                    //timeList.remove(i);
                                    currentDateText.setText(nextDateText.getText());
                                    currentImageView.setImageDrawable(nextImageView.getDrawable());
                                    nextImageView.setVisibility(View.GONE);
                                    nextCalendarButton.setVisibility(View.GONE);
                                    buttonNext.setVisibility(View.GONE);
                                    nextDateText.setText("");
                                    rowNext.setVisibility(View.GONE);
                                    if (currentDateText.getText() == "") {
                                        currentButton.setVisibility(View.GONE);
                                        row.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (view instanceof TableRow) {
                        TableRow row = (TableRow) view;

                        for (int j = 0; j < row.getChildCount(); j++) {
                            View elementView = row.getChildAt(j);

                            if (elementView instanceof Button) {
                                Button currentButton = (Button) elementView;

                                View textView = row.getChildAt(1);
                                TextView currentDateText = (TextView) textView;

                                View calendarButtonCurrentView = row.getChildAt(2);
                                Button currentCalendarButton = (Button) calendarButtonCurrentView;

                                View imageView = row.getChildAt(0);
                                ImageView currentImageView = (ImageView) imageView;

                                if (currentButton == v) {
                                    //TODO senden an Firebase
                                    //dateList.remove(i);
                                    //timeList.remove(i);
                                    currentDateText.setText("");
                                    currentCalendarButton.setVisibility(View.GONE);
                                    currentButton.setVisibility(View.GONE);
                                    currentImageView.setVisibility(View.GONE);
                                    row.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    public void bindView(View view) {
        matchingOverview = (TableLayout) view.findViewById(R.id.matchingOverview);
    }

    @Override
    public void showError(String message) {

    }
}
