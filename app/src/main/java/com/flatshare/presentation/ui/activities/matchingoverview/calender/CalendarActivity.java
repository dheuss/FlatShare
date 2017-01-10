package com.flatshare.presentation.ui.activities.matchingoverview.calender;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.presentation.presenters.chat.CalendarPresenter;
import com.flatshare.presentation.presenters.chat.impl.CalendarPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AbstractActivity implements CalendarPresenter.View {

    private CalendarPresenter mPresenter;
    private ImageButton couchChatButton;


    private Calendar calendar;
    private TextView dateDisplay;
    private int year, month, day, hour, min;

    List<String> dateList = new ArrayList<String>();
    List<String> timeList = new ArrayList<String>();

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

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);


        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        showDate(year, month+1, day);

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @SuppressWarnings("deprecation")
    public void setTime(View view) {
        showDialog(888);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, datePickerListener, year, month, day);
        }else if(id == 888){
            return new TimePickerDialog(this, timePickerListener, year, month, true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker1, int year, int month, int day) {
            // TODO Auto-generated method stub

            // Toast.makeText(getApplicationContext(), "ca: " + datePicker1, Toast.LENGTH_SHORT).show();
            showDate(year, month+1, day);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker1, int hour, int min) {
            // TODO Auto-generated method stub

            // Toast.makeText(getApplicationContext(), "ca: " + datePicker1, Toast.LENGTH_SHORT).show();
            showDate(hour, min, 999);
        }
    };


    private void showDate(int arg1, int arg2, int arg3) {
        if(arg3 != 999){
            dateList.add(arg3 + "/" + arg2 + "/" + arg1);
        }else{
            timeList.add(arg1 + ":" + arg2);
        }

        dateDisplay.setText(dateList.toString() + " " + timeList.toString());
    }


    private void bindView() {
        couchChatButton = (ImageButton)findViewById(R.id.couchChatBtn);
        dateDisplay = (TextView) findViewById(R.id.date_display);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_calendar;
    }

    @Override
    public void showError(String message) {Toast.makeText(this, message, Toast.LENGTH_LONG).show();}
}
