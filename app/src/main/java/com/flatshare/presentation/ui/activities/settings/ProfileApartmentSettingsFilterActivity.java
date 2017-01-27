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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.profile.ApartmentSettingsPresenter;
import com.flatshare.presentation.presenters.profile.impl.ApartmentSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.MainActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfileApartmentSettingsFilterActivity extends AbstractActivity implements ApartmentSettingsPresenter.View {

    private TextView headlineTextView;
    private TextView infoTextView;
    private TextView minAgeTextView;
    private TextView maxAgeTextView;

    private RangeBar ageRangeBar;

    private RadioButton maleRadioButton, femaleRadioButton, transgenderRadioButton;

    private RadioButton yesSmokerRadioButton, noSmokerRadioButton, neiSmokerRadioButton;

    private RadioButton yesPetsRadioButton, noPetsRadioButton, neiPetsRadioButton;

    private Spinner occupationSpiner;

    private Button saveButton;

    private ApartmentSettingsPresenter mPresenter;
    private UserState userState;
    private static final String TAG = "ProfileApartmentSettingsFilterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);

        bindView();

        minAgeTextView.setText(ageRangeBar.getLeftPinValue());
        maxAgeTextView.setText(ageRangeBar.getRightPinValue());

        ageRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                minAgeTextView.setText(ageRangeBar.getLeftPinValue());
                maxAgeTextView.setText(ageRangeBar.getRightPinValue());
            }
        });

        mPresenter = new ApartmentSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFilterSettings();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apartment_settings;
    }

    private void sendFilterSettings() {
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
                        int minAge = Integer.parseInt(minAgeTextView.getText().toString());
                        int maxAge = Integer.parseInt(maxAgeTextView.getText().toString());

                        int gender = 2;
                        int smoker = 2;
                        int pets = 2;

                        if (maleRadioButton.isChecked()){
                            gender = 0;
                        }
                        if (femaleRadioButton.isChecked()){
                            gender = 1;
                        }
                        if (transgenderRadioButton.isChecked()){
                            gender = 2;
                        }
                        if (yesSmokerRadioButton.isChecked()){
                            smoker = 0;
                        }
                        if (noSmokerRadioButton.isChecked()){
                            smoker = 1;
                        }
                        if (neiSmokerRadioButton.isChecked()){
                            smoker = 2;
                        }
                        if (yesPetsRadioButton.isChecked()){
                            pets = 0;
                        }
                        if (noPetsRadioButton.isChecked()){
                            pets = 1;
                        }
                        if (neiSmokerRadioButton.isChecked()){
                            pets = 2;
                        }

                        String occupation = occupationSpiner.getSelectedItem().toString();

                        ApartmentFilterSettings apartmentFilterSettings = new ApartmentFilterSettings();

                        apartmentFilterSettings.setAgeFrom(minAge);
                        apartmentFilterSettings.setAgeTo(maxAge);
                        apartmentFilterSettings.setGender(gender);
                        apartmentFilterSettings.setSmoker(smoker);
                        apartmentFilterSettings.setPetsAllowed(pets);
                        apartmentFilterSettings.setOccupation(occupation);

                        mPresenter.sendFilterSettings(apartmentFilterSettings);
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

    private void bindView() {
        headlineTextView = (TextView)findViewById(R.id.apartment_setting_inital);
        headlineTextView.setText("Apartment Settings");
        infoTextView = (TextView)findViewById(R.id.apartment_settings_info);
        infoTextView.setText("Here you can update all your settings so that we can find the perfect roommate for your apartment! Try it out! Maybe you get new matches!");
        minAgeTextView = (TextView)findViewById(R.id.apartment_settings_age_range_2);
        minAgeTextView.setText(userState.getApartmentProfile().getApartmentFilterSettings().getAgeFrom()+"");
        maxAgeTextView = (TextView)findViewById(R.id.apartment_settings_age_range_4);
        maxAgeTextView.setText(userState.getApartmentProfile().getApartmentFilterSettings().getAgeTo()+"");

        ageRangeBar = (RangeBar)findViewById(R.id.rangebar_age_range);
        ageRangeBar.setRangePinsByValue(userState.getApartmentProfile().getApartmentFilterSettings().getAgeFrom(), userState.getApartmentProfile().getApartmentFilterSettings().getAgeTo());

        maleRadioButton = (RadioButton)findViewById(R.id.genderMaleApartmentSettingsRadioButton);
        femaleRadioButton = (RadioButton)findViewById(R.id.genderFemaleApartmentSettingsButton);
        transgenderRadioButton = (RadioButton)findViewById(R.id.genderALLApartmentSettingsButton);
        if (userState.getApartmentProfile().getApartmentFilterSettings().getGender() == 0){
            maleRadioButton.setChecked(true);
            femaleRadioButton.setChecked(false);
            transgenderRadioButton.setChecked(false);
        } else if (userState.getApartmentProfile().getApartmentFilterSettings().getGender() == 1){
            maleRadioButton.setChecked(false);
            femaleRadioButton.setChecked(true);
            transgenderRadioButton.setChecked(false);
        } else {
            maleRadioButton.setChecked(false);
            femaleRadioButton.setChecked(false);
            transgenderRadioButton.setChecked(true);
        }

        yesSmokerRadioButton = (RadioButton)findViewById(R.id.smokerYesApartmentSettingsRadioButton);
        noSmokerRadioButton = (RadioButton)findViewById(R.id.smokerNoApartmentSettingsButton);
        neiSmokerRadioButton = (RadioButton)findViewById(R.id.smokerALLApartmentSettingsButton);
        if (userState.getApartmentProfile().getApartmentFilterSettings().getSmoker() == 0){
            yesSmokerRadioButton.setChecked(true);
            noSmokerRadioButton.setChecked(false);
            neiSmokerRadioButton.setChecked(false);
        } else if (userState.getApartmentProfile().getApartmentFilterSettings().getSmoker() == 1){
            yesSmokerRadioButton.setChecked(false);
            noSmokerRadioButton.setChecked(true);
            neiSmokerRadioButton.setChecked(false);
        } else {
            yesSmokerRadioButton.setChecked(false);
            noSmokerRadioButton.setChecked(false);
            neiSmokerRadioButton.setChecked(true);
        }

        yesPetsRadioButton = (RadioButton)findViewById(R.id.petYesApartmentSettingsRadioButton);
        noPetsRadioButton = (RadioButton)findViewById(R.id.petNoApartmentSettingsButton);
        neiPetsRadioButton = (RadioButton)findViewById(R.id.petALLApartmentSettingsButton);
        if (userState.getApartmentProfile().getApartmentFilterSettings().getPetsAllowed() == 0){
            yesPetsRadioButton.setChecked(true);
            noPetsRadioButton.setChecked(false);
            neiPetsRadioButton.setChecked(false);
        } else if (userState.getApartmentProfile().getApartmentFilterSettings().getPetsAllowed() == 1){
            yesPetsRadioButton.setChecked(false);
            noPetsRadioButton.setChecked(true);
            neiPetsRadioButton.setChecked(false);
        } else {
            yesPetsRadioButton.setChecked(false);
            noPetsRadioButton.setChecked(false);
            neiPetsRadioButton.setChecked(true);
        }

        occupationSpiner = (Spinner)findViewById(R.id.occupationApartmentSettingsSpinner);

        saveButton = (Button)findViewById(R.id.done_1_apartment_settings);
        saveButton.setText("SAVE!");
    }

    @Override
    public void changeToMatchingActivity() {
        Toast.makeText(getApplicationContext(), "Sucess!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "sucess! Changed to MatchingActivity!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please click save!", Toast.LENGTH_SHORT).show();
    }
}
