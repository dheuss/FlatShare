package com.flatshare.presentation.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.presentation.presenters.TenantSettingsPresenter;
import com.flatshare.presentation.presenters.impl.TenantSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

import dmax.dialog.SpotsDialog;

import static com.appyvet.rangebar.R.attr.rangeBar;

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

        priceRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                minPrice.setText("" + leftPinIndex);
                maxPrice.setText("" + rightPinIndex);
            }

        });

        sizeRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                minSize.setText("" + leftPinIndex);
                maxSize.setText("" + rightPinIndex);
            }

        });


        profileDoneButton.setOnClickListener(view -> sendFilterSettings());


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
        minPrice = (TextView) findViewById(R.id.price_range_textview_minPRICE);
        maxPrice = (TextView) findViewById(R.id.price_range_textview_maxPRICE);
        priceRange = (RangeBar) findViewById(R.id.rangebar_price_range);

        minSize = (TextView) findViewById(R.id.size_range_textview_minSIZE);
        maxSize = (TextView) findViewById(R.id.size_range_textview_maxSIZE);
        sizeRange = (RangeBar) findViewById(R.id.rangebar_size_range);

        //TODO AREA

        purposeCommunityRadioGroup = (RadioGroup) findViewById(R.id.purposeCommunityRadioGroup);
        yesPurposeCommunityRadioButton = (RadioButton) findViewById(R.id.necessaryPCRadioButton);
        noPurposeCommunityRadioButton = (RadioButton) findViewById(R.id.notNecessaryPCRadioButton);

        smokingApartmentRadioGroup = (RadioGroup) findViewById(R.id.smoker_apartment_RadioGroup);
        yesSmokingApartmentRadioButton = (RadioButton) findViewById(R.id.necessarySARadioButton);
        noSmokingApartmentRadioButton = (RadioButton) findViewById(R.id.notNecessarySARadioButton);

        balconyRadioGroup = (RadioGroup) findViewById(R.id.balconyRadioGroup);
        yesBalconyRadioButton = (RadioButton) findViewById(R.id.necessaryBRadioButton);
        noBalconyRadioButton = (RadioButton) findViewById(R.id.notNecessaryBRadioButton);

        internetRadioGroup = (RadioGroup) findViewById(R.id.internetRadioGroup);
        yesInternetRadioButton = (RadioButton) findViewById(R.id.internetYESRadioButton);
        noInternetRadioButton = (RadioButton) findViewById(R.id.internetNORadioButton);

        cabelTVRadioGroup = (RadioGroup) findViewById(R.id.cabelTVRadioGroup);
        yesCabelTVRadioButton = (RadioButton) findViewById(R.id.cabelTVYESRadioButton);
        noCabelTVRadioButton = (RadioButton) findViewById(R.id.cableTVNORadioButton);

        washingMashineRadioGroup = (RadioGroup) findViewById(R.id.washingMashineRadioGroup);
        yesWashingMashineRadioButton = (RadioButton) findViewById(R.id.washingMashineYESRadioButton);
        noWashingMashineRadioButton = (RadioButton) findViewById(R.id.washingMashineNORadioButton);

        dryerRadioGroup = (RadioGroup) findViewById(R.id.dryerRadioGroup);
        yesDryerRadioButton = (RadioButton) findViewById(R.id.dryerYESRadioButton);
        noDryerRadioButton = (RadioButton) findViewById(R.id.dryerNORadioButton);

        bathTubeRadioGroup = (RadioGroup) findViewById(R.id.bathTubeRadioGroup);
        yesBathTubeRadioButton = (RadioButton) findViewById(R.id.bathTubeYESRadioButton);
        noBathTubeButton = (RadioButton) findViewById(R.id.bathTubeNORadioButton);

        petsRadioGroup = (RadioGroup) findViewById(R.id.petsRadioGroup);
        yesPetsRadioButton = (RadioButton) findViewById(R.id.petsYESRadioButton);
        noPetsRadioButton = (RadioButton) findViewById(R.id.petsNORadioButton);

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
