package com.flatshare.presentation.ui.activities.matchingoverview.calendar;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.flatshare.R;

import com.flatshare.presentation.presenters.matchingoverview.calendar.CalendarPresenter;
import com.flatshare.presentation.presenters.matchingoverview.calendar.impl.CalendarPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarActivity extends AbstractActivity implements CalendarPresenter.View {

    private CalendarPresenter mPresenter;

    private Button setDate, send;

    private int yearCurrent, monthCurrent, dayCurrent, hourCurrent, minCurrent;

    private List<String> dateList = new ArrayList<>();
    private List<String> timeList = new ArrayList<>();

    private TableLayout dateOverview;

    private static final String TAG = "CalenderAct";

    private boolean isTenant;
    private String finalDate, tenantID, apartmentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new CalendarPresenterImpl(MainThreadImpl.getInstance(), this);

        isTenant = mPresenter.checkForTenant();


        if (isTenant){
            //    setDate.setVisibility(View.GONE);
        }

        Calendar calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH);
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        hourCurrent = calendar.get(Calendar.HOUR_OF_DAY);
        minCurrent = calendar.get(Calendar.MINUTE);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);


        //Send Button
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isTenant){
                    mPresenter.sendBackFromTenant(finalDate, tenantID, apartmentID);
                }else {
                    mPresenter.sendDateToTenant(dateList, timeList, tenantID, apartmentID);
                    Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        if (dateList.size() <= 20){
            showDialog(999);
            send.setVisibility(View.VISIBLE);

        }else{
            Toast.makeText(getApplicationContext(), "Reach the maximum of Dates!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            DatePickerDialog datePicker = new DatePickerDialog(this, datePickerListener, yearCurrent, monthCurrent, dayCurrent);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    if (which == DialogInterface.BUTTON_NEGATIVE)
                    {
                        //Nothing
                    }
                }
            });
            return datePicker;
        }else if(id == 888){
            TimePickerDialog timePicker = new TimePickerDialog(this, timePickerListener, hourCurrent, minCurrent, true);
            timePicker.updateTime(hourCurrent, minCurrent);
            timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    if (which == DialogInterface.BUTTON_NEGATIVE)
                    {
                        timeList.add("");
                        showOnScreen();
                    }
                }
            });
            return timePicker;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            dateList.add(day + "/" + month + 1 + "/" + year);
            showDialog(888);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            timeList.add(" " + hour + ":" + min);
            showOnScreen();
        }
    };

    public void showOnScreen(){

        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView dateText = new TextView(this);
        Button deleteButton = new Button(this);

        dateText.setText(dateList.get(dateList.size()-1) + " " + timeList.get(timeList.size()-1));

        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.clear_icon));
        deleteButton.setOnClickListener(myDeleteHandler);


        row.addView(dateText);
        row.addView(deleteButton);

        dateOverview.addView(row, dateList.size()-1);

    }

    View.OnClickListener myDeleteHandler = new View.OnClickListener() {
        public void onClick(View v) {

            String nextElementText = "";

            for (int i =0; i < dateOverview.getChildCount() - 1; i++) {
                View nextView = dateOverview.getChildAt(i + 1);

                if (nextView instanceof TableRow) {
                    TableRow nextRow = (TableRow) nextView;

                    for (int j = 0; j < nextRow.getChildCount(); j++) {
                        View nextElementView = nextRow.getChildAt(j);
                        if (nextElementView instanceof TextView) {
                            nextElementText = ((TextView) nextElementView).getText().toString();
                        }
                        nextElementView.setVisibility(View.GONE);
                    }
                }
            }

            for (int i =0; i < dateOverview.getChildCount(); i++){
                View view = dateOverview.getChildAt(i);

                if (view instanceof TableRow) {
                    TableRow row = (TableRow) view;

                    for (int x = 0; x < row.getChildCount(); x++) {
                        View elementView = row.getChildAt(x);

                        if (elementView instanceof TextView){
                            ((TextView) elementView).setText(nextElementText);
                        }
                    }
                    dateList.remove(i);
                    timeList.remove(i);
                }
            }
        }
    };


    //Tenant set Dates, show Tenant which dates send WG
    public void setDatesFromWG(List<String> dateList, List<String> timeList){
        for(int i = 0; i < dateList.size(); i++){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView dateText = new TextView(this);
            Button checkButton = new Button(this);

            dateText.setText(dateList.get(i) + " " + timeList.get(i));

            checkButton.setVisibility(View.VISIBLE);
            checkButton.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.done_icon));
            checkButton.setOnClickListener(myCheckHandler);

            row.addView(dateText);
            row.addView(checkButton);

            dateOverview.addView(row, i);
        }
    }

    View.OnClickListener myCheckHandler = new View.OnClickListener() {
        public void onClick(View v) {

            v.setVisibility(View.GONE);
            TableRow row = (TableRow) v;

            for (int x = 0; x < row.getChildCount(); x++) {
                View view = row.getChildAt(x);
                if (view instanceof TextView){
                    view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorAccent));
                    finalDate = ((TextView) view).getText().toString();
                }
            }
            send.setVisibility(View.VISIBLE);
        }
    };

    //WG and Tenant, show the final date
    public void showFinalDate(String finalDate){
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView dateText = new TextView(this);

        dateText.setText("Your appointment is: " + finalDate);
        dateText.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorAccent));

        row.addView(dateText);

        dateOverview.addView(row);
    }

    public void datesSuccessfulToTenants(){
        send.setEnabled(false);
        setDate.setEnabled(false);
    }

    private void bindView() {
        send = (Button) findViewById(R.id.send);
        setDate = (Button) findViewById(R.id.setDate);
        dateOverview = (TableLayout) findViewById(R.id.dateOverview);
    }


    @Override
    protected int getLayoutResourceId() {return R.layout.activity_calendar;}

    @Override
    public void showError(String message) {Toast.makeText(this, message, Toast.LENGTH_LONG).show();}

}
