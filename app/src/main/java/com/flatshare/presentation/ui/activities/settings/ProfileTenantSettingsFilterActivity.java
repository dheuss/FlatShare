package com.flatshare.presentation.ui.activities.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.settings.ProfileTenantSettingsFilterPresenter;
import com.flatshare.presentation.presenters.settings.impl.ProfileTenantSettingsFilterPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfileTenantSettingsFilterActivity extends AbstractActivity implements ProfileTenantSettingsFilterPresenter.View {

    private static final String TAG = "ProfileTenantSettingsFilterActivity";
    private ProfileTenantSettingsFilterPresenter mPresenter;
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

        mPresenter = new ProfileTenantSettingsFilterPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile_tenant_settings_filter;
    }

    private void bindView(){
        priceRangeBar = (RangeBar)findViewById(R.id.rangebar_filter_price_range);
        minPriceRangeTextView = (TextView)findViewById(R.id.tenant_filter_settings_price_range_2);
        maxPriceRangeTextView = (TextView)findViewById(R.id.tenant_filter_settings_price_range_4);

        sizeRangeBar = (RangeBar)findViewById(R.id.rangebar_filter_size_range);
        minSizeRangeTextView = (TextView)findViewById(R.id.tenant_filter_settings_size_range_2);
        maxSizeRangeTextView = (TextView)findViewById(R.id.tenant_filter_settings_size_range_4);

        purposeCommunityRadioGroup = (RadioGroup)findViewById(R.id.purposeCommunity_filter_RadioGroup);
        purposeCommunityRadioButtonYES = (RadioButton)findViewById(R.id.purposeCommunityYES_filter_RadioButton);
        purposeCommunityRadioButtonNO = (RadioButton)findViewById(R.id.purposeCommunityNO_filter_RadioButton);

        smokingApartmentRadioGroup = (RadioGroup)findViewById(R.id.smokingApartment_filter_RadioGroup);
        smokingApartmentRadioButtonYES = (RadioButton)findViewById(R.id.smokingApartmentYES_filter_RadioButton);
        smokingApartmentRadioButtonNO = (RadioButton)findViewById(R.id.smokingApartmentNO_filter_RadioButton);

        balconyRadioGroup = (RadioGroup)findViewById(R.id.balcony_filter_RadioGroup);
        balconyRadioButtonYES = (RadioButton)findViewById(R.id.balconyYES_filter_RadioButton);
        balconyRadioButtonNO = (RadioButton)findViewById(R.id.balconyNO_filter_RadioButton);

        internetRadioGroup = (RadioGroup)findViewById(R.id.internet_filter_RadioGroup);
        internetRadioButtonYES = (RadioButton)findViewById(R.id.internetYES_filter_RadioButton);
        internetRadioButtonNO = (RadioButton)findViewById(R.id.internetNO_filter_RadioButton);

        cabelTVRadioGroup = (RadioGroup)findViewById(R.id.tv_filter_RadioGroup);
        cabelTVRadioButtonYES = (RadioButton)findViewById(R.id.tvYES_filter_RadioButton);
        cabelTVRadioButtonNO = (RadioButton)findViewById(R.id.tvNO_filter_RadioButton);

        washingMashineRadioGroup = (RadioGroup)findViewById(R.id.washingMashine_filter_RadioGroup);
        washingMashineRadioButtonYES = (RadioButton)findViewById(R.id.washingMashineYES_filter_RadioButton);
        washingMashineRadioButtonNO = (RadioButton)findViewById(R.id.washingMashineNO_filter_RadioButton);

        dryerRadioGroup = (RadioGroup)findViewById(R.id.dryer_filter_RadioGroup);
        dryerRadioButtonYES = (RadioButton)findViewById(R.id.dryerYES_filter_RadioButton);
        dryerRadioButtonNO = (RadioButton)findViewById(R.id.dryerNO_filter_RadioButton);

        bathtubeRadioGroup = (RadioGroup)findViewById(R.id.bathTube_filter_RadioGroup);
        bathtubeRadioButtonYES = (RadioButton)findViewById(R.id.bathTubeYES_filter_RadioButton);
        bathtubeRadioButtonNO = (RadioButton)findViewById(R.id.bathTubeNO_filter_RadioButton);

        petsRadioGroup = (RadioGroup)findViewById(R.id.filter_pets_RadioGroup);
        petsRadioButtonYES = (RadioButton)findViewById(R.id.petsYES_filter_RadioButton);
        petsRadioButtonNO = (RadioButton)findViewById(R.id.petsNO_filter_RadioButton);

        saveButton = (Button)findViewById(R.id.filter_done_2_tenant_profile);
    }

    private void sendProfile(){

    }

    @Override
    public void changeToProfileTenantSettingsActivity() {
        Log.d(TAG, "success! changed to MatchingActivity!");
        Intent intent = new Intent(this, ProfileTenantSettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {

    }
}
