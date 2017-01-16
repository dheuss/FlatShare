package com.flatshare.presentation.ui.activities.matchingoverview.calendar;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.flatshare.R;

import com.flatshare.presentation.presenters.matchingoverview.calendar.CalendarPresenter;
import com.flatshare.presentation.presenters.matchingoverview.calendar.impl.CalendarPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.chat.ChatActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarActivity extends AbstractActivity implements CalendarPresenter.View {

    private CalendarPresenter mPresenter;

    private ImageButton couchChatButton;
    private Button send, deleteButton1, deleteButton2, deleteButton3, deleteButton4, deleteButton5, deleteButton6, deleteButton7, deleteButton8, deleteButton9, deleteButton10;

    private int yearCurrent, monthCurrent, dayCurrent, hourCurrent, minCurrent;

    private List<String> dateList = new ArrayList<>();
    private List<String> timeList = new ArrayList<>();


    private static final String TAG = "CalenderAct";

    private int counter = -1;
    private TextView date1, date2, date3, date4, date5, date6, date7, date8, date9, date10;
    private TextView[] dateTextView = {};
    private Button[] dateButton = {};
    private int[] buttonView = {};
    private boolean tendant;
    private boolean deleteButtonToGONE = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new CalendarPresenterImpl(MainThreadImpl.getInstance(), this);

        tendant = mPresenter.checkForTendant();

        Calendar calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH);
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        hourCurrent = calendar.get(Calendar.HOUR_OF_DAY);
        minCurrent = calendar.get(Calendar.MINUTE);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        couchChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarActivity.this, ChatActivity.class));
            }
        });

        dateTextView = new TextView[] {date1, date2, date3, date4, date5, date6, date7, date8, date9, date10};
        dateButton = new Button[] {deleteButton1, deleteButton2, deleteButton3, deleteButton4, deleteButton5, deleteButton6, deleteButton7, deleteButton8, deleteButton9, deleteButton10};
        buttonView = new int[]{R.id.delete1,R.id.delete2,R.id.delete3,R.id.delete4,R.id.delete5,R.id.delete6,R.id.delete7,R.id.delete8,R.id.delete9,R.id.delete10};

        //Send Button
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO send dateList and timeList to firebase
                if (tendant){
                    //mPresenter.sendBackFromTendant(finalDate);
                }else {
                    mPresenter.send(dateList, timeList);
                    Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    //klick on delete Date
    public void handleClick(View view) {
        for(int i =0;i<=buttonView.length-1;i++){
            if(view.getId() == buttonView[i]) {
                if (tendant) {
                    chooseTendantDate(1);
                } else {
                    if (deleteButtonToGONE) {
                        deleteButtonToGONE = false;
                        deleteButton1.setVisibility(View.GONE);
                    } else {
                        deleteButtonPressed(1);
                    }
                }
            }
        }
    }

    private void deleteButtonPressed(int i) {
        counter--;
        for (int j = i; j <= dateTextView.length-1; j++){
            if (j == dateTextView.length-1){
                dateTextView[j].setText("");
                deleteButtonToGONE = true;
                dateButton[j].performClick();
            }else {
                dateTextView[j].setText(dateTextView[j + 1].getText());
                if (dateTextView[j +1].getText() == "") {
                    deleteButtonToGONE = true;
                    dateButton[j +1].performClick();
                    //dateButton[j +1].setVisibility(View.GONE);
                }
            }
        }
        dateList.remove(i);
        timeList.remove(i);
    }


    private void chooseTendantDate(int i) {
        //TODO markiere gewältes Datum - Sendbutton sichtbar und verschicken
        send.setVisibility(View.VISIBLE);
        //dateButton[i].setBackground();
    }

    private void showFinalDate(String finalDate){
        for (int i=0; i <= dateTextView.length-1; i++){
            dateTextView[i].setText("");
        }
        dateTextView[5].setText("Your appointment is: " + finalDate);
        dateTextView[4].setBackground(getResources().getDrawable(R.color.colorPrimary));
        dateTextView[6].setBackground(getResources().getDrawable(R.color.colorPrimary));
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        if (dateList.size() <= 10){
            showDialog(999);
            counter++;
            tendant = false;
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
            return datePicker;
        }else if(id == 888){
            TimePickerDialog timePicker = new TimePickerDialog(this, timePickerListener, hourCurrent, minCurrent, true);
            timePicker.updateTime(hourCurrent, minCurrent);
            return timePicker;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            showDate(year, month+1, day);
            showDialog(888);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            showTime(hour, min);
        }
    };


    private void showDate(int arg1, int arg2, int arg3) {
        dateList.add(arg3 + "/" + arg2 + "/" + arg1);
        timeList.add("");
        dateTextView[counter].setText(dateList.get(counter));
        dateButton[counter].setVisibility(View.VISIBLE);
    }

    private void showTime(int arg1, int arg2){
        timeList.add(counter, arg1 + ":" + arg2);
        dateTextView[counter].setText(dateList.get(counter) + " " + timeList.get(counter));
        dateButton[counter].setVisibility(View.VISIBLE);
    }

    public void showDatesForTendants(List<String> dateTendantList, List<String> timeTendantList){
        //TODO wird vom Presenter aufgerufen sobald Daten kommen
        for(int i = 0; i < dateTendantList.size() - 1; i++){
            dateTextView[i].setText(dateTendantList.get(i) + " " + timeTendantList.get(i));
            dateButton[i].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.checkmark));
            dateButton[i].setVisibility(View.VISIBLE);
            tendant = true;
        }
    }


    private void bindView() {
        couchChatButton = (ImageButton)findViewById(R.id.couchChatBtn);
        send = (Button) findViewById(R.id.send);
        deleteButton1 = (Button) findViewById(R.id.delete1);
        deleteButton2 = (Button) findViewById(R.id.delete2);
        deleteButton3 = (Button) findViewById(R.id.delete3);
        deleteButton4 = (Button) findViewById(R.id.delete4);
        deleteButton5 = (Button) findViewById(R.id.delete5);
        deleteButton6 = (Button) findViewById(R.id.delete6);
        deleteButton7 = (Button) findViewById(R.id.delete7);
        deleteButton8 = (Button) findViewById(R.id.delete8);
        deleteButton9 = (Button) findViewById(R.id.delete9);
        deleteButton10 = (Button) findViewById(R.id.delete10);
        date1 = (TextView) findViewById(R.id.date1);
        date2 = (TextView) findViewById(R.id.date2);
        date3 = (TextView) findViewById(R.id.date3);
        date4 = (TextView) findViewById(R.id.date4);
        date5 = (TextView) findViewById(R.id.date5);
        date6 = (TextView) findViewById(R.id.date6);
        date7 = (TextView) findViewById(R.id.date7);
        date8 = (TextView) findViewById(R.id.date8);
        date9 = (TextView) findViewById(R.id.date9);
        date10 = (TextView) findViewById(R.id.date10);
    }


    @Override
    protected int getLayoutResourceId() {return R.layout.activity_calendar;}

    @Override
    public void showError(String message) {Toast.makeText(this, message, Toast.LENGTH_LONG).show();}

}
