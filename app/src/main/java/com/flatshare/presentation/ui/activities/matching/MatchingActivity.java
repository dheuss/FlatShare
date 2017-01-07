package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.presentation.presenters.matching.MatchingPresenter;
import com.flatshare.presentation.presenters.matching.impl.MatchingPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.settings.ProfilSettingsActivity;
import com.flatshare.presentation.ui.activities.chat.ChatActivity;
import com.flatshare.threading.MainThreadImpl;
import com.flatshare.utils.random.MainActivity_ProfileCard;
import com.flatshare.utils.random.Profile;
import com.flatshare.utils.random.Utils;
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


        // TODO: TEST

        mPresenter.getMatches();

        //

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.activity_main_card_in)
                        .setSwipeOutMsgLayoutId(R.layout.activity_main_card_out));

        for (Profile profile : Utils.loadProfiles(this.getApplicationContext())) {
            mSwipeView.addView(new MainActivity_ProfileCard(mContext, profile, mSwipeView));
        }

        profileBtn.setOnClickListener(v -> startActivity(new Intent(MatchingActivity.this, ProfilSettingsActivity.class)));

        chatBtn.setOnClickListener(v -> startActivity(new Intent(MatchingActivity.this, ChatActivity.class)));

        rejectBtn.setOnClickListener(v -> mSwipeView.doSwipe(false));

        acceptBtn.setOnClickListener(v -> mSwipeView.doSwipe(true));
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
    public void showTenants(List<TenantUserProfile> tenants) {
        //TODO: display tenants
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.activity_main_card_in)
                        .setSwipeOutMsgLayoutId(R.layout.activity_main_card_out));


        Log.d(TAG, "size of potential apartments: " + tenants.size());

//        int i = 0;
//
//        for (TenantUserProfile t : tenants){
//            Log.d(TAG, "i: " + i++ + "\n" + t.toString());
//        }

    }

    @Override
    public void showApartments(List<ApartmentUserProfile> apartments) {

        Log.d(TAG, "size of potential apartments: " + apartments.size());

//        int i = 0;
//
//        for (ApartmentUserProfile a : apartments){
//            Log.d(TAG, "i: " + i++ + "\n" + a.toString());
//        }
    }
}
