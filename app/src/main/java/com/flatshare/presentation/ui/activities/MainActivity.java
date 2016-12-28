package com.flatshare.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.presentation.presenters.MainPresenter;
import com.flatshare.presentation.presenters.impl.MainPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;
import com.flatshare.utils.random.MainActivity_ProfileCard;
import com.flatshare.utils.random.Profile;
import com.flatshare.utils.random.Utils;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import static com.flatshare.R.id.rejectBtn;
import static com.flatshare.R.id.settingsBtn;

public class MainActivity extends AbstractActivity implements MainPresenter.View {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private ImageButton acceptBtn;
    private ImageButton rejectBtn;
    private ImageButton settingsBtn;
    private ImageButton chatBtn;

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

        for(Profile profile : Utils.loadProfiles(this.getApplicationContext())){
            mSwipeView.addView(new MainActivity_ProfileCard(mContext, profile, mSwipeView));
        }

        settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
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

        settingsBtn = (ImageButton)findViewById(R.id.settingsBtn);
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
