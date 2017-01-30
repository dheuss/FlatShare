package com.flatshare.presentation.ui.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.profile.ApartmentSettingsPresenter;
import com.flatshare.presentation.presenters.profile.impl.ApartmentSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.MainActivity;
import com.flatshare.threading.MainThreadImpl;
/**
 * Created by david on 06/12/2016.
 */
public class RoommateProfileSettingsFilterActivity extends AbstractActivity implements ApartmentSettingsPresenter.View{

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
                Toast.makeText(getApplicationContext(), "You're no allowed to save anything, sorry!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apartment_settings;
    }

    private void bindView() {
        headlineTextView = (TextView)findViewById(R.id.apartment_setting_inital);
        headlineTextView.setText("Roommate Settings");
        infoTextView = (TextView)findViewById(R.id.apartment_settings_info);
        infoTextView.setText("Here you can see all your settings so that we can find the perfect roommate for your apartment! Try it out! Maybe you get new matches! Unfortunately you're not allowed to change anything!");

        minAgeTextView = (TextView)findViewById(R.id.apartment_settings_age_range_2);
        minAgeTextView.setText(userState.getApartmentProfile().getApartmentFilterSettings().getAgeFrom()+"");
        minAgeTextView.setEnabled(false);
        maxAgeTextView = (TextView)findViewById(R.id.apartment_settings_age_range_4);
        maxAgeTextView.setText(userState.getApartmentProfile().getApartmentFilterSettings().getAgeTo()+"");
        maxAgeTextView.setEnabled(false);

        ageRangeBar = (RangeBar)findViewById(R.id.rangebar_age_range);
        ageRangeBar.setRangePinsByValue(userState.getApartmentProfile().getApartmentFilterSettings().getAgeFrom(), userState.getApartmentProfile().getApartmentFilterSettings().getAgeTo());
        ageRangeBar.setEnabled(false);

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
        maleRadioButton.setClickable(false);
        femaleRadioButton.setClickable(false);
        transgenderRadioButton.setClickable(false);

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
        yesSmokerRadioButton.setClickable(false);
        noSmokerRadioButton.setClickable(false);
        neiSmokerRadioButton.setClickable(false);

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
        yesPetsRadioButton.setClickable(false);
        noPetsRadioButton.setClickable(false);
        neiPetsRadioButton.setClickable(false);

        occupationSpiner = (Spinner)findViewById(R.id.occupationApartmentSettingsSpinner);
        occupationSpiner.setEnabled(false);

        saveButton = (Button)findViewById(R.id.done_1_apartment_settings);
        saveButton.setText("SAVE!");
    }

    @Override
    public void changeToMatchingActivity() {

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
