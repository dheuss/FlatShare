package com.flatshare.presentation.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.presentation.presenters.MainPresenter;
import com.flatshare.presentation.presenters.impl.MainPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class MainActivity extends AbstractActivity implements MainPresenter.View {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private static final String TAG = "MainActivity";

    TextView mWelcomeTextView;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        Log.d(TAG, "inside onCreate(), creating presenter for this view");
        mPresenter = new MainPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                    .setPaddingTop(20)
                    .setRelativeScale(0.01f)
                    .setSwipeInMsgLayoutId(R.layout.activity_main_card_in)
                    .setSwipeOutMsgLayoutId(R.layout.activity_main_card_out));

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mSwipeView.doSwipe(true);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void bindView() {
        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();
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
