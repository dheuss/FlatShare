package com.flatshare.presentation.ui.activities.matchingoverview.calender;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.presentation.presenters.chat.CalendarPresenter;
import com.flatshare.presentation.presenters.chat.impl.CalendarPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;


public class CalendarActivity extends AbstractActivity implements CalendarPresenter.View {

    private CalendarPresenter mPresenter;
    private CalendarView calendarView;
    private ImageButton couchChatButton;
    private TextView dateDisplay;
    List<Long> dateList = new ArrayList<Long>();

    private static final String TAG = "CalenderAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new CalendarPresenterImpl(MainThreadImpl.getInstance(), this);

        couchChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarActivity.this, MatchingActivity.class));
            }
        });


        calendarView.setFirstDayOfWeek(2);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        dateDisplay.setText("Date: ");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                //for (int i = 0; i <= dateList.size(); i++){
                //    if (dateList.get(i) != calendarView.getDate()){
                        dateList.add(calendarView.getDate());
                        //calendarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                //    }
                //}

                dateDisplay.setText("Date: " + day + " / " + month + " / " + year);

                Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + day + "\n" + "Month = " + month + "\n" + "Year = " + year, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void bindView() {
        couchChatButton = (ImageButton)findViewById(R.id.couchChatBtn);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dateDisplay = (TextView) findViewById(R.id.date_display);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_calendar;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
