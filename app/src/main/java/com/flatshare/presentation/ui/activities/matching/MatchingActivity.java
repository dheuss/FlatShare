package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.presentation.presenters.matching.PotentialMatchingPresenter;
import com.flatshare.presentation.presenters.matching.impl.PotentialMatchingPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.settings.ProfileTenantSettingsActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.chat.ChatActivity;
import com.flatshare.threading.MainThreadImpl;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import android.view.ViewGroup.LayoutParams;


import java.util.List;

/**
 * Created by Arber on 20/12/2016.
 */
public class MatchingActivity extends AbstractActivity implements PotentialMatchingPresenter.View {

    private static final String EVENT_LISTENER_KEY = "eventListenerKey";
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private ImageButton acceptBtn;
    private ImageButton rejectBtn;
    private ImageButton profileBtn;
    private ImageButton chatBtn;

    private PopupWindow mPopupWindow;

    private FrameLayout mFrameLayout;

    private static final String TAG = "MatchingActivity";

    private PotentialMatchingPresenter mPresenter;

    private ApartmentProfile mApartmetProfile;
    private TenantProfile mTenantProfile;

    private int popupFlag; //0 == Apartment; 1 == Tenant
    private TextView apartmentPriceTextView;
    private TextView apartmentAreaTextView;
    private TextView apartmentZipCodeTextView;
    private TextView apartmentRoomSizeTextView;
    private TextView apartmentApartmentSizeTextView;
    private TextView apartmentInternetTextView;
    private TextView apartmentSmokerTextView;
    private TextView apartmentPetsTextView;
    private TextView apartmentWashingMashineTextView;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        Log.d(TAG, "inside onCreate(), creating presenter for this view");

        mPresenter = new PotentialMatchingPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        mPresenter.getPotentialMatches();

        boolean eventListenerExists = sharedPref.getBoolean(getResources().getString(R.string.pot_matching_listener_attached), false);

        if (!eventListenerExists) {
            mPresenter.setPotentialMatchesListener();
        }

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.activity_matching_card_in)
                        .setSwipeOutMsgLayoutId(R.layout.activity_matching_card_out));

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchingActivity.this.startActivity(new Intent(MatchingActivity.this, ProfileTenantSettingsActivity.class));
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
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_matching;
    }


    private void bindView() {
        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mFrameLayout = (FrameLayout) findViewById(R.id.mactchingActivityFrameLayout);

        profileBtn = (ImageButton) findViewById(R.id.profilsettingsBtn);
        chatBtn = (ImageButton) findViewById(R.id.chatBtn);

        rejectBtn = (ImageButton) findViewById(R.id.rejectBtn);
        acceptBtn = (ImageButton) findViewById(R.id.acceptBtn);

        popupFlag = 0;
    }

    public void cardClick(View view) {
        if (popupFlag == 0) {
            apartmentPopUp(view);
        } else {
            tenantPopUp(view);
        }
    }

    public void apartmentPopUp(View view) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.activity_show_detail_profil_apartment, null);

        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );

        apartmentPriceTextView = (TextView) findViewById(R.id.apartmentPriceTextView);
        apartmentAreaTextView = (TextView) findViewById(R.id.apartmentAreaTextView);
        apartmentZipCodeTextView = (TextView) findViewById(R.id.apartmentZIPTextView);
        apartmentRoomSizeTextView = (TextView) findViewById(R.id.apartmentRoomSizeTextView);
        apartmentApartmentSizeTextView = (TextView) findViewById(R.id.apartmentAreaTextView);
        apartmentInternetTextView = (TextView) findViewById(R.id.apartmentInternetTextView);
        apartmentSmokerTextView = (TextView) findViewById(R.id.apartmentSmokerTextView);
        apartmentPetsTextView = (TextView) findViewById(R.id.apartmentPetsTextView);
        apartmentWashingMashineTextView = (TextView) findViewById(R.id.apartmentWashingMashineTextView);
        closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

        //apartmentPriceTextView.setText(mApartmetProfile.getPrice() + "");

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                Log.v(TAG, "DISMISS");
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(mFrameLayout, Gravity.CENTER, 0, 0);
    }

    public void tenantPopUp(View view) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.activity_show_detail_profil_tenant, null);

        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );

        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                Log.v(TAG, "DISMISS");
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(mFrameLayout, Gravity.CENTER, 0, 0);
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
        Log.d(TAG, "size of potential apartments: " + tenants.size());
        popupFlag = 1;//set Flag for popUpView;
        int i = 0;

        for (TenantProfile t : tenants) {
            //Log.d(TAG, "i: " + i++ + "\n" + t.toString());
            mSwipeView.addView(new MatchingActivity_ProfileCard_Tenant(mContext, t, mSwipeView));
            mTenantProfile = t;
        }

    }

    @Override
    public void showApartments(List<ApartmentProfile> apartments) {
        Log.d(TAG, "size of potential apartments: " + apartments.size());
        popupFlag = 0;//set Flag for popUpView;
        int i = 0;

        for (ApartmentProfile a : apartments) {
            //Log.d(TAG, "i: " + i++ + "\n" + a.toString());
            mSwipeView.addView(new MatchingActivity_ProfileCard_Apartment(mContext, a, mSwipeView));
            mApartmetProfile = a;
        }
    }

    @Override
    public void updateListener(boolean listenerAttached) {
        writeToSharedPreferences(R.string.pot_matching_listener_attached, listenerAttached);
    }

    private void tenantSwipedApartment(ApartmentProfile apartmentProfile, boolean accepted) {
        mPresenter.tenantSwipedApartment(apartmentProfile.getId(), accepted);
    }

    private void roommateSwipedTenant(TenantProfile tenantProfile, boolean accepted) {
        mPresenter.roommateSwipedTenant(tenantProfile.getId(), accepted);
    }

}
