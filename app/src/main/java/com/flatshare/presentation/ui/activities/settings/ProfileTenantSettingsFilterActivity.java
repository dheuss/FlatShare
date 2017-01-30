package com.flatshare.presentation.ui.activities.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.MainActivity;
import com.flatshare.threading.MainThreadImpl;
/**
 * Created by david on 06/12/2016.
 */
public class ProfileTenantSettingsFilterActivity extends AbstractActivity implements TenantSettingsPresenter.View {

    private static final String TAG = "ProfileTenantSettingsFilterActivity";
    private TenantSettingsPresenter mPresenter;
    private UserState userState;

    private TextView tenantProfileSettingsHeadline;
    private TextView tenantProfileSettingsInfo;

    private RangeBar priceRangeBar;
    private TextView minPriceRangeTextView;
    private TextView maxPriceRangeTextView;

    private RangeBar sizeRangeBar;
    private TextView minSizeRangeTextView;
    private TextView maxSizeRangeTextView;

    private RadioButton purposeCommunityRadioButtonYES, purposeCommunityRadioButtonNO, purposeCommunityRadioButtonNEI;

    private RadioButton smokingApartmentRadioButtonYES, smokingApartmentRadioButtonNO, smokingApartmentRadioButtonNEI;

    private RadioButton balconyRadioButtonYES, balconyRadioButtonNO, balconyRadioButtonNEI;

    private RadioButton internetRadioButtonYES, internetRadioButtonNO, internetRadioButtonNEI;

    private RadioButton cabelTVRadioButtonYES, cabelTVRadioButtonNO, cabelTVRadioButtonNEI;

    private RadioButton washingMashineRadioButtonYES, washingMashineRadioButtonNO, washingMashineRadioButtonNEI;

    private RadioButton dryerRadioButtonYES, dryerRadioButtonNO, dryerRadioButtonNEI;

    private RadioButton bathtubeRadioButtonYES, bathtubeRadioButtonNO, bathtubeRadioButtonNEI;

    private RadioButton petsRadioButtonYES, petsRadioButtonNO, petsRadioButtonNEI;

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
        return R.layout.activity_tenant_settings;
    }

    private void bindView(){
        tenantProfileSettingsHeadline = (TextView)findViewById(R.id.tenant_setting_inital);
        tenantProfileSettingsHeadline.setText("Tenant Profile Settings");
        tenantProfileSettingsInfo = (TextView)findViewById(R.id.tenant_settings_info);
        tenantProfileSettingsInfo.setText("Here you can update all your settings so that we can find the perfect apartment for you! Try it out! Maybe you get new matches!");

        priceRangeBar = (RangeBar)findViewById(R.id.rangebar_price_range);
        minPriceRangeTextView = (TextView)findViewById(R.id.tenant_settings_price_range_2);
        maxPriceRangeTextView = (TextView)findViewById(R.id.tenant_settings_price_range_4);
        minPriceRangeTextView.setText(userState.getTenantProfile().getTenantFilterSettings().getPriceFrom()+"");
        maxPriceRangeTextView.setText(userState.getTenantProfile().getTenantFilterSettings().getPriceTo()+"");
        priceRangeBar.setRangePinsByValue(userState.getTenantProfile().getTenantFilterSettings().getPriceFrom(), userState.getTenantProfile().getTenantFilterSettings().getPriceTo());

        sizeRangeBar = (RangeBar)findViewById(R.id.rangebar_size_range);
        minSizeRangeTextView = (TextView)findViewById(R.id.tenant_settings_size_range_2);
        maxSizeRangeTextView = (TextView)findViewById(R.id.tenant_settings_size_range_4);
        minSizeRangeTextView.setText(userState.getTenantProfile().getTenantFilterSettings().getSizeFrom()+"");
        maxSizeRangeTextView.setText(userState.getTenantProfile().getTenantFilterSettings().getSizeTo()+"");
        sizeRangeBar.setRangePinsByValue(userState.getTenantProfile().getTenantFilterSettings().sizeFrom, userState.getTenantProfile().getTenantFilterSettings().getSizeTo());

        purposeCommunityRadioButtonYES = (RadioButton)findViewById(R.id.purposeCommunityYES_RadioButton);
        purposeCommunityRadioButtonNO = (RadioButton)findViewById(R.id.purposeCommunityNO_RadioButton);
        purposeCommunityRadioButtonNEI = (RadioButton)findViewById(R.id.purposeCommunityNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getPurposeApartment() == 0){
            purposeCommunityRadioButtonYES.setChecked(true);
            purposeCommunityRadioButtonNO.setChecked(false);
            purposeCommunityRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getPurposeApartment() == 1){
            purposeCommunityRadioButtonYES.setChecked(false);
            purposeCommunityRadioButtonNO.setChecked(true);
            purposeCommunityRadioButtonNEI.setChecked(false);
        } else {
            purposeCommunityRadioButtonYES.setChecked(false);
            purposeCommunityRadioButtonNO.setChecked(false);
            purposeCommunityRadioButtonNEI.setChecked(true);
        }

        smokingApartmentRadioButtonYES = (RadioButton)findViewById(R.id.smokingApartmentYES_RadioButton);
        smokingApartmentRadioButtonNO = (RadioButton)findViewById(R.id.smokingApartmentNO_RadioButton);
        smokingApartmentRadioButtonNEI = (RadioButton)findViewById(R.id.smokingApartmentNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getSmokerApartment() == 0){
            smokingApartmentRadioButtonYES.setChecked(true);
            smokingApartmentRadioButtonNO.setChecked(false);
            smokingApartmentRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getSmokerApartment() == 1){
            smokingApartmentRadioButtonYES.setChecked(false);
            smokingApartmentRadioButtonNO.setChecked(true);
            smokingApartmentRadioButtonNEI.setChecked(false);
        } else {
            smokingApartmentRadioButtonYES.setChecked(false);
            smokingApartmentRadioButtonNO.setChecked(false);
            smokingApartmentRadioButtonNEI.setChecked(true);
        }

        balconyRadioButtonYES = (RadioButton)findViewById(R.id.balconyYES_RadioButton);
        balconyRadioButtonNO = (RadioButton)findViewById(R.id.balconyNO_RadioButton);
        balconyRadioButtonNEI = (RadioButton)findViewById(R.id.balconyNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getBalcony() == 0){
            balconyRadioButtonYES.setChecked(true);
            balconyRadioButtonNO.setChecked(false);
            balconyRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getBalcony() == 1){
            balconyRadioButtonYES.setChecked(false);
            balconyRadioButtonNO.setChecked(true);
            balconyRadioButtonNEI.setChecked(false);
        } else {
            balconyRadioButtonYES.setChecked(false);
            balconyRadioButtonNO.setChecked(false);
            balconyRadioButtonNEI.setChecked(true);
        }

        internetRadioButtonYES = (RadioButton)findViewById(R.id.internetYES_RadioButton);
        internetRadioButtonNO = (RadioButton)findViewById(R.id.internetNO_RadioButton);
        internetRadioButtonNEI = (RadioButton)findViewById(R.id.internetNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getInternet() == 0){
            internetRadioButtonYES.setChecked(true);
            internetRadioButtonNO.setChecked(false);
            internetRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getInternet() == 1){
            internetRadioButtonYES.setChecked(false);
            internetRadioButtonNO.setChecked(true);
            internetRadioButtonNEI.setChecked(false);
        } else {
            internetRadioButtonYES.setChecked(false);
            internetRadioButtonNO.setChecked(false);
            internetRadioButtonNEI.setChecked(true);
        }

        cabelTVRadioButtonYES = (RadioButton)findViewById(R.id.tvYES_RadioButton);
        cabelTVRadioButtonNO = (RadioButton)findViewById(R.id.tvNO_RadioButton);
        cabelTVRadioButtonNEI = (RadioButton)findViewById(R.id.tvNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getTvCable() == 0){
            cabelTVRadioButtonYES.setChecked(true);
            cabelTVRadioButtonNO.setChecked(false);
            cabelTVRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getTvCable() == 1){
            cabelTVRadioButtonYES.setChecked(false);
            cabelTVRadioButtonNO.setChecked(true);
            cabelTVRadioButtonNEI.setChecked(false);
        } else {
            cabelTVRadioButtonYES.setChecked(false);
            cabelTVRadioButtonNO.setChecked(false);
            cabelTVRadioButtonNEI.setChecked(true);
        }

        washingMashineRadioButtonYES = (RadioButton)findViewById(R.id.washingMashineYES_RadioButton);
        washingMashineRadioButtonNO = (RadioButton)findViewById(R.id.washingMashineNO_RadioButton);
        washingMashineRadioButtonNEI = (RadioButton)findViewById(R.id.washingMashineNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getWashingMachine() == 0){
            washingMashineRadioButtonYES.setChecked(true);
            washingMashineRadioButtonNO.setChecked(false);
            washingMashineRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getWashingMachine() == 1){
            washingMashineRadioButtonYES.setChecked(false);
            washingMashineRadioButtonNO.setChecked(true);
            washingMashineRadioButtonNEI.setChecked(false);
        } else {
            washingMashineRadioButtonYES.setChecked(false);
            washingMashineRadioButtonNO.setChecked(false);
            washingMashineRadioButtonNEI.setChecked(true);
        }

        dryerRadioButtonYES = (RadioButton)findViewById(R.id.dryerYES_RadioButton);
        dryerRadioButtonNO = (RadioButton)findViewById(R.id.dryerNO_RadioButton);
        dryerRadioButtonNEI = (RadioButton)findViewById(R.id.dryerNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getDryer() == 0){
            dryerRadioButtonYES.setChecked(true);
            dryerRadioButtonNO.setChecked(false);
            dryerRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getDryer() == 1){
            dryerRadioButtonYES.setChecked(false);
            dryerRadioButtonNO.setChecked(true);
            dryerRadioButtonNEI.setChecked(false);
        } else {
            dryerRadioButtonYES.setChecked(false);
            dryerRadioButtonNO.setChecked(false);
            dryerRadioButtonNEI.setChecked(true);
        }

        bathtubeRadioButtonYES = (RadioButton)findViewById(R.id.bathTubeYES_RadioButton);
        bathtubeRadioButtonNO = (RadioButton)findViewById(R.id.bathTubeNO_RadioButton);
        bathtubeRadioButtonNEI = (RadioButton)findViewById(R.id.bathTubeNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getBathtub() == 0){
            bathtubeRadioButtonYES.setChecked(true);
            bathtubeRadioButtonNO.setChecked(false);
            bathtubeRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getBathtub() == 1){
            bathtubeRadioButtonYES.setChecked(false);
            bathtubeRadioButtonNO.setChecked(true);
            bathtubeRadioButtonNEI.setChecked(false);
        } else {
            bathtubeRadioButtonYES.setChecked(false);
            bathtubeRadioButtonNO.setChecked(false);
            bathtubeRadioButtonNEI.setChecked(true);
        }

        petsRadioButtonYES = (RadioButton)findViewById(R.id.petsYES_RadioButton);
        petsRadioButtonNO = (RadioButton)findViewById(R.id.petsNO_RadioButton);
        petsRadioButtonNEI = (RadioButton)findViewById(R.id.petsNEI_RadioButton);
        if (userState.getTenantProfile().getTenantFilterSettings().getPetsAllowed() == 0) {
            petsRadioButtonYES.setChecked(true);
            petsRadioButtonNO.setChecked(false);
            petsRadioButtonNEI.setChecked(false);
        } else if (userState.getTenantProfile().getTenantFilterSettings().getPetsAllowed() == 1){
            petsRadioButtonYES.setChecked(false);
            petsRadioButtonNO.setChecked(true);
            petsRadioButtonNEI.setChecked(false);
        } else {
            petsRadioButtonYES.setChecked(false);
            petsRadioButtonNO.setChecked(false);
            petsRadioButtonNEI.setChecked(true);
        }

        saveButton = (Button)findViewById(R.id.done_2_tenant_profile);
        saveButton.setText("SAVE!");
    }

    private void sendProfile(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View mView = layoutInflater.inflate(R.layout.activity_popup, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final TextView popUpTextView = (TextView) mView.findViewById(R.id.popup_TextView);
        popUpTextView.setText("Do you want to save your settings?");


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int changeMinPrice = Integer.parseInt(minPriceRangeTextView.getText().toString());
                        int changeMaxPrice = Integer.parseInt(maxPriceRangeTextView.getText().toString());
                        int changeMinSize = Integer.parseInt(minSizeRangeTextView.getText().toString());
                        int changeMaxSize = Integer.parseInt(maxSizeRangeTextView.getText().toString());

                        int isPurposeApartment = 2;
                        int isSmokerApartment = 2;
                        int isBalcony = 2;
                        int isInternet = 2;
                        int isTVCable = 2;
                        int isWashingMashine = 2;
                        int isDryer = 2;
                        int isBathtube = 2;
                        int isPetAllowed = 2;

                        if (purposeCommunityRadioButtonYES.isChecked()){
                            isPurposeApartment = 0;
                        }
                        if (purposeCommunityRadioButtonNO.isChecked()){
                            isPurposeApartment = 1;
                        }
                        if (purposeCommunityRadioButtonNEI.isChecked()){
                            isPurposeApartment = 2;
                        }
                        if (smokingApartmentRadioButtonYES.isChecked()){
                            isSmokerApartment = 0;
                        }
                        if (smokingApartmentRadioButtonNO.isChecked()){
                            isSmokerApartment = 1;
                        }
                        if (smokingApartmentRadioButtonNEI.isChecked()){
                            isSmokerApartment = 2;
                        }
                        if (balconyRadioButtonYES.isChecked()){
                            isBalcony = 0;
                        }
                        if (balconyRadioButtonNO.isChecked()){
                            isBalcony = 1;
                        }
                        if (balconyRadioButtonNEI.isChecked()){
                            isBalcony = 2;
                        }
                        if (internetRadioButtonYES.isChecked()){
                            isInternet = 0;
                        }
                        if (internetRadioButtonNO.isChecked()){
                            isInternet = 1;
                        }
                        if (internetRadioButtonNEI.isChecked()){
                            isInternet = 2;
                        }
                        if (cabelTVRadioButtonYES.isChecked()){
                            isTVCable = 0;
                        }
                        if (cabelTVRadioButtonNO.isChecked()){
                            isTVCable = 1;
                        }
                        if (cabelTVRadioButtonNEI.isChecked()){
                            isTVCable = 2;
                        }
                        if (washingMashineRadioButtonYES.isChecked()){
                            isWashingMashine = 0;
                        }
                        if (washingMashineRadioButtonNO.isChecked()){
                            isWashingMashine = 1;
                        }
                        if (washingMashineRadioButtonNEI.isChecked()){
                            isWashingMashine = 2;
                        }
                        if (dryerRadioButtonYES.isChecked()){
                            isDryer = 0;
                        }
                        if (dryerRadioButtonNO.isChecked()){
                            isDryer = 1;
                        }
                        if (dryerRadioButtonNEI.isChecked()){
                            isDryer = 2;
                        }
                        if (bathtubeRadioButtonYES.isChecked()){
                            isBathtube = 0;
                        }
                        if (bathtubeRadioButtonNO.isChecked()){
                            isBathtube = 1;
                        }
                        if (bathtubeRadioButtonNEI.isChecked()){
                            isBathtube = 2;
                        }
                        if (petsRadioButtonYES.isChecked()){
                            isPetAllowed = 0;
                        }
                        if (petsRadioButtonNO.isChecked()){
                            isPetAllowed = 1;
                        }
                        if (petsRadioButtonNEI.isChecked()){
                            isPetAllowed = 2;
                        }

                        TenantFilterSettings tenantFilterSettings = new TenantFilterSettings();
                        tenantFilterSettings.setPriceFrom(changeMinPrice);
                        tenantFilterSettings.setPriceTo(changeMaxPrice);
                        tenantFilterSettings.setSizeFrom(changeMinSize);
                        tenantFilterSettings.setSizeTo(changeMaxSize);
                        tenantFilterSettings.setPurposeApartment(isPurposeApartment);
                        tenantFilterSettings.setSmokerApartment(isSmokerApartment);
                        tenantFilterSettings.setBalcony(isBalcony);
                        tenantFilterSettings.setInternet(isInternet);
                        tenantFilterSettings.setTvCable(isTVCable);
                        tenantFilterSettings.setWashingMachine(isWashingMashine);
                        tenantFilterSettings.setDryer(isDryer);
                        tenantFilterSettings.setBathtub(isBathtube);
                        tenantFilterSettings.setPetsAllowed(isPetAllowed);

                        mPresenter.sendFilterSettings(tenantFilterSettings);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please click save!", Toast.LENGTH_SHORT).show();
    }
}
