package com.flatshare.presentation.ui.activities.matchingoverview.calendar;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new CalendarPresenterImpl(MainThreadImpl.getInstance(), this);

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

        //setButtonOnDelete(999);

        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO send dateList and timeList to firebase
                mPresenter.send(dateList, timeList);
                Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_SHORT).show();
            }
        });



        /*
        deleteButton1.setOnClickListener(myDeleteButtonHandler);
        deleteButton2.setOnClickListener(myDeleteButtonHandler);
        deleteButton3.setOnClickListener(myDeleteButtonHandler);
        deleteButton4.setOnClickListener(myDeleteButtonHandler);
        deleteButton5.setOnClickListener(myDeleteButtonHandler);
        deleteButton6.setOnClickListener(myDeleteButtonHandler);
        deleteButton7.setOnClickListener(myDeleteButtonHandler);
        deleteButton8.setOnClickListener(myDeleteButtonHandler);
        deleteButton9.setOnClickListener(myDeleteButtonHandler);
        deleteButton10.setOnClickListener(myDeleteButtonHandler);

        deleteButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(0);
            }
        });

        deleteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(1);
            }
        });

        deleteButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(2);
            }
        });

        deleteButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(3);
            }
        });

        deleteButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(4);
            }
        });

        deleteButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(5);
            }
        });

        deleteButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(6);
            }
        });

        deleteButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(7);
            }
        });

        deleteButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(8);
            }
        });

        deleteButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonPressed(9);
            }
        });
    */

    }

    /*
    private View.OnClickListener myDeleteButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            for(int i =0;i<=buttonView.length-1;i++){
                if(v.getId() == buttonView[i]){
                    deleteButtonPressed(i, v);
                }
            }
        }
    };
    */

    public void handleClick(View view) {
        for(int i =0;i<=buttonView.length-1;i++){
            if(view.getId() == buttonView[i]){
                deleteButtonPressed(i, view);
            }
        }
    }

    private void deleteButtonPressed(int i, View v) {
        counter--;
        for (int j = i; j <= dateTextView.length-1; j++){
            if (j == dateTextView.length-1){
                dateTextView[j].setText("");
                //dateButton[j].setVisibility(dateButton[2].getRootView().getGONE);
            }else {
                dateTextView[j].setText(dateTextView[j + 1].getText());
                if (dateTextView[j + 1].getText() == "") {
                    //dateButton[j + 1].setVisibility(dateButton[2].getRootView().GONE);
                }
            }
        }
        dateList.remove(i);
        timeList.remove(i);
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        if (dateList.size() <= 10){
            showDialog(999);
            counter++;
        }else{
            Toast.makeText(getApplicationContext(), "Reach the maximum of Dates!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
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
            // TODO Auto-generated method stub
            showDate(year, month+1, day);
            showDialog(888);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            // TODO Auto-generated method stub
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
