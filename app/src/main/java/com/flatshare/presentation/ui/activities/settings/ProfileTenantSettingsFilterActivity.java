package com.flatshare.presentation.ui.activities.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.profile.TenantSettingsPresenter;
import com.flatshare.presentation.presenters.profile.impl.TenantSettingsPresenterImpl;
import com.flatshare.presentation.presenters.settings.ProfileTenantSettingsFilterPresenter;
import com.flatshare.presentation.presenters.settings.impl.ProfileTenantSettingsFilterPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.MainActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfileTenantSettingsFilterActivity extends AbstractActivity implements TenantSettingsPresenter.View {

    private static final String TAG = "ProfileTenantSettingsFilterActivity";
    private TenantSettingsPresenter mPresenter;
    private UserState userState;

    private RangeBar priceRangeBar;
    private TextView minPriceRangeTextView;
    private TextView maxPriceRangeTextView;

    private RangeBar sizeRangeBar;
    private TextView minSizeRangeTextView;
    private TextView maxSizeRangeTextView;

    private RadioGroup purposeCommunityRadioGroup;
    private RadioButton purposeCommunityRadioButtonYES;
    private RadioButton purposeCommunityRadioButtonNO;

    private RadioGroup smokingApartmentRadioGroup;
    private RadioButton smokingApartmentRadioButtonYES;
    private RadioButton smokingApartmentRadioButtonNO;

    private RadioGroup balconyRadioGroup;
    private RadioButton balconyRadioButtonYES;
    private RadioButton balconyRadioButtonNO;

    private RadioGroup internetRadioGroup;
    private RadioButton internetRadioButtonYES;
    private RadioButton internetRadioButtonNO;

    private RadioGroup cabelTVRadioGroup;
    private RadioButton cabelTVRadioButtonYES;
    private RadioButton cabelTVRadioButtonNO;

    private RadioGroup washingMashineRadioGroup;
    private RadioButton washingMashineRadioButtonYES;
    private RadioButton washingMashineRadioButtonNO;

    private RadioGroup dryerRadioGroup;
    private RadioButton dryerRadioButtonYES;
    private RadioButton dryerRadioButtonNO;

    private RadioGroup bathtubeRadioGroup;
    private RadioButton bathtubeRadioButtonYES;
    private RadioButton bathtubeRadioButtonNO;

    private RadioGroup petsRadioGroup;
    private RadioButton petsRadioButtonYES;
    private RadioButton petsRadioButtonNO;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new TenantSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendProfile();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile_tenant_settings_filter;
    }

    private void bindView(){
        priceRangeBar = (RangeBar)findViewById(R.id.rangebar_filter_price_range);
        minPriceRangeTextView = (TextView)findViewById(R.id.tenant_filter_settings_price_range_2);
        maxPriceRangeTextView = (TextView)findViewById(R.id.tenant_filter_settings_price_range_4);
        minPriceRangeTextView.setText(userState.getTenantProfile().getTenantFilterSettings().getPriceFrom()+"");
        maxPriceRangeTextView.setText(userState.getTenantProfile().getTenantFilterSettings().getPriceTo()+"");
        priceRangeBar.setRangePinsByValue(userState.getTenantProfile().getTenantFilterSettings().getPriceFrom(), userState.getTenantProfile().getTenantFilterSettings().getPriceTo());

        sizeRangeBar = (RangeBar)findViewById(R.id.rangebar_filter_size_range);
        minSizeRangeTextView = (TextView)findViewById(R.id.tenant_filter_settings_size_range_2);
        maxSizeRangeTextView = (TextView)findViewById(R.id.tenant_filter_settings_size_range_4);
        minSizeRangeTextView.setText(userState.getTenantProfile().getTenantFilterSettings().getSizeFrom()+"");
        maxSizeRangeTextView.setText(userState.getTenantProfile().getTenantFilterSettings().getSizeTo()+"");
        sizeRangeBar.setRangePinsByValue(userState.getTenantProfile().getTenantFilterSettings().sizeFrom, userState.getTenantProfile().getTenantFilterSettings().getSizeTo());

        purposeCommunityRadioGroup = (RadioGroup)findViewById(R.id.purposeCommunity_filter_RadioGroup);
        purposeCommunityRadioButtonYES = (RadioButton)findViewById(R.id.purposeCommunityYES_filter_RadioButton);
        purposeCommunityRadioButtonNO = (RadioButton)findViewById(R.id.purposeCommunityNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getPurposeApartment() == 1){
            purposeCommunityRadioButtonYES.setChecked(true);
            purposeCommunityRadioButtonNO.setChecked(false);
        } else {
            purposeCommunityRadioButtonYES.setChecked(false);
            purposeCommunityRadioButtonNO.setChecked(true);
        }

        smokingApartmentRadioGroup = (RadioGroup)findViewById(R.id.smokingApartment_filter_RadioGroup);
        smokingApartmentRadioButtonYES = (RadioButton)findViewById(R.id.smokingApartmentYES_filter_RadioButton);
        smokingApartmentRadioButtonNO = (RadioButton)findViewById(R.id.smokingApartmentNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getSmokerApartment() == 0){
            smokingApartmentRadioButtonYES.setChecked(true);
            smokingApartmentRadioButtonNO.setChecked(false);
        } else {
            smokingApartmentRadioButtonYES.setChecked(false);
            smokingApartmentRadioButtonNO.setChecked(true);
        }

        balconyRadioGroup = (RadioGroup)findViewById(R.id.balcony_filter_RadioGroup);
        balconyRadioButtonYES = (RadioButton)findViewById(R.id.balconyYES_filter_RadioButton);
        balconyRadioButtonNO = (RadioButton)findViewById(R.id.balconyNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getBalcony() == 0){
            balconyRadioButtonYES.setChecked(true);
            balconyRadioButtonNO.setChecked(false);
        } else {
            balconyRadioButtonYES.setChecked(false);
            balconyRadioButtonNO.setChecked(true);
        }

        internetRadioGroup = (RadioGroup)findViewById(R.id.internet_filter_RadioGroup);
        internetRadioButtonYES = (RadioButton)findViewById(R.id.internetYES_filter_RadioButton);
        internetRadioButtonNO = (RadioButton)findViewById(R.id.internetNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getInternet() == 0){
            internetRadioButtonYES.setChecked(true);
            internetRadioButtonNO.setChecked(false);
        } else {
            internetRadioButtonYES.setChecked(false);
            internetRadioButtonNO.setChecked(true);
        }

        cabelTVRadioGroup = (RadioGroup)findViewById(R.id.tv_filter_RadioGroup);
        cabelTVRadioButtonYES = (RadioButton)findViewById(R.id.tvYES_filter_RadioButton);
        cabelTVRadioButtonNO = (RadioButton)findViewById(R.id.tvNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getTvCable() == 0){
            cabelTVRadioButtonYES.setChecked(true);
            cabelTVRadioButtonNO.setChecked(false);
        } else {
            cabelTVRadioButtonYES.setChecked(false);
            cabelTVRadioButtonNO.setChecked(true);
        }

        washingMashineRadioGroup = (RadioGroup)findViewById(R.id.washingMashine_filter_RadioGroup);
        washingMashineRadioButtonYES = (RadioButton)findViewById(R.id.washingMashineYES_filter_RadioButton);
        washingMashineRadioButtonNO = (RadioButton)findViewById(R.id.washingMashineNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getWashingMachine() == 0){
            washingMashineRadioButtonYES.setChecked(true);
            washingMashineRadioButtonNO.setChecked(false);
        } else {
            washingMashineRadioButtonYES.setChecked(false);
            washingMashineRadioButtonNO.setChecked(true);
        }

        dryerRadioGroup = (RadioGroup)findViewById(R.id.dryer_filter_RadioGroup);
        dryerRadioButtonYES = (RadioButton)findViewById(R.id.dryerYES_filter_RadioButton);
        dryerRadioButtonNO = (RadioButton)findViewById(R.id.dryerNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getDryer() == 0){
            dryerRadioButtonYES.setChecked(true);
            dryerRadioButtonNO.setChecked(false);
        } else {
            dryerRadioButtonYES.setChecked(false);
            dryerRadioButtonNO.setChecked(true);
        }

        bathtubeRadioGroup = (RadioGroup)findViewById(R.id.bathTube_filter_RadioGroup);
        bathtubeRadioButtonYES = (RadioButton)findViewById(R.id.bathTubeYES_filter_RadioButton);
        bathtubeRadioButtonNO = (RadioButton)findViewById(R.id.bathTubeNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getBathtub() == 0){
            bathtubeRadioButtonYES.setChecked(true);
            bathtubeRadioButtonNO.setChecked(false);
        } else {
            bathtubeRadioButtonYES.setChecked(false);
            bathtubeRadioButtonNO.setChecked(true);
        }

        petsRadioGroup = (RadioGroup)findViewById(R.id.filter_pets_RadioGroup);
        petsRadioButtonYES = (RadioButton)findViewById(R.id.petsYES_filter_RadioButton);
        petsRadioButtonNO = (RadioButton)findViewById(R.id.petsNO_filter_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getPetsAllowed() == 0) {
            petsRadioButtonYES.setChecked(true);
            petsRadioButtonNO.setChecked(false);
        } else {
            petsRadioButtonYES.setChecked(false);
            petsRadioButtonNO.setChecked(true);
        }

        saveButton = (Button)findViewById(R.id.filter_done_2_tenant_profile);
    }

    private void sendProfile(){
        int changeMinPrice = Integer.parseInt(minPriceRangeTextView.getText().toString());
        int changeMaxPrice = Integer.parseInt(maxPriceRangeTextView.getText().toString());
        int changeMinSize = Integer.parseInt(minSizeRangeTextView.getText().toString());
        int changeMaxSize = Integer.parseInt(maxSizeRangeTextView.getText().toString());

        int changePurposeCommunity = purposeCommunityRadioGroup.getCheckedRadioButtonId() == purposeCommunityRadioButtonYES.getId() ? 0 : 1;
        int changeSmokingApartment = smokingApartmentRadioGroup.getCheckedRadioButtonId() == smokingApartmentRadioButtonYES.getId() ? 0 : 1;
        int changeBalcony = balconyRadioGroup.getCheckedRadioButtonId() == balconyRadioButtonYES.getId() ? 0 : 1;
        int changeInternet = internetRadioGroup.getCheckedRadioButtonId() == balconyRadioButtonYES.getId() ? 0 : 1;
        int changeCabelTV = cabelTVRadioGroup.getCheckedRadioButtonId() == cabelTVRadioButtonYES.getId() ? 0 : 1;
        int changeWashingMachine = washingMashineRadioGroup.getCheckedRadioButtonId() == washingMashineRadioButtonYES.getId() ? 0 : 1;
        int changeDryer = dryerRadioGroup.getCheckedRadioButtonId() == dryerRadioButtonYES.getId() ? 0 : 1;
        int changeBathtube = bathtubeRadioGroup.getCheckedRadioButtonId() == bathtubeRadioButtonYES.getId() ? 0 : 1;
        int changePets = petsRadioGroup.getCheckedRadioButtonId() == petsRadioButtonYES.getId() ? 0 : 1;

        TenantFilterSettings tenantFilterSettings = new TenantFilterSettings();
        tenantFilterSettings.setPriceFrom(changeMinPrice);
        tenantFilterSettings.setPriceTo(changeMaxPrice);
        tenantFilterSettings.setSizeFrom(changeMinSize);
        tenantFilterSettings.setSizeTo(changeMaxSize);
        tenantFilterSettings.setPurposeApartment(changePurposeCommunity);
        tenantFilterSettings.setSmokerApartment(changeSmokingApartment);
        tenantFilterSettings.setBalcony(changeBalcony);
        tenantFilterSettings.setInternet(changeInternet);
        tenantFilterSettings.setTvCable(changeCabelTV);
        tenantFilterSettings.setWashingMachine(changeWashingMachine);
        tenantFilterSettings.setDryer(changeDryer);
        tenantFilterSettings.setBathtub(changeBathtube);
        tenantFilterSettings.setPetsAllowed(changePets);
        mPresenter.sendFilterSettings(tenantFilterSettings);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void changeToMatchingActivity() {
        Toast.makeText(getApplicationContext(), "Sucess!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "sucess! Changed to MatchingActivity!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
