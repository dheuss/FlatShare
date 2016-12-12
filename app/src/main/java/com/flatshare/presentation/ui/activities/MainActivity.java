package com.flatshare.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.executor.impl.ThreadExecutor;
import com.flatshare.presentation.presenters.MainPresenter;
import com.flatshare.presentation.presenters.impl.MainPresenterImpl;
import com.flatshare.threading.MainThreadImpl;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {


    private static final String TAG = "MainActivity";

//    @Bind(R.id.welcome_textview)
    TextView mWelcomeTextView;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "inside onCreate(), calling ButterKnife.bind(this)");
//        ButterKnife.bind(this);

        bindView();
        Log.d(TAG, "inside onCreate(), creating presenter for this view");
        // create a presenter for this view
        mPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
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
    public void showProgress() {
        Log.d(TAG, "inside showProgress()");
        mWelcomeTextView.setText("Retrieving...");
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "inside hideProgress(), Toast.maxeText(this, \"Retrieved\", Toast.LENGTH_LONG).show();");
        Toast.makeText(this, "Retrieved!", Toast.LENGTH_LONG).show();
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
