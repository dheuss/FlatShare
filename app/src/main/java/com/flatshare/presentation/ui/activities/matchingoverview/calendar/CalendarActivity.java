package com.flatshare.presentation.ui.activities.matchingoverview.calendar;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.flatshare.R;

import com.flatshare.presentation.presenters.matchingoverview.calendar.CalendarPresenter;
import com.flatshare.presentation.presenters.matchingoverview.calendar.impl.CalendarPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AbstractActivity implements CalendarPresenter.View {

    private CalendarPresenter mPresenter;

    private ImageButton couchChatButton;
    private ImageButton deleteButton;
    private TableLayout dateDisplay;

    private Calendar calendar;
    private int yearCurrent, monthCurrent, dayCurrent, hourCurrent, minCurrent;

    List<String> dateList = new ArrayList<String>();
    List<String> timeList = new ArrayList<String>();

    private static final String TAG = "CalenderAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new CalendarPresenterImpl(MainThreadImpl.getInstance(), this);

        calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH);
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        hourCurrent = calendar.get(Calendar.HOUR_OF_DAY);
        minCurrent = calendar.get(Calendar.MINUTE);


        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        couchChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(CalendarActivity.this, MatchingActivity.class));
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        if (dateList.size() <= 10){
            showDialog(999);
        }else{
            Toast.makeText(getApplicationContext(), "Reach the maximum of Dates!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, datePickerListener, yearCurrent, monthCurrent, dayCurrent);
        }else if(id == 888){
            return new TimePickerDialog(this, timePickerListener, hourCurrent, minCurrent, true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            // TODO Auto-generated method stub
            datePicker.setMinDate(System.currentTimeMillis() - 1000);
            //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            showDate(year, month+1, day);
            showDialog(888);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            // TODO Auto-generated method stub
            showDate(hour, min, 999);
        }
    };


    private void showDate(int arg1, int arg2, int arg3) {
        if(arg3 != 999){
            dateList.add(arg3 + "/" + arg2 + "/" + arg1);
        }else{
            timeList.add(arg1 + ":" + arg2);
        }
        Toast.makeText(getApplicationContext(), "Test: " + dateList.size(), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < dateList.size()-1; i++) {
            Toast.makeText(getApplicationContext(), "Test! " + dateList.get(i).toString() + " " + timeList.get(i).toString() + " " + i, Toast.LENGTH_SHORT).show();
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            deleteButton = new ImageButton(this);
            deleteButton.setBackground(getResources().getDrawable(R.drawable.delete1));

            TextView dates = new TextView(this);
            dates.setText(dateList.get(0).toString() + " " + dateList.get(1).toString() + " ");
        //dates.setText(dateList.get(0).toString() + " " + timeList.get(0).toString() + " ");

            row.addView(dates);
            row.addView(deleteButton);

            dateDisplay.addView(row, i);
        //dateDisplay.addView(row);

        }
    }


    private void bindView() {
        couchChatButton = (ImageButton)findViewById(R.id.couchChatBtn);
        dateDisplay = (TableLayout) findViewById(R.id.date_display);
    }


    @Override
    protected int getLayoutResourceId() {return R.layout.activity_calendar;}

    @Override
    public void showError(String message) {Toast.makeText(this, message, Toast.LENGTH_LONG).show();}
}
