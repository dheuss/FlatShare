package com.flatshare.presentation.ui.activities.matchingoverview.calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.flatshare.presentation.ui.activities.MainActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.MatchingOverviewActivity;
import com.flatshare.threading.MainThreadImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarActivity extends AbstractActivity implements CalendarPresenter.View {

    private CalendarPresenter mPresenter;

    private FloatingActionButton setDate;
    private Button send;

    private int yearCurrent, monthCurrent, dayCurrent, hourCurrent, minCurrent;

    private List<String> dateList = new ArrayList<>();
    private List<String> timeList = new ArrayList<>();

    private TableLayout dateOverview;

    private static final String TAG = "CalenderAct";

    private boolean isTenant;
    private String finalDate, tenantID, apartmentID;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new CalendarPresenterImpl(MainThreadImpl.getInstance(), this);

        isTenant = mPresenter.checkForTenant();

        if (isTenant) {
            setDate.setVisibility(View.GONE);
        }

        Calendar calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH);
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        hourCurrent = calendar.get(Calendar.HOUR_OF_DAY);
        minCurrent = calendar.get(Calendar.MINUTE);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        tenantID = getIntent().getStringExtra(MatchingOverviewActivity.TenantSessionId);
        apartmentID = getIntent().getStringExtra(MatchingOverviewActivity.ApartmentSessionId);

        mPresenter.checkForAppointment(tenantID, apartmentID);
        mPresenter.checkFinalAppointment(tenantID, apartmentID);

        //Send Button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(CalendarActivity.this);
                View mView = layoutInflater.inflate(R.layout.activity_popup, null);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(CalendarActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                final TextView popUpTextView = (TextView) mView.findViewById(R.id.popup_TextView);
                popUpTextView.setText("Do you really want to send?");

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (isTenant) {
                                    mPresenter.sendBackFromTenant(finalDate, tenantID, apartmentID);
                                } else {
                                    mPresenter.sendDateToTenant(dateList, timeList, tenantID, apartmentID);
                                    Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        if (dateList.size() <= 20) {
            showDialog(999);
            send.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getApplicationContext(), "Reach the maximum of Dates!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            DatePickerDialog datePicker = new DatePickerDialog(this, datePickerListener, yearCurrent, monthCurrent, dayCurrent);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        //Nothing
                    }
                }
            });
            return datePicker;
        } else if (id == 888) {
            TimePickerDialog timePicker = new TimePickerDialog(this, timePickerListener, hourCurrent, minCurrent, true);
            timePicker.updateTime(hourCurrent, minCurrent);
            timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
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
            dateList.add("" + (day < 10 ? '0' : "") + day + "/" + (month < 10 ? '0' : "") + month + "/" + year);
            showDialog(888);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            timeList.add(" " + (hour < 10 ? '0' : "") + hour + ":" + (min < 10 ? '0' : "") + min);
            showOnScreen();
        }
    };

    public void showOnScreen() {

        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView dateText = new TextView(this);
        ImageButton deleteButton = new ImageButton(this);

        dateText.setText(dateList.get(dateList.size() - 1) + " " + timeList.get(timeList.size() - 1));

        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.clear_icon));
        deleteButton.setOnClickListener(myDeleteHandler);

        row.addView(dateText);
        row.addView(deleteButton);

        TableRow.LayoutParams paramsText = (TableRow.LayoutParams) dateText.getLayoutParams();
        paramsText.setMargins(10, 0, 10, 0);
        paramsText.weight = 1;
        paramsText.width = TableRow.LayoutParams.WRAP_CONTENT;
        paramsText.gravity = Gravity.CENTER;
        dateText.setLayoutParams(paramsText);

        TableRow.LayoutParams paramsDelete = (TableRow.LayoutParams) deleteButton.getLayoutParams();
        paramsDelete.setMargins(0, 0, 0, 0);
        paramsDelete.gravity = Gravity.RIGHT;
        deleteButton.setLayoutParams(paramsDelete);

        dateOverview.addView(row, dateList.size() - 1);

    }

    View.OnClickListener myDeleteHandler = new View.OnClickListener() {
        public void onClick(View v) {

            for (int i = 0; i < dateOverview.getChildCount(); i++) {
                View view = dateOverview.getChildAt(i);

                if (i < dateOverview.getChildCount() - 1) {
                    View viewNext = dateOverview.getChildAt(i + 1);

                    if (view instanceof TableRow) {
                        TableRow row = (TableRow) view;
                        TableRow rowNext = (TableRow) viewNext;

                        for (int j = 0; j < row.getChildCount(); j++) {
                            View elementView = row.getChildAt(j);

                            if (elementView instanceof ImageButton) {
                                ImageButton currentButton = (ImageButton) elementView;

                                View buttonViewNext = rowNext.getChildAt(1);
                                ImageButton buttonNext = (ImageButton) buttonViewNext;

                                View textViewNext = rowNext.getChildAt(0);
                                TextView nextDateText = (TextView) textViewNext;

                                View textView = row.getChildAt(0);
                                TextView currentDateText = (TextView) textView;

                                if (currentButton == v) {
                                    dateList.remove(i);
                                    timeList.remove(i);
                                    currentDateText.setText(nextDateText.getText());
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

                            if (elementView instanceof ImageButton) {
                                ImageButton currentButton = (ImageButton) elementView;

                                View textView = row.getChildAt(0);
                                TextView currentDateText = (TextView) textView;

                                if (currentButton == v) {
                                    dateList.remove(i);
                                    timeList.remove(i);
                                    currentDateText.setText("");
                                    currentButton.setVisibility(View.GONE);
                                    row.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }
        }
    };


    //Tenant set Dates, show Tenant which dates send WG
    public void setDatesFromWG(List<String> dateList, List<String> timeList) {
        for (int i = 0; i < dateList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView dateText = new TextView(this);
            ImageButton checkButton = new ImageButton(this);

            dateText.setText(dateList.get(i) + " " + timeList.get(i));

            if (!isTenant) checkButton.setVisibility(View.GONE);
            if (isTenant) {
                checkButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.done_icon));
                checkButton.setOnClickListener(myCheckHandler);
            }
            row.addView(dateText);
            row.addView(checkButton);

            TableRow.LayoutParams paramsText = (TableRow.LayoutParams) dateText.getLayoutParams();
            paramsText.setMargins(10, 0, 10, 0);
            paramsText.weight = 1;
            paramsText.width = TableRow.LayoutParams.WRAP_CONTENT;
            paramsText.gravity = Gravity.CENTER;
            dateText.setLayoutParams(paramsText);

            TableRow.LayoutParams paramsCheck = (TableRow.LayoutParams) checkButton.getLayoutParams();
            paramsCheck.setMargins(0, 0, 0, 0);
            paramsCheck.gravity = Gravity.RIGHT;
            checkButton.setLayoutParams(paramsCheck);

            dateOverview.addView(row, i);
        }
        send.setVisibility(View.VISIBLE);
        if (!isTenant) {
            send.setEnabled(false);
            setDate.setEnabled(false);
            setDate.setAlpha(0.5f);
            send.setAlpha(0.5f);
        } else {
            setDate.setVisibility(View.GONE);
        }
    }

    View.OnClickListener myCheckHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d(TAG, "onClick: You pressed me");
            for (int i = 0; i < dateOverview.getChildCount(); i++) {
                View view = dateOverview.getChildAt(i);

                if (view instanceof TableRow) {
                    TableRow row = (TableRow) view;

                    for (int j = 0; j < row.getChildCount(); j++) {
                        View elementView = row.getChildAt(j);

                        if (elementView instanceof ImageButton) {
                            ImageButton currentButton = (ImageButton) elementView;

                            View textView = row.getChildAt(0);
                            TextView currentText = (TextView) textView;

                            row.setBackground(ContextCompat.getDrawable(getApplicationContext(), android.R.color.transparent));

                            if (currentButton == v) {
                                if (currentButton == v) {
                                    row.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorAccent));
                                    finalDate = currentText.getText().toString();
                                    send.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    //WG and Tenant, show the final date
    public void showFinalDate(String finalDate) {
        if (isTenant) {
            setDate.setVisibility(View.GONE);
            send.setEnabled(false);
            setDate.setEnabled(false);
            setDate.setAlpha(0.5f);
            send.setAlpha(0.5f);
        } else {
            send.setVisibility(View.VISIBLE);
            send.setEnabled(false);
            setDate.setEnabled(false);
            setDate.setAlpha(0.5f);
            send.setAlpha(0.5f);
        }
        dateOverview.setColumnStretchable(0, true);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView dateText = new TextView(this);

        dateText.setText("Your appointment is: " + finalDate);
        dateText.setTypeface(null, Typeface.BOLD_ITALIC);
        dateText.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorAccent));

        row.addView(dateText);

        TableRow.LayoutParams paramsText = (TableRow.LayoutParams) dateText.getLayoutParams();
        paramsText.setMargins(10, 0, 10, 0);
        paramsText.gravity = Gravity.CENTER;
        paramsText.width = TableRow.LayoutParams.MATCH_PARENT;
        dateText.setLayoutParams(paramsText);

        dateOverview.addView(row);
    }

    public void datesSuccessfulToTenants() {
        send.setEnabled(false);
        setDate.setEnabled(false);
        setDate.setAlpha(0.5f);
        send.setAlpha(0.5f);
        finish();
//        startActivity(new Intent(CalendarActivity.this, MainActivity.class));
    }

    private void bindView() {
        send = (Button) findViewById(R.id.send);
        setDate = (FloatingActionButton) findViewById(R.id.setDate);
        dateOverview = (TableLayout) findViewById(R.id.dateOverview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_cal);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dateOverview.removeAllViews();
                mPresenter.checkFinalAppointment(tenantID, apartmentID);
                mPresenter.checkForAppointment(tenantID, apartmentID);
            }
        });
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        swipeRefreshLayout.setRefreshing(false);
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
