package com.flatshare.presentation.ui.activities.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.presentation.presenters.profile.TenantSettingsPresenter;
import com.flatshare.presentation.presenters.profile.impl.TenantSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

import dmax.dialog.SpotsDialog;

/**
 * Created by Arber on 16/12/2016.
 */
public class TenantSettingsActivity extends AbstractActivity implements TenantSettingsPresenter.View {

    private TextView minPrice;
    private TextView maxPrice;
    private RangeBar priceRange;

    private TextView minSize;
    private TextView maxSize;
    private RangeBar sizeRange;

    private RadioGroup purposeCommunityRadioGroup;
    private RadioButton yesPurposeCommunityRadioButton, noPurposeCommunityRadioButton;

    private RadioGroup smokingApartmentRadioGroup;
    private RadioButton yesSmokingApartmentRadioButton, noSmokingApartmentRadioButton;

    private RadioGroup balconyRadioGroup;
    private RadioButton yesBalconyRadioButton, noBalconyRadioButton;

    private RadioGroup internetRadioGroup;
    private RadioButton yesInternetRadioButton, noInternetRadioButton;

    private RadioGroup cabelTVRadioGroup;
    private RadioButton yesCabelTVRadioButton, noCabelTVRadioButton;

    private RadioGroup washingMashineRadioGroup;
    private RadioButton yesWashingMashineRadioButton, noWashingMashineRadioButton;

    private RadioGroup dryerRadioGroup;
    private RadioButton yesDryerRadioButton, noDryerRadioButton;

    private RadioGroup bathTubeRadioGroup;
    private RadioButton yesBathTubeRadioButton, noBathTubeButton;

    private RadioGroup petsRadioGroup;
    private RadioButton yesPetsRadioButton, noPetsRadioButton;

    private Button profileDoneButton;

    private TenantSettingsPresenter mPresenter;
    private static final String TAG = "TenantProfileAct";
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new TenantSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        minPrice.setText(priceRange.getLeftPinValue());
        maxPrice.setText(priceRange.getRightPinValue());

        priceRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar,
                                              int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                minPrice.setText(priceRange.getLeftPinValue());
                maxPrice.setText(priceRange.getRightPinValue());
            }

        });

        minSize.setText(sizeRange.getLeftPinValue());
        maxSize.setText(sizeRange.getRightPinValue());

        sizeRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar,
                                              int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue,
                                              String rightPinValue) {
                minSize.setText(sizeRange.getLeftPinValue());
                maxSize.setText(sizeRange.getRightPinValue());
            }
        });

        profileDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TenantSettingsActivity.this.sendFilterSettings();
            }
        });


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tenant_settings;
    }

    private void sendFilterSettings() {

        int priceFrom = Integer.parseInt(minPrice.getText().toString());
        int priceTo = Integer.parseInt(maxPrice.getText().toString());

        //TODO Area

        int isInternet = internetRadioGroup.getCheckedRadioButtonId() == yesInternetRadioButton.getId() ? 0 : 1;
        int isSomkerApartment = smokingApartmentRadioGroup.getCheckedRadioButtonId() == yesSmokingApartmentRadioButton.getId() ? 0 : 1;
        int isPetAllowed = petsRadioGroup.getCheckedRadioButtonId() == yesPetsRadioButton.getId() ? 0 : 1;
        int isPurposeApartment = purposeCommunityRadioGroup.getCheckedRadioButtonId() == yesPetsRadioButton.getId() ? 0 : 1;
        int isWashingMashine = washingMashineRadioGroup.getCheckedRadioButtonId() == yesWashingMashineRadioButton.getId() ? 0 : 1;
        int isDryer = dryerRadioGroup.getCheckedRadioButtonId() == yesDryerRadioButton.getId() ? 0 : 1;
        int isBalcony = balconyRadioGroup.getCheckedRadioButtonId() == yesBalconyRadioButton.getId() ? 0 : 1;
        int isBathtube = bathTubeRadioGroup.getCheckedRadioButtonId() == yesBathTubeRadioButton.getId() ? 0 : 1;
        int isTVCable = cabelTVRadioGroup.getCheckedRadioButtonId() == yesCabelTVRadioButton.getId() ? 0 : 1;

        TenantFilterSettings tenantFilterSettings = new TenantFilterSettings();
        tenantFilterSettings.setPriceFrom(priceFrom);
        tenantFilterSettings.setPriceTo(priceTo);
        tenantFilterSettings.setInternet(isInternet);
        tenantFilterSettings.setSmokerApartment(isSomkerApartment);
        tenantFilterSettings.setPetsAllowed(isPetAllowed);
        tenantFilterSettings.setPurposeApartment(isPurposeApartment);
        tenantFilterSettings.setWashingMachine(isWashingMashine);
        tenantFilterSettings.setDryer(isDryer);
        tenantFilterSettings.setBalcony(isBalcony);
        tenantFilterSettings.setBathtub(isBathtube);
        tenantFilterSettings.setTvCable(isTVCable);

        mPresenter.sendFilterSettings(tenantFilterSettings);
    }

    private void bindView() {
        minPrice = (TextView) findViewById(R.id.tenant_settings_price_range_2);
        maxPrice = (TextView) findViewById(R.id.tenant_settings_price_range_4);
        priceRange = (RangeBar) findViewById(R.id.rangebar_price_range);

        minSize = (TextView) findViewById(R.id.tenant_settings_size_range_2);
        maxSize = (TextView) findViewById(R.id.tenant_settings_size_range_4);
        sizeRange = (RangeBar) findViewById(R.id.rangebar_size_range);

        //TODO AREA

        purposeCommunityRadioGroup = (RadioGroup) findViewById(R.id.purposeCommunity_RadioGroup);
        yesPurposeCommunityRadioButton = (RadioButton) findViewById(R.id.purposeCommunityYES_RadioButton);
        noPurposeCommunityRadioButton = (RadioButton) findViewById(R.id.purposeCommunityNO_RadioButton);

        smokingApartmentRadioGroup = (RadioGroup) findViewById(R.id.smokingApartment_RadioGroup);
        yesSmokingApartmentRadioButton = (RadioButton) findViewById(R.id.smokingApartmentYES_RadioButton);
        noSmokingApartmentRadioButton = (RadioButton) findViewById(R.id.smokingApartmentNO_RadioButton);

        balconyRadioGroup = (RadioGroup) findViewById(R.id.balcony_RadioGroup);
        yesBalconyRadioButton = (RadioButton) findViewById(R.id.balconyYES_RadioButton);
        noBalconyRadioButton = (RadioButton) findViewById(R.id.balconyNO_RadioButton);

        internetRadioGroup = (RadioGroup) findViewById(R.id.internet_RadioGroup);
        yesInternetRadioButton = (RadioButton) findViewById(R.id.internetYES_RadioButton);
        noInternetRadioButton = (RadioButton) findViewById(R.id.internetNO_RadioButton);

        cabelTVRadioGroup = (RadioGroup) findViewById(R.id.tv_RadioGroup);
        yesCabelTVRadioButton = (RadioButton) findViewById(R.id.tvYES_RadioButton);
        noCabelTVRadioButton = (RadioButton) findViewById(R.id.tvNO_RadioButton);

        washingMashineRadioGroup = (RadioGroup) findViewById(R.id.washingMashine_RadioGroup);
        yesWashingMashineRadioButton = (RadioButton) findViewById(R.id.washingMashineYES_RadioButton);
        noWashingMashineRadioButton = (RadioButton) findViewById(R.id.washingMashineNO_RadioButton);

        dryerRadioGroup = (RadioGroup) findViewById(R.id.dryer_RadioGroup);
        yesDryerRadioButton = (RadioButton) findViewById(R.id.dryerYES_RadioButton);
        noDryerRadioButton = (RadioButton) findViewById(R.id.dryerNO_RadioButton);

        bathTubeRadioGroup = (RadioGroup) findViewById(R.id.bathTube_RadioGroup);
        yesBathTubeRadioButton = (RadioButton) findViewById(R.id.bathTubeYES_RadioButton);
        noBathTubeButton = (RadioButton) findViewById(R.id.bathTubeNO_RadioButton);

        petsRadioGroup = (RadioGroup) findViewById(R.id.pets_RadioGroup);
        yesPetsRadioButton = (RadioButton) findViewById(R.id.petsYES_RadioButton);
        noPetsRadioButton = (RadioButton) findViewById(R.id.petsNO_RadioButton);

        profileDoneButton = (Button) findViewById(R.id.done_2_tenant_profile);

        progressDialog = new SpotsDialog(this, R.style.Custom);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToMatchingActivity() {
        //TODO change to MatchingActivity
        Log.d(TAG, "success! changed to TenantSettings!");
        Intent intent = new Intent(this, MatchingActivity.class);
        startActivity(intent);
    }
}
