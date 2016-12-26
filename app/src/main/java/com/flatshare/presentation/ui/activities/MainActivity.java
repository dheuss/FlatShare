package com.flatshare.presentation.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.presentation.presenters.MainPresenter;
import com.flatshare.presentation.presenters.impl.MainPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class MainActivity extends AbstractActivity implements MainPresenter.View {


    private static final String TAG = "MainActivity";

    //    @Bind(R.id.welcome_textview)
    TextView mWelcomeTextView;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);

        bindView();
        Log.d(TAG, "inside onCreate(), creating presenter for this view");
        // create a presenter for this view
        mPresenter = new MainPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void bindView() {
        mWelcomeTextView = (TextView) findViewById(R.id.welcome_textview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "inside onResume()");

        // let's start welcome message retrieval when the app resumes
        mPresenter.resume();
    }

    @Override
    public void showError(String message) {
        Log.d(TAG, "inside showError(String message)");
        mWelcomeTextView.setText(message);
    }

    @Override
    public void displayWelcomeMessage(String msg) {
        Log.d(TAG, "inside displayWelcomeMessage(String msg)");
        mWelcomeTextView.setText(msg);
    }
}
