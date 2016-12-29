package com.flatshare.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.presentation.presenters.MainPresenter;
import com.flatshare.presentation.presenters.impl.MainPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;
import com.flatshare.utils.random.MainActivity_ProfileCard;
import com.flatshare.utils.random.Profile;
import com.flatshare.utils.random.Utils;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

/**
 * Created by Arber on 20/12/2016.
 */
public class MatchingActivity extends AbstractActivity implements MainPresenter.View {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private ImageButton acceptBtn;
    private ImageButton rejectBtn;
    private ImageButton profilBtn;
    private ImageButton chatBtn;

    private static final String TAG = "MatchingActivity";

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

        for(Profile profile : Utils.loadProfiles(this.getApplicationContext())){
            mSwipeView.addView(new MainActivity_ProfileCard(mContext, profile, mSwipeView));
        }

        profilBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MatchingActivity.this, ProfilSettingsActivity.class));
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MatchingActivity.this, ChatActivity.class));
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mSwipeView.doSwipe(false);
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener(){
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

        profilBtn = (ImageButton)findViewById(R.id.profilsettingsBtn);
        chatBtn = (ImageButton)findViewById(R.id.chatBtn);

        rejectBtn = (ImageButton)findViewById(R.id.rejectBtn);
        acceptBtn = (ImageButton)findViewById(R.id.acceptBtn);
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
