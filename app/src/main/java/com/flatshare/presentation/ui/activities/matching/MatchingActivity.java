package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.matching.PotentialMatchingPresenter;
import com.flatshare.presentation.presenters.matching.impl.PotentialMatchingPresenterImpl;
import com.flatshare.presentation.ui.AbstractFragmentActivity;
import com.flatshare.threading.MainThreadImpl;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import android.view.ViewGroup.LayoutParams;

import java.util.List;

public class MatchingActivity extends AbstractFragmentActivity implements PotentialMatchingPresenter.View {

    private static final String TAG = "MatchingActivity";

    private SwipePlaceHolderView mSwipeView;
//    private Context mContext;
    private UserState userState;

    private ImageButton acceptBtn;
    private ImageButton rejectBtn;
    private ImageButton infoBtn;

    private TextView badgeCounter;
    private Button badgeIcon;

    private PopupWindow mPopupWindow;

    private FrameLayout mFrameLayout;

    private PotentialMatchingPresenter mPresenter;

    private List<ApartmentProfile> apartmentProfiles;
    private List<TenantProfile> tenantProfiles;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matching, container, false);

        sharedPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

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

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardClick(view);
            }
        });

        return view;
    }


    private void bindView(View view) {
        mSwipeView = (SwipePlaceHolderView) view.findViewById(R.id.swipeView);
//        this.mContext = getActivity();

        mFrameLayout = (FrameLayout) view.findViewById(R.id.matchingActivityFrameLayout);

        rejectBtn = (ImageButton) view.findViewById(R.id.rejectBtn);
        infoBtn = (ImageButton) view.findViewById(R.id.infoBtn);
        acceptBtn = (ImageButton) view.findViewById(R.id.acceptBtn);
    }

    public void cardClick(View view) {
        if (userState.getPrimaryUserProfile().getClassificationId() == ProfileType.TENANT.getValue()) {
            apartmentPopUp(view);
        } else {
            tenantPopUp(view);
        }
    }

    public void apartmentPopUp(View view) {
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

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "DISMISS");
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(mFrameLayout, Gravity.CENTER, 0, 0);
    }

    public void tenantPopUp(View view) {
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
        mPresenter.resume();
    }

    @Override
    public void showError(String message) {
        Log.d(TAG, "inside showError: " + message);
    }

    @Override
    public void showTenants(List<Pair<TenantProfile, Bitmap>> tenants) {
        Log.d(TAG, "size of potential apartments: " + tenants.size());

        for (Pair<TenantProfile, Bitmap> pair : tenants) {
            TenantProfile tenantProfile = pair.getLeft();
            Bitmap bitmap = pair.getRight();
            //TODO: get ImageView and show it, IF BITMAP IS NULL THEN PICK SOME DEFAULT IMAGE (apartment/ tenant (male/female))
            mSwipeView.addView(new MatchingActivity_ProfileCard_Tenant(this, tenantProfile, mSwipeView));
        }

    }

    @Override
    public void showApartments(List<Pair<ApartmentProfile, Bitmap>> apartments) {
        Log.d(TAG, "size of potential apartments: " + apartments.size());

        for (Pair<ApartmentProfile, Bitmap> pair : apartments) {
            ApartmentProfile apartmentProfile = pair.getLeft();
            Bitmap bitmap = pair.getRight();
            //TODO: get ImageView and show it
            mSwipeView.addView(new MatchingActivity_ProfileCard_Apartment(this, apartmentProfile, mSwipeView));
        }
    }

    @Override
    public void updateListener(boolean listenerAttached) {
        //writeToSharedPreferences(R.string.pot_matching_listener_attached, listenerAttached);
    }

    public void tenantSwipedApartment(ApartmentProfile apartmentProfile, boolean accepted) {
        mPresenter.tenantSwipedApartment(apartmentProfile.getId(), accepted);
    }

    public void roommateSwipedTenant(TenantProfile tenantProfile, boolean accepted) {
        mPresenter.roommateSwipedTenant(tenantProfile.getId(), accepted);
    }

    protected void writeToSharedPreferences(int key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(key), value);
        editor.apply();
    }

}
