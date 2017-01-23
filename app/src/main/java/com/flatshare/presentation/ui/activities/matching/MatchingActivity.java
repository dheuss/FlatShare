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
import com.google.android.gms.vision.text.Text;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import android.view.ViewGroup.LayoutParams;

import java.util.List;

import static com.flatshare.R.drawable.apartment_default;
import static com.flatshare.R.drawable.thumb_down_icon;
import static com.flatshare.R.drawable.thumb_up_icon;

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
    private TextView apartmentSizeTextView;
    private TextView apartmentZipCodeTextView;
    private TextView apartmentCityTextView;
    private TextView apartmentStateTextView;
    private TextView apartmentCountryTextView;
    private ImageView apartmentImageView;
    private ImageView internetImageView;
    private ImageView smokerImageView;
    private ImageView petsImageView;
    private ImageView washingMashineImageView;
    private ImageView purposeImageView;

    private int apartmentPrice;
    private int apartmentSize;
    private int apartmentZipCode;
    private String apartmentCity;
    private String apartmentState;
    private String apartmentCountry;
    private Bitmap apartmentImage;

    private ImageButton closeButton;

    private Boolean internet;
    private Boolean smoker;
    private Boolean pets;
    private Boolean washingMashine;
    private Boolean purpose;

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

        mSwipeView.getBuilder()
                .setDisplayViewCount(1)
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

        apartmentPriceTextView = (TextView) customView.findViewById(R.id.apartmentPriceTextView);
        apartmentPriceTextView.setText(getApartmentPrice()+" €");
        apartmentSizeTextView = (TextView) customView.findViewById(R.id.apartmentSizeTextView);
        apartmentSizeTextView.setText(getApartmentSize()+" m2");
        apartmentZipCodeTextView = (TextView)customView.findViewById(R.id.apartmentZIPCODETextView);
        apartmentZipCodeTextView.setText(getApartmentZipCode()+"");
        apartmentCityTextView = (TextView)customView.findViewById(R.id.apartmentCITYTextView);
        apartmentCityTextView.setText(getApartmentCity());
        apartmentStateTextView = (TextView)customView.findViewById(R.id.apartmentSTATETextView);
        apartmentStateTextView.setText(getApartmentState());
        apartmentCountryTextView = (TextView)customView.findViewById(R.id.apartmentCOUNTRYTextView);
        apartmentCountryTextView.setText(getApartmentCountry());
        apartmentImageView = (ImageView) customView.findViewById(R.id.apartmentInfoImageView);
        if (getApartmentImage() == null){
            apartmentImageView.setImageResource(apartment_default);
        } else {
            apartmentImageView.setImageBitmap(getApartmentImage());
        }
        internetImageView  = (ImageView) customView.findViewById(R.id.internetThumb);
        if (getInternet()){
            internetImageView.setImageResource(thumb_up_icon);
        }else{
            internetImageView.setImageResource(thumb_down_icon);
        }
        smokerImageView = (ImageView) customView.findViewById(R.id.smokerThumb);
        if (getSmoker()){
            smokerImageView.setImageResource(thumb_up_icon);
        } else {
            smokerImageView.setImageResource(thumb_down_icon);
        }
        petsImageView = (ImageView) customView.findViewById(R.id.petsThumb);
        if (getPets()){
            petsImageView.setImageResource(thumb_up_icon);
        } else {
            petsImageView.setImageResource(thumb_down_icon);
        }
        washingMashineImageView = (ImageView) customView.findViewById(R.id.washingMashineThumb);
        if (getWashingMashine()){
            washingMashineImageView.setImageResource(thumb_up_icon);
        } else {
            washingMashineImageView.setImageResource(thumb_down_icon);
        }
        purposeImageView = (ImageView) customView.findViewById(R.id.purpseThumb);

        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );

        closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        //Todo Google Maps

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
            mSwipeView.addView(new MatchingActivity_ProfileCard_Tenant(this, tenantProfile, mSwipeView, bitmap));
        }
    }

    @Override
    public void showApartments(List<Pair<ApartmentProfile, Bitmap>> apartments) {
        Log.d(TAG, "size of potential apartments: " + apartments.size());

        for (Pair<ApartmentProfile, Bitmap> pair : apartments) {
            ApartmentProfile apartmentProfile = pair.getLeft();
            Bitmap bitmap = pair.getRight();
            mSwipeView.addView(new MatchingActivity_ProfileCard_Apartment(this, apartmentProfile, mSwipeView, bitmap));
        }
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

    public int getApartmentPrice() {
        return apartmentPrice;
    }

    public void setApartmentPrice(int apartmentPrice) {
        this.apartmentPrice = apartmentPrice;
    }

    public int getApartmentSize() {
        return apartmentSize;
    }

    public void setApartmentSize(int apartmentSize) {
        this.apartmentSize = apartmentSize;
    }

    public int getApartmentZipCode() {
        return apartmentZipCode;
    }

    public void setApartmentZipCode(int apartmentZipCode) {
        this.apartmentZipCode = apartmentZipCode;
    }

    public String getApartmentCity() {
        return apartmentCity;
    }

    public void setApartmentCity(String apartmentCity) {
        this.apartmentCity = apartmentCity;
    }

    public String getApartmentState() {
        return apartmentState;
    }

    public void setApartmentState(String apartmentState) {
        this.apartmentState = apartmentState;
    }

    public String getApartmentCountry() {
        return apartmentCountry;
    }

    public void setApartmentCountry(String apartmentCountry) {
        this.apartmentCountry = apartmentCountry;
    }

    public Bitmap getApartmentImage() {
        return apartmentImage;
    }

    public void setApartmentImage(Bitmap apartmentImage) {
        this.apartmentImage = apartmentImage;
    }

    public Boolean getInternet() {
        return internet;
    }

    public void setInternet(Boolean internet) {
        this.internet = internet;
    }

    public Boolean getSmoker() {
        return smoker;
    }

    public void setSmoker(Boolean smoker) {
        this.smoker = smoker;
    }

    public Boolean getPets() {
        return pets;
    }

    public void setPets(Boolean pets) {
        this.pets = pets;
    }

    public Boolean getWashingMashine() {
        return washingMashine;
    }

    public void setWashingMashine(Boolean washingMashine) {
        this.washingMashine = washingMashine;
    }

    public Boolean getPurpose() {
        return purpose;
    }

    public void setPurpose(Boolean purpose) {
        this.purpose = purpose;
    }
}
