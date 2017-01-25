package com.flatshare.presentation.ui.activities.matchingoverview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;


import com.flatshare.R;

import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.matchingoverview.MatchingOverviewPresenter;
import com.flatshare.presentation.presenters.matchingoverview.impl.MatchingOverviewPresenterImpl;
import com.flatshare.presentation.ui.AbstractFragmentActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.calendar.CalendarActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

import static com.flatshare.R.drawable.female_icon;
import static com.flatshare.R.drawable.male_icon;
import static com.flatshare.R.drawable.thumb_down_icon;
import static com.flatshare.R.drawable.thumb_up_icon;


public class MatchingOverviewActivity extends AbstractFragmentActivity implements MatchingOverviewPresenter.View {

    private UserState userState;


    //ApartmentPopUp
    private TextView apartmentPriceTextView, apartmentSizeTextView, apartmentZipCodeTextView, apartmentCityTextView, apartmentStateTextView, apartmentCountryTextView;
    private ImageView apartmentImageView, internetImageView, smokerImageView, petsImageView, washingMashineImageView, purposeImageView;
    private int apartmentPrice, apartmentSize, apartmentZipCode;
    private String apartmentCity, apartmentState, apartmentCountry;
    private Bitmap apartmentImage;
    private Boolean internet, smoker, pets, washingMashine, purpose;

    //TenantPopUp
    private TextView tenantNameTextView, tenantAgeTextView, tenantOccupationTextView, tenantInfoTextView;
    private ImageView tenantGenderImageView, tenantSmokerImageView, tenantPetsImageView, tenantImageView;
    private String tenantName, tenantOccupation, tenantInfo;
    private int tenantAge;
    private Bitmap tenantImage;
    private int tenantGender;
    private Boolean tenantSmoker, tenantPets;

    private ImageButton closeButton;

    private ProgressBar progressBar;

    private PopupWindow mPopupWindow;
    private FrameLayout mFrameLayout;

    private static final String TAG = "MatchingOverviewActivity";

    private MatchingOverviewPresenter mPresenter;
    private TableLayout matchingOverview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matching_overview, container, false);

        bindView(view);

        Log.d(TAG, "inside onCreate(), creating presenter for this view");

        mPresenter = new MatchingOverviewPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );


        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        List<String> matchingTitleList = new ArrayList<>();
        List<Integer> matchingImageList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            matchingTitleList.add(i + " This should be a Title for the hole shit!");
            matchingImageList.add(R.drawable.tenant_default);
        }
        generateMatchingOverview(matchingTitleList, matchingImageList);

        return view;
    }

    public void generateMatchingOverview(List<String> matchingTitleList, List<Integer> matchingImageList) {

        for (int i = 0; i < matchingTitleList.size(); i++) {

            TableRow row = new TableRow(this.getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);

            TextView matchingText = new TextView(this.getActivity());
            ImageView matchingImage = new ImageView(this.getActivity());
            ImageButton matchingCalendar = new ImageButton(this.getActivity());
            ImageButton deleteButton = new ImageButton(this.getActivity());
            ImageButton infoButton = new ImageButton(this.getActivity());

            matchingImage.setImageResource(matchingImageList.get(i));

            matchingText.setText(matchingTitleList.get(i));

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
            //TODO übergeben welcher Match übergeben wird
            //TODO in der CalendarView die MatchingID oder so was überprüfen
            startActivity(new Intent(getActivity(), CalendarActivity.class));
        }
    };

    View.OnClickListener myInfoHandler = new View.OnClickListener() {
        public void onClick(View v) {
            if (userState.getPrimaryUserProfile().getClassificationId() == ProfileType.TENANT.getValue()) {
                apartmentPopUp();
            } else {
                tenantPopUp();
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
                                    //TODO semd gelöschtest zu firebase
                                    mPresenter.deleteMatch();
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
                                    //TODO senden an Firebase
                                    mPresenter.deleteMatch();
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

    public void apartmentPopUp() {
        View customView = getActivity().getLayoutInflater().inflate(R.layout.activity_show_detail_profil_apartment, null);
        apartmentPriceTextView = (TextView) customView.findViewById(R.id.apartmentPriceTextView);
        apartmentPriceTextView.setText(userState.getApartmentProfile().getPrice() + " €");
        apartmentSizeTextView = (TextView) customView.findViewById(R.id.apartmentSizeTextView);
        apartmentSizeTextView.setText(userState.getApartmentProfile().getApartmentSize() + " m2");
        apartmentZipCodeTextView = (TextView) customView.findViewById(R.id.apartmentZIPCODETextView);
        apartmentZipCodeTextView.setText(userState.getApartmentProfile().getApartmentLocation().getZipCode() + "");
        apartmentCityTextView = (TextView) customView.findViewById(R.id.apartmentCITYTextView);
        apartmentCityTextView.setText(userState.getApartmentProfile().getApartmentLocation().getCity());
        apartmentStateTextView = (TextView) customView.findViewById(R.id.apartmentSTATETextView);
        apartmentStateTextView.setText(userState.getApartmentProfile().getApartmentLocation().getState());
        apartmentCountryTextView = (TextView) customView.findViewById(R.id.apartmentCOUNTRYTextView);
        apartmentCountryTextView.setText(userState.getApartmentProfile().getApartmentLocation().getCountry());
        apartmentImageView = (ImageView) customView.findViewById(R.id.apartmentInfoImageView);
        /* TODO Image
        if (getApartmentImage() == null) {
            apartmentImageView.setImageResource(apartment_default);
        } else {
            apartmentImageView.setImageBitmap(getApartmentImage());
        }*/
        internetImageView = (ImageView) customView.findViewById(R.id.internetThumb);
        if (userState.getApartmentProfile().hasInternet()) {
            internetImageView.setImageResource(thumb_up_icon);
        } else {
            internetImageView.setImageResource(thumb_down_icon);
        }
        smokerImageView = (ImageView) customView.findViewById(R.id.smokerThumb);
        if (userState.getApartmentProfile().isSmokerApartment()) {
            smokerImageView.setImageResource(thumb_up_icon);
        } else {
            smokerImageView.setImageResource(thumb_down_icon);
        }
        petsImageView = (ImageView) customView.findViewById(R.id.petsThumb);
        if (userState.getApartmentProfile().hasPets()) {
            petsImageView.setImageResource(thumb_up_icon);
        } else {
            petsImageView.setImageResource(thumb_down_icon);
        }
        washingMashineImageView = (ImageView) customView.findViewById(R.id.washingMashineThumb);
        if (userState.getApartmentProfile().hasWashingMachine()) {
            washingMashineImageView.setImageResource(thumb_up_icon);
        } else {
            washingMashineImageView.setImageResource(thumb_down_icon);
        }
        purposeImageView = (ImageView) customView.findViewById(R.id.purpseThumb);
        if (userState.getApartmentProfile().isPurposeApartment()) {
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

        //Todo Google Maps

        mPopupWindow.showAtLocation(mFrameLayout, Gravity.CENTER, 0, 0);
    }

    public void tenantPopUp() {
        View customView = getActivity().getLayoutInflater().inflate(R.layout.activity_show_detail_profil_tenant, null);
        tenantImageView = (ImageView) customView.findViewById(R.id.tenantInfoImageView);
        /* TODO Image
        if (getTenantImage() == null) {
            tenantImageView.setImageResource(tenant_default);
        } else {
            tenantImageView.setImageBitmap(getTenantImage());
        }*/
        tenantNameTextView = (TextView) customView.findViewById(R.id.tenantNameTextView);
        tenantNameTextView.setText(userState.getTenantProfile().getFirstName());
        tenantAgeTextView = (TextView) customView.findViewById(R.id.tenantAgeTextView2);
        tenantAgeTextView.setText(userState.getTenantProfile().getAge() + "");
        tenantGenderImageView = (ImageView) customView.findViewById(R.id.genderThumb);
        if (userState.getTenantProfile().getGender() == 0) {
            tenantGenderImageView.setImageResource(male_icon);
        } else {
            tenantGenderImageView.setImageResource(female_icon);
        }
        tenantSmokerImageView = (ImageView) customView.findViewById(R.id.smokerThumb);
        if (userState.getTenantProfile().isSmoker()) {
            tenantSmokerImageView.setImageResource(thumb_up_icon);
        } else {
            tenantSmokerImageView.setImageResource(thumb_down_icon);
        }
        tenantPetsImageView = (ImageView) customView.findViewById(R.id.petsThumb);
        if (userState.getTenantProfile().hasPets()) {
            tenantPetsImageView.setImageResource(thumb_up_icon);
        } else {
            tenantPetsImageView.setImageResource(thumb_down_icon);
        }
        tenantOccupationTextView = (TextView) customView.findViewById(R.id.tenantOccupationTextView);
        tenantOccupationTextView.setText(userState.getTenantProfile().getOccupation());
        tenantInfoTextView = (TextView) customView.findViewById(R.id.tenantInfoTextView);
        tenantInfoTextView.setText(userState.getTenantProfile().getShortBio());
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
    }

    @Override
    public void showError(String message) {

    }
}
