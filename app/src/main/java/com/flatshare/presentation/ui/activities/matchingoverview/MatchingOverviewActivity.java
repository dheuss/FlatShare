package com.flatshare.presentation.ui.activities.matchingoverview;


import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.flatshare.R;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;
import com.flatshare.presentation.presenters.matchingoverview.impl.MatchingOverviewPresenterImpl;
import com.flatshare.presentation.ui.AbstractFragmentActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.calendar.CalendarActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import static com.flatshare.R.drawable.apartment_default;
import static com.flatshare.R.drawable.female_icon;
import static com.flatshare.R.drawable.male_icon;
import static com.flatshare.R.drawable.tenant_default;
import static com.flatshare.R.drawable.thumb_down_icon;
import static com.flatshare.R.drawable.thumb_up_icon;
import static com.flatshare.R.id.overview_linear_1;

public class MatchingOverviewActivity extends AbstractFragmentActivity implements MatchingOverviewPresenter.View {

    private UserState userState;

    private SwipeRefreshLayout swipeRefreshLayout;

    //ApartmentPopUp
    private TextView apartmentPriceTextView, apartmentSizeTextView, apartmentZipCodeTextView, apartmentCityTextView, apartmentStateTextView, apartmentCountryTextView, apartmentEmailTextView;
    private ImageView apartmentImageView, internetImageView, smokerImageView, petsImageView, washingMashineImageView, purposeImageView;
    private int apartmentPrice, apartmentSize, apartmentZipCode;
    private String apartmentCity, apartmentState, apartmentCountry;
    private Bitmap apartmentImage;
    private Boolean internet, smoker, pets, washingMashine, purpose;

    //TenantPopUp
    private TextView tenantNameTextView, tenantAgeTextView, tenantOccupationTextView, tenantInfoTextView, tenantEmailTextView;
    private ImageView tenantGenderImageView, tenantSmokerImageView, tenantPetsImageView, tenantImageView;
    private String tenantName, tenantOccupation, tenantInfo;
    private int tenantAge;
    private Bitmap tenantImage;
    private int tenantGender;
    private Boolean tenantSmoker, tenantPets;

    private PopupWindow mPopupWindow;
    private FrameLayout mFrameLayout;
    private ImageButton closeButton;


    public static final String TenantSessionId = "TenantSessionId";
    public static final String ApartmentSessionId = "ApartmentSessionId";

    private static final String TAG = "MatchingOverviewActivity";

    private MatchingOverviewPresenter mPresenter;
    private TableLayout matchingOverview;

    List<Bitmap> matchingTenantBitmap = new ArrayList<>();
    List<TenantProfile> matchingTenantProfile = new ArrayList<>();
    List<ApartmentProfile> matchingApartmentProfile = new ArrayList<>();
    List<Bitmap> matchingApartmentBitmap = new ArrayList<>();
    private Context mContext;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matching_overview, container, false);

        mContext = getContext();

        bindView(view);

        Log.d(TAG, "inside onCreate(), creating presenter for this view");

        mPresenter = new MatchingOverviewPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        mPresenter.getMatches();

        return view;
    }


    public void generateMatchingOverview(List<TenantProfile> matchingTenantList, List<ApartmentProfile> matchingApartmentList, List<Bitmap> matchingBitmapList) {


        List<String> matchingProfileList = new ArrayList<>();
        List<String> matchingIdList = new ArrayList<>();
        if (matchingApartmentList != null) {
            for (int i = 0; i < matchingApartmentList.size(); i++) {
                matchingProfileList.add(matchingApartmentList.get(i).getApartmentLocation().getStreet() + " " + matchingApartmentList.get(i).getPrice() + " €");
                matchingIdList.add(matchingApartmentList.get(i).getId());
            }
        } else {
            for (int i = 0; i < matchingTenantList.size(); i++) {
                matchingProfileList.add(matchingTenantList.get(i).getFirstName() + " ");
                matchingIdList.add(matchingTenantList.get(i).getId());
            }
        }

        for (int i = 0; i < matchingProfileList.size(); i++) {

            TableRow row = new TableRow(this.getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);

            TextView matchingText = new TextView(this.getActivity());
            ImageView matchingImage = new ImageView(this.getActivity());
            ImageButton matchingCalendar = new ImageButton(this.getActivity());
            ImageButton deleteButton = new ImageButton(this.getActivity());
            ImageButton infoButton = new ImageButton(this.getActivity());


            if (matchingBitmapList.get(i) == null) {
                if (userState.getPrimaryUserProfile().getClassificationId() == ProfileType.TENANT.getValue()) {
                    Glide.with(mContext).load(R.drawable.apartment_default).into(matchingImage);
                } else {
                    Glide.with(mContext).load(R.drawable.tenant_default).into(matchingImage);
                }
            } else {
                matchingImage.setImageBitmap(matchingBitmapList.get(i));
            }

            matchingText.setText(matchingProfileList.get(i));

            matchingCalendar.setVisibility(View.VISIBLE);
            matchingCalendar.setBackground(getResources().getDrawable(R.drawable.calendar_icon));
            matchingCalendar.setOnClickListener(myCalendarHandler);

            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setBackground(getResources().getDrawable(R.drawable.clear_icon));
            deleteButton.setOnClickListener(myDeleteHandler);

            infoButton.setVisibility(View.VISIBLE);
            infoButton.setBackground(getResources().getDrawable(R.drawable.info_icon));
            infoButton.setOnClickListener(myInfoHandler);

            row.addView(matchingImage);
            row.addView(matchingText);
            row.addView(infoButton);
            row.addView(matchingCalendar);
            row.addView(deleteButton);

            TableRow.LayoutParams paramsImage = (TableRow.LayoutParams) matchingImage.getLayoutParams();
            paramsImage.setMargins(10, 1, 10, 1);
            paramsImage.width = 90;
            paramsImage.height = 90;
            matchingImage.setLayoutParams(paramsImage);

            TableRow.LayoutParams paramsText = (TableRow.LayoutParams) matchingText.getLayoutParams();
            paramsText.setMargins(10, 0, 10, 0);
            paramsText.weight = 1;
            paramsText.width = TableRow.LayoutParams.FILL_PARENT;
            matchingText.setLayoutParams(paramsText);

            TableRow.LayoutParams paramsCalendar = (TableRow.LayoutParams) matchingCalendar.getLayoutParams();
            paramsCalendar.setMargins(0, 0, 0, 0);
            matchingCalendar.setLayoutParams(paramsCalendar);

            TableRow.LayoutParams paramsDelete = (TableRow.LayoutParams) deleteButton.getLayoutParams();
            paramsDelete.setMargins(0, 0, 0, 0);
            deleteButton.setLayoutParams(paramsDelete);

            TableRow.LayoutParams paramsInfo = (TableRow.LayoutParams) infoButton.getLayoutParams();
            paramsInfo.setMargins(0, 0, 0, 0);
            infoButton.setLayoutParams(paramsInfo);

            matchingOverview.addView(row, i);

        }
    }

    View.OnClickListener myCalendarHandler = new View.OnClickListener() {
        public void onClick(View v) {
            for (int i = 0; i < matchingOverview.getChildCount(); i++) {
                View view = matchingOverview.getChildAt(i);

                if (view instanceof TableRow) {
                    TableRow row = (TableRow) view;

                    for (int j = 0; j < row.getChildCount(); j++) {
                        View elementView = row.getChildAt(j);

                        if (elementView instanceof ImageButton) {
                            ImageButton currentButton = (ImageButton) elementView;

                            if (currentButton == v) {
                                if (currentButton == v) {
                                    Intent intent = new Intent(getContext(), CalendarActivity.class);
                                    if (userState.getPrimaryUserProfile().getClassificationId() == ProfileType.TENANT.getValue()) {
                                        Log.d(TAG, "onClick: matchingApartmentProfil: " + matchingApartmentProfile.get(i).getId());
                                        Log.d(TAG, "onClick: matchingTenantProfil: " + userState.getTenantId());
                                        intent.putExtra(TenantSessionId, userState.getTenantId());
                                        intent.putExtra(ApartmentSessionId, matchingApartmentProfile.get(i).getId());
                                        startActivity(intent);
                                    } else {
                                        Log.d(TAG, "onClick: matchingTenantProfil: " + matchingTenantProfile.get(i).getId());
                                        Log.d(TAG, "onClick: matchingApartmentProfil" + userState.getApartmentId());
                                        intent.putExtra(TenantSessionId, matchingTenantProfile.get(i).getId());
                                        intent.putExtra(ApartmentSessionId, userState.getApartmentId());
                                        startActivity(intent);
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    };

    View.OnClickListener myInfoHandler = new View.OnClickListener() {
        public void onClick(View v) {
            for (int i = 0; i < matchingOverview.getChildCount(); i++) {
                View view = matchingOverview.getChildAt(i);

                if (view instanceof TableRow) {
                    TableRow row = (TableRow) view;

                    for (int j = 0; j < row.getChildCount(); j++) {
                        View elementView = row.getChildAt(j);

                        if (elementView instanceof ImageButton) {
                            ImageButton currentButton = (ImageButton) elementView;

                            if (currentButton == v) {
                                if (currentButton == v) {
                                    if (userState.getPrimaryUserProfile().getClassificationId() == ProfileType.TENANT.getValue()) {
                                        apartmentPopUp(v, i);
                                    } else {
                                        tenantPopUp(v, i);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    View.OnClickListener myDeleteHandler = new View.OnClickListener() {
        public void onClick(View v) {
            for (int i = 0; i < matchingOverview.getChildCount(); i++) {
                View view = matchingOverview.getChildAt(i);

                if (i < matchingOverview.getChildCount() - 1) {
                    View viewNext = matchingOverview.getChildAt(i + 1);

                    if (view instanceof TableRow) {
                        TableRow row = (TableRow) view;
                        TableRow rowNext = (TableRow) viewNext;

                        for (int j = 0; j < row.getChildCount(); j++) {
                            View elementView = row.getChildAt(j);

                            if (elementView instanceof ImageButton) {
                                ImageButton currentButton = (ImageButton) elementView;

                                View buttonViewNext = rowNext.getChildAt(3);
                                ImageButton buttonNext = (ImageButton) buttonViewNext;

                                View calendarButtonNextView = rowNext.getChildAt(2);
                                ImageButton nextCalendarButton = (ImageButton) calendarButtonNextView;

                                View textViewNext = rowNext.getChildAt(1);
                                TextView nextDateText = (TextView) textViewNext;

                                View textView = row.getChildAt(1);
                                TextView currentDateText = (TextView) textView;

                                View imageViewNext = rowNext.getChildAt(0);
                                ImageView nextImageView = (ImageView) imageViewNext;

                                View imageView = row.getChildAt(0);
                                ImageView currentImageView = (ImageView) imageView;

                                if (currentButton == v) {
                                    sendDeleteToServer(i);
                                    currentDateText.setText(nextDateText.getText());
                                    currentImageView.setImageDrawable(nextImageView.getDrawable());
                                    nextImageView.setVisibility(View.GONE);
                                    nextCalendarButton.setVisibility(View.GONE);
                                    buttonNext.setVisibility(View.GONE);
                                    nextDateText.setText("");
                                    rowNext.setVisibility(View.GONE);
                                    if (currentDateText.getText() == "") {
                                        currentButton.setVisibility(View.GONE);
                                        row.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (view instanceof TableRow) {
                        TableRow row = (TableRow) view;

                        for (int j = 0; j < row.getChildCount(); j++) {
                            View elementView = row.getChildAt(j);

                            if (elementView instanceof ImageButton) {
                                ImageButton currentButton = (ImageButton) elementView;

                                View textView = row.getChildAt(1);
                                TextView currentDateText = (TextView) textView;

                                View calendarButtonCurrentView = row.getChildAt(2);
                                ImageButton currentCalendarButton = (ImageButton) calendarButtonCurrentView;

                                View imageView = row.getChildAt(0);
                                ImageView currentImageView = (ImageView) imageView;

                                if (currentButton == v) {
                                    sendDeleteToServer(i);
                                    currentDateText.setText("");
                                    currentCalendarButton.setVisibility(View.GONE);
                                    currentButton.setVisibility(View.GONE);
                                    currentImageView.setVisibility(View.GONE);
                                    row.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    private void sendDeleteToServer(int i) {
        if (userState.getPrimaryUserProfile().getClassificationId() == ProfileType.TENANT.getValue()) {
            Log.d(TAG, "onClick: Delete TenantID: " + userState.getTenantId() + " delete matchingApartment" + matchingApartmentProfile.get(i).getId());
            mPresenter.userDeleteApartment(userState.getTenantId(), matchingApartmentProfile.get(i).getId());
        } else {
            Log.d(TAG, "onClick: Delete TenantID: " + matchingTenantProfile.get(i).getId() + " delete matchingApartment" + userState.getApartmentId());

            mPresenter.userDeleteApartment(matchingTenantProfile.get(i).getId(), userState.getApartmentId());
        }
    }

    public void apartmentPopUp(View v, int i) {
        View customView = getActivity().getLayoutInflater().inflate(R.layout.activity_show_detail_profil_apartment, null);

        apartmentPriceTextView = (TextView) customView.findViewById(R.id.apartmentPriceTextView);
        apartmentPriceTextView.setText(matchingApartmentProfile.get(i).getPrice() + " €");
        apartmentSizeTextView = (TextView) customView.findViewById(R.id.apartmentSizeTextView);
        apartmentSizeTextView.setText(matchingApartmentProfile.get(i).getArea() + " m2");
        apartmentEmailTextView = (TextView) customView.findViewById(R.id.apartmentEMAILTextView);
        apartmentEmailTextView.setText(matchingApartmentProfile.get(i).getEmail());
        apartmentZipCodeTextView = (TextView) customView.findViewById(R.id.apartmentZIPCODETextView);
        apartmentZipCodeTextView.setText(matchingApartmentProfile.get(i).getApartmentLocation().getZipCode() + "");
        apartmentCityTextView = (TextView) customView.findViewById(R.id.apartmentCITYTextView);
        apartmentCityTextView.setText(matchingApartmentProfile.get(i).getApartmentLocation().getCity());
        apartmentStateTextView = (TextView) customView.findViewById(R.id.apartmentSTATETextView);
        apartmentStateTextView.setText(matchingApartmentProfile.get(i).getApartmentLocation().getState() + "");
        apartmentCountryTextView = (TextView) customView.findViewById(R.id.apartmentCOUNTRYTextView);
        apartmentCountryTextView.setText(matchingApartmentProfile.get(i).getApartmentLocation().getCountry() + "");
        apartmentImageView = (ImageView) customView.findViewById(R.id.apartmentInfoImageView);
        if (matchingApartmentBitmap.get(i) == null) {
            apartmentImageView.setImageResource(apartment_default);
        } else {
            apartmentImageView.setImageBitmap(matchingApartmentBitmap.get(i));
        }
        internetImageView = (ImageView) customView.findViewById(R.id.internetThumb);
        if (matchingApartmentProfile.get(i).hasInternet()) {
            internetImageView.setImageResource(thumb_up_icon);
        } else {
            internetImageView.setImageResource(thumb_down_icon);
        }
        smokerImageView = (ImageView) customView.findViewById(R.id.smokerThumb);
        if (matchingApartmentProfile.get(i).isSmokerApartment()) {
            smokerImageView.setImageResource(thumb_up_icon);
        } else {
            smokerImageView.setImageResource(thumb_down_icon);
        }
        petsImageView = (ImageView) customView.findViewById(R.id.petsThumb);
        if (matchingApartmentProfile.get(i).hasPets()) {
            petsImageView.setImageResource(thumb_up_icon);
        } else {
            petsImageView.setImageResource(thumb_down_icon);
        }
        washingMashineImageView = (ImageView) customView.findViewById(R.id.washingMashineThumb);
        if (matchingApartmentProfile.get(i).hasWashingMachine()) {
            washingMashineImageView.setImageResource(thumb_up_icon);
        } else {
            washingMashineImageView.setImageResource(thumb_down_icon);
        }
        purposeImageView = (ImageView) customView.findViewById(R.id.purpseThumb);
        if (matchingApartmentProfile.get(i).isPurposeApartment()) {
            purposeImageView.setImageResource(thumb_up_icon);
        } else {
            purposeImageView.setImageResource(thumb_down_icon);
        }

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


        mPopupWindow.showAtLocation(mFrameLayout, Gravity.CENTER, 0, 0);
    }

    public void tenantPopUp(View v, int i) {
        View customView = getActivity().getLayoutInflater().inflate(R.layout.activity_show_detail_profil_tenant, null);
        tenantEmailTextView = (TextView) customView.findViewById(R.id.tenantEmailTextView);
        tenantEmailTextView.setText(matchingTenantProfile.get(i).getEmail());
        tenantImageView = (ImageView) customView.findViewById(R.id.tenantInfoImageView);
        if (matchingTenantBitmap.get(i) == null) {
            tenantImageView.setImageResource(tenant_default);
        } else {
            tenantImageView.setImageBitmap(matchingTenantBitmap.get(i));
        }
        tenantNameTextView = (TextView) customView.findViewById(R.id.tenantNameTextView);
        tenantNameTextView.setText(matchingTenantProfile.get(i).getFirstName() + "");
        tenantAgeTextView = (TextView) customView.findViewById(R.id.tenantAgeTextView2);
        tenantAgeTextView.setText(matchingTenantProfile.get(i).getAge() + "");
        tenantGenderImageView = (ImageView) customView.findViewById(R.id.genderThumb);
        if (matchingTenantProfile.get(i).getGender() == 0) {
            tenantGenderImageView.setImageResource(male_icon);
        } else {
            tenantGenderImageView.setImageResource(female_icon);
        }
        tenantSmokerImageView = (ImageView) customView.findViewById(R.id.smokerThumb);
        if (matchingTenantProfile.get(i).isSmoker()) {
            tenantSmokerImageView.setImageResource(thumb_up_icon);
        } else {
            tenantSmokerImageView.setImageResource(thumb_down_icon);
        }
        tenantPetsImageView = (ImageView) customView.findViewById(R.id.petsThumb);
        if (matchingTenantProfile.get(i).hasPets()) {
            tenantPetsImageView.setImageResource(thumb_up_icon);
        } else {
            tenantPetsImageView.setImageResource(thumb_down_icon);
        }
        tenantOccupationTextView = (TextView) customView.findViewById(R.id.tenantOccupationTextView);
        tenantOccupationTextView.setText(matchingTenantProfile.get(i).getOccupation() + "");
        tenantInfoTextView = (TextView) customView.findViewById(R.id.tenantInfoTextView);
        tenantInfoTextView.setText(matchingTenantProfile.get(i).getShortBio() + "");

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

    public void bindView(View view) {
        matchingOverview = (TableLayout) view.findViewById(R.id.matchingOverview);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.matchingOverviewActivity);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMatches();
            }
        });
    }

    @Override
    public void showTenants(List<Pair<TenantProfile, Bitmap>> tenants) {
        matchingTenantProfile.clear();
        matchingTenantBitmap.clear();
        for (Pair<TenantProfile, Bitmap> pair : tenants) {
            matchingTenantProfile.add(pair.getLeft());
            matchingTenantBitmap.add(pair.getRight());
        }
        Log.d(TAG, "showTenants: success " + tenants.size());
        generateMatchingOverview(matchingTenantProfile, null, matchingTenantBitmap);
    }

    @Override
    public void showApartments(List<Pair<ApartmentProfile, Bitmap>> apartments) {
        matchingApartmentProfile.clear();
        matchingApartmentBitmap.clear();
        for (Pair<ApartmentProfile, Bitmap> pair : apartments) {
            matchingApartmentProfile.add(pair.getLeft());
            matchingApartmentBitmap.add(pair.getRight());
        }
        Log.d(TAG, "showApartments: success " + apartments.size());
        generateMatchingOverview(null, matchingApartmentProfile, matchingApartmentBitmap);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void successfulDeleted() {
        Toast.makeText(getActivity(), "Successful deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Log.e("Error in MatchingOverview: ", message);
    }
}
