package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.presentation.presenters.matching.MatchingPresenter;
import com.flatshare.presentation.presenters.matching.impl.MatchingPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.settings.ProfilSettingsActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.chat.ChatActivity;
import com.flatshare.threading.MainThreadImpl;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

/**
 * Created by Arber on 20/12/2016.
 */
public class MatchingActivity extends AbstractActivity implements MatchingPresenter.View {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private ImageButton acceptBtn;
    private ImageButton rejectBtn;
    private ImageButton profileBtn;
    private ImageButton chatBtn;
    private ImageButton cardViewInfoButton;

    private static final String TAG = "MatchingActivity";

    private MatchingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        Log.d(TAG, "inside onCreate(), creating presenter for this view");

        mPresenter = new MatchingPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        mPresenter.getMatches();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.activity_main_card_in)
                        .setSwipeOutMsgLayoutId(R.layout.activity_main_card_out));

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchingActivity.this.startActivity(new Intent(MatchingActivity.this, ProfilSettingsActivity.class));
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchingActivity.this.startActivity(new Intent(MatchingActivity.this, ChatActivity.class));
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        cardViewInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailedInfoCard();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void bindView() {
        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        profileBtn = (ImageButton) findViewById(R.id.profilsettingsBtn);
        chatBtn = (ImageButton) findViewById(R.id.chatBtn);

        rejectBtn = (ImageButton) findViewById(R.id.rejectBtn);
        acceptBtn = (ImageButton) findViewById(R.id.acceptBtn);

        cardViewInfoButton = (ImageButton) findViewById(R.id.cardViewInfoButton);
    }

    public void detailedInfoCard(){
        startActivity(new Intent(MatchingActivity.this, ShowDetailProfilApartmentActivity.class));
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
        Log.d(TAG, "inside showError: " + message);
    }

    @Override
    public void showTenants(List<TenantProfile> tenants) {
        //TODO: display tenants
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.activity_main_card_in)
                        .setSwipeOutMsgLayoutId(R.layout.activity_main_card_out));


        Log.d(TAG, "size of potential apartments: " + tenants.size());

        int i = 0;

        for (TenantProfile t : tenants){
            Log.d(TAG, "i: " + i++ + "\n" + t.toString());
        }

    }

    @Override
    public void showApartments(List<ApartmentProfile> apartments) {

        Log.d(TAG, "size of potential apartments: " + apartments.size());

        int i = 0;

        for (ApartmentProfile a : apartments){
            //Log.d(TAG, "i: " + i++ + "\n" + a.toString());
            mSwipeView.addView(new MatchingActivity_ProfileCard(mContext, a, mSwipeView));
        }
    }
}
