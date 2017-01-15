package com.flatshare.presentation.ui.activities.matching;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.matching.PotentialMatchingPresenter;
import com.flatshare.presentation.presenters.matching.impl.PotentialMatchingPresenterImpl;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;
import com.flatshare.threading.MainThreadImpl;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import android.view.ViewGroup.LayoutParams;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Arber on 20/12/2016.
 */
public class MatchingActivity extends AbstarctFragmentAcivity implements PotentialMatchingPresenter.View {

    private static final String TAG = "MatchingActivity";

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private UserState userState;

    private int classificationId;
    private ImageButton acceptBtn;
    private ImageButton rejectBtn;
    private ImageButton profileBtn;

    private ImageButton chatBtn;

    private TextView badgeCounter;
    private Button badgeIcon;

    private PopupWindow mPopupWindow;

    private FrameLayout mFrameLayout;

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

    private SharedPreferences sharedPref;

    public MatchingActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matching, container, false);

        sharedPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        classificationId = userState.getPrimaryUserProfile().getClassificationId();

        bindView(view);

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

        return view;
    }


    private void bindView(View view) {
        mSwipeView = (SwipePlaceHolderView) view.findViewById(R.id.swipeView);
        this.mContext = getActivity();

        mFrameLayout = (FrameLayout) view.findViewById(R.id.matchingActivityFrameLayout);

        rejectBtn = (ImageButton) view.findViewById(R.id.rejectBtn);
        acceptBtn = (ImageButton) view.findViewById(R.id.acceptBtn);

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
        //LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        //View customView = inflater.inflate(R.layout.activity_show_detail_profil_apartment, null);
        View customView = getActivity().getLayoutInflater().inflate(R.layout.activity_show_detail_profil_apartment, null);

        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );

        apartmentPriceTextView = (TextView) view.findViewById(R.id.apartmentPriceTextView);
        apartmentAreaTextView = (TextView) view.findViewById(R.id.apartmentAreaTextView);
        apartmentZipCodeTextView = (TextView) view.findViewById(R.id.apartmentZIPTextView);
        apartmentRoomSizeTextView = (TextView) view.findViewById(R.id.apartmentRoomSizeTextView);
        apartmentApartmentSizeTextView = (TextView) view.findViewById(R.id.apartmentAreaTextView);
        apartmentInternetTextView = (TextView) view.findViewById(R.id.apartmentInternetTextView);
        apartmentSmokerTextView = (TextView) view.findViewById(R.id.apartmentSmokerTextView);
        apartmentPetsTextView = (TextView) view.findViewById(R.id.apartmentPetsTextView);
        apartmentWashingMashineTextView = (TextView) view.findViewById(R.id.apartmentWashingMashineTextView);
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
        //LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        //View customView = inflater.inflate(R.layout.activity_show_detail_profil_tenant, null);
        View customView = getActivity().getLayoutInflater().inflate(R.layout.activity_show_detail_profil_tenant, null);

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
    public void onResume() {
        super.onResume();

        Log.d(TAG, "inside onResume()");

        // let's start welcome message retrieval when the app resumes
        mPresenter.resume();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
        //writeToSharedPreferences(R.string.pot_matching_listener_attached, listenerAttached);
    }

    private void tenantSwipedApartment(ApartmentProfile apartmentProfile, boolean accepted) {
        mPresenter.tenantSwipedApartment(apartmentProfile.getId(), accepted);
    }

    private void roommateSwipedTenant(TenantProfile tenantProfile, boolean accepted) {
        mPresenter.roommateSwipedTenant(tenantProfile.getId(), accepted);
    }

    protected void writeToSharedPreferences(int key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(key), value);
        editor.apply();
    }

}
