package com.flatshare.presentation.ui.activities.matchingoverview.calender;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.presentation.presenters.chat.CalendarPresenter;
import com.flatshare.presentation.presenters.chat.impl.CalendarPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

public class CalendarActivity extends AbstractActivity implements CalendarPresenter.View {

    private CalendarPresenter mPresenter;
    private ImageButton couchChatButton;
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
    }


    private void bindView() {
        couchChatButton = (ImageButton)findViewById(R.id.couchChatBtn);
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
