package com.flatshare.presentation.ui.activities.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.presentation.presenters.profile.TenantSettingsPresenter;
import com.flatshare.presentation.presenters.profile.impl.TenantSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.MainActivity;
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

    private RadioButton yesPurposeCommunityRadioButton, noPurposeCommunityRadioButton, neiPurposeCommunityRadioButton;

    private RadioButton yesSmokingApartmentRadioButton, noSmokingApartmentRadioButton, neiSmokingApartmentRadioButton;

    private RadioButton yesBalconyRadioButton, noBalconyRadioButton, neiBalconyRadioButton;

    private RadioButton yesInternetRadioButton, noInternetRadioButton, neiInternetRadioButton;

    private RadioButton yesCabelTVRadioButton, noCabelTVRadioButton, neiCabelTVRadioButton;

    private RadioButton yesWashingMashineRadioButton, noWashingMashineRadioButton, neiWashingMashineRadioButton;

    private RadioButton yesDryerRadioButton, noDryerRadioButton, neiDryerRadioButton;

    private RadioButton yesBathTubeRadioButton, noBathTubeButton, neiBathTubeRadioButton;

    private RadioButton yesPetsRadioButton, noPetsRadioButton, neiPetsRadioButton;

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
        int sizeFrom = Integer.parseInt(minSize.getText().toString());
        int sizeTo = Integer.parseInt(maxSize.getText().toString());

        int isPurposeApartment = 2;
        int isSmokerApartment = 2;
        int isBalcony = 2;
        int isInternet = 2;
        int isTVCable = 2;
        int isWashingMashine = 2;
        int isDryer = 2;
        int isBathtube = 2;
        int isPetAllowed = 2;

        //TODO Area

        if (yesPurposeCommunityRadioButton.isChecked()){
            isPurposeApartment = 0;
        }
        if (noPurposeCommunityRadioButton.isChecked()){
            isPurposeApartment = 1;
        }
        if (neiPurposeCommunityRadioButton.isChecked()){
            isPurposeApartment = 2;
        }
        if (yesSmokingApartmentRadioButton.isChecked()){
            isSmokerApartment = 0;
        }
        if (noSmokingApartmentRadioButton.isChecked()){
            isSmokerApartment = 1;
        }
        if (neiSmokingApartmentRadioButton.isChecked()){
            isSmokerApartment = 2;
        }
        if (yesBalconyRadioButton.isChecked()){
            isBalcony = 0;
        }
        if (noBalconyRadioButton.isChecked()){
            isBalcony = 1;
        }
        if (neiBalconyRadioButton.isChecked()){
            isBalcony = 2;
        }
        if (yesInternetRadioButton.isChecked()){
            isInternet = 0;
        }
        if (noInternetRadioButton.isChecked()){
            isInternet = 1;
        }
        if (neiInternetRadioButton.isChecked()){
            isInternet = 2;
        }
        if (yesCabelTVRadioButton.isChecked()){
            isTVCable = 0;
        }
        if (noCabelTVRadioButton.isChecked()){
            isTVCable = 1;
        }
        if (neiCabelTVRadioButton.isChecked()){
            isTVCable = 2;
        }
        if (yesWashingMashineRadioButton.isChecked()){
            isWashingMashine = 0;
        }
        if (noWashingMashineRadioButton.isChecked()){
            isWashingMashine = 1;
        }
        if (neiWashingMashineRadioButton.isChecked()){
            isWashingMashine = 2;
        }
        if (yesDryerRadioButton.isChecked()){
            isDryer = 0;
        }
        if (noDryerRadioButton.isChecked()){
            isDryer = 1;
        }
        if (neiDryerRadioButton.isChecked()){
            isDryer = 2;
        }
        if (yesBathTubeRadioButton.isChecked()){
            isBathtube = 0;
        }
        if (noBathTubeButton.isChecked()){
            isBathtube = 1;
        }
        if (neiBathTubeRadioButton.isChecked()){
            isBathtube = 2;
        }
        if (yesPetsRadioButton.isChecked()){
            isPetAllowed = 0;
        }
        if (noPetsRadioButton.isChecked()){
            isPetAllowed = 1;
        }
        if (neiPetsRadioButton.isChecked()){
            isPetAllowed = 2;
        }

        TenantFilterSettings tenantFilterSettings = new TenantFilterSettings();
        tenantFilterSettings.setPriceFrom(priceFrom);
        tenantFilterSettings.setPriceTo(priceTo);
        tenantFilterSettings.setSizeFrom(sizeFrom);
        tenantFilterSettings.setSizeTo(sizeTo);
        tenantFilterSettings.setInternet(isInternet);
        tenantFilterSettings.setSmokerApartment(isSmokerApartment);
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

        yesPurposeCommunityRadioButton = (RadioButton) findViewById(R.id.purposeCommunityYES_RadioButton);
        noPurposeCommunityRadioButton = (RadioButton) findViewById(R.id.purposeCommunityNO_RadioButton);
        neiPurposeCommunityRadioButton = (RadioButton) findViewById(R.id.purposeCommunityNEI_RadioButton);

        yesSmokingApartmentRadioButton = (RadioButton) findViewById(R.id.smokingApartmentYES_RadioButton);
        noSmokingApartmentRadioButton = (RadioButton) findViewById(R.id.smokingApartmentNO_RadioButton);
        neiSmokingApartmentRadioButton = (RadioButton) findViewById(R.id.smokingApartmentNEI_RadioButton);

        yesBalconyRadioButton = (RadioButton) findViewById(R.id.balconyYES_RadioButton);
        noBalconyRadioButton = (RadioButton) findViewById(R.id.balconyNO_RadioButton);
        neiBalconyRadioButton = (RadioButton) findViewById(R.id.balconyNEI_RadioButton);

        yesInternetRadioButton = (RadioButton) findViewById(R.id.internetYES_RadioButton);
        noInternetRadioButton = (RadioButton) findViewById(R.id.internetNO_RadioButton);
        neiInternetRadioButton = (RadioButton) findViewById(R.id.internetNEI_RadioButton);

        yesCabelTVRadioButton = (RadioButton) findViewById(R.id.tvYES_RadioButton);
        noCabelTVRadioButton = (RadioButton) findViewById(R.id.tvNO_RadioButton);
        neiCabelTVRadioButton = (RadioButton) findViewById(R.id.tvNEI_RadioButton);

        yesWashingMashineRadioButton = (RadioButton) findViewById(R.id.washingMashineYES_RadioButton);
        noWashingMashineRadioButton = (RadioButton) findViewById(R.id.washingMashineNO_RadioButton);
        neiWashingMashineRadioButton = (RadioButton) findViewById(R.id.washingMashineNEI_RadioButton);

        yesDryerRadioButton = (RadioButton) findViewById(R.id.dryerYES_RadioButton);
        noDryerRadioButton = (RadioButton) findViewById(R.id.dryerNO_RadioButton);
        neiDryerRadioButton = (RadioButton)findViewById(R.id.dryerNEI_RadioButton);

        yesBathTubeRadioButton = (RadioButton) findViewById(R.id.bathTubeYES_RadioButton);
        noBathTubeButton = (RadioButton) findViewById(R.id.bathTubeNO_RadioButton);
        neiBathTubeRadioButton = (RadioButton)findViewById(R.id.bathTubeNEI_RadioButton);

        yesPetsRadioButton = (RadioButton) findViewById(R.id.petsYES_RadioButton);
        noPetsRadioButton = (RadioButton) findViewById(R.id.petsNO_RadioButton);
        neiPetsRadioButton = (RadioButton) findViewById(R.id.petsNEI_RadioButton);

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
        Log.d(TAG, "success! changed to MainActivity!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
