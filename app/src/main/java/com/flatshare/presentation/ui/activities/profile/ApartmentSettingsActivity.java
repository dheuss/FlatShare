package com.flatshare.presentation.ui.activities.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.domain.datatypes.db.filters.ApartmentFilterSettings;
import com.flatshare.presentation.presenters.profile.ApartmentSettingsPresenter;
import com.flatshare.presentation.presenters.profile.impl.ApartmentSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

/**
 * Created by Arber on 16/12/2016.
 */
public class ApartmentSettingsActivity extends AbstractActivity implements ApartmentSettingsPresenter.View{

    private TextView minAgeTextView;
    private TextView maxAgeTextView;
    private RangeBar ageRangeBar;

    private RadioGroup genderRadioGroup;
    private RadioButton maleGenderRadioButton;
    private RadioButton femaleGenderRadioButton;
    private RadioButton allGenderRadioButton;

    private RadioGroup smokerRadioGroup;
    private RadioButton yesSmokerRadioGroup;
    private RadioButton noSmokerRadioButton;
    private RadioButton allSmokerRadioButton;

    private RadioGroup petsRadioGroup;
    private RadioButton yesPetsRadioButton;
    private RadioButton noPetsRadioButton;
    private RadioButton allPetsRadioButton;

    private Spinner occupationSpinner;

    private Spinner durationOfStaySpinner;

    private Button doneButton;

    private ApartmentSettingsPresenter mPresenter;
    private static final String TAG = "TenantProfileAct";
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new ApartmentSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        minAgeTextView.setText(ageRangeBar.getLeftPinValue());
        maxAgeTextView.setText(ageRangeBar.getRightPinValue());

        ageRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar,
                                              int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                minAgeTextView.setText(ageRangeBar.getLeftPinValue());
                maxAgeTextView.setText(ageRangeBar.getRightPinValue());
            }

        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ApartmentSettingsActivity.this.sendFilterSettings();
                startActivity(new Intent(ApartmentSettingsActivity.this, MatchingActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apartment_settings;
    }

    private void sendFilterSettings() {
        int minAge =  Integer.parseInt(minAgeTextView.getText().toString());;
        int maxAge = Integer.parseInt(maxAgeTextView.getText().toString());;
//        int gender;
//        int smoker;
//        int pets;
//        String occupation;
//        int durationOfStay;

        ApartmentFilterSettings apartmentFilterSettings = new ApartmentFilterSettings();
        apartmentFilterSettings.setAgeFrom(minAge);
        apartmentFilterSettings.setAgeTo(maxAge);
//        apartmentFilterSettings.setGender(gender);
//        apartmentFilterSettings.setSmoker(smoker);
//        apartmentFilterSettings.setPetsAllowed(pets);
        mPresenter.sendFilterSettings(apartmentFilterSettings);
    }

    private void bindView() {
        minAgeTextView = (TextView)findViewById(R.id.apartment_settings_age_range_2);
        maxAgeTextView = (TextView)findViewById(R.id.apartment_settings_age_range_4);
        ageRangeBar = (RangeBar)findViewById(R.id.rangebar_age_range);

        genderRadioGroup = (RadioGroup)findViewById(R.id.genderApartmentSettingsRadioGroup);
        maleGenderRadioButton = (RadioButton)findViewById(R.id.genderMaleApartmentSettingsRadioButton);
        femaleGenderRadioButton = (RadioButton)findViewById(R.id.genderFemaleApartmentSettingsButton);
        allGenderRadioButton = (RadioButton)findViewById(R.id.genderALLApartmentSettingsButton);

        smokerRadioGroup = (RadioGroup)findViewById(R.id.smokerApartmentSettingsRadioGroup);
        yesSmokerRadioGroup = (RadioButton)findViewById(R.id.smokerYesApartmentSettingsRadioButton);
        noSmokerRadioButton = (RadioButton)findViewById(R.id.smokerNoApartmentSettingsButton);
        allSmokerRadioButton = (RadioButton)findViewById(R.id.smokerALLApartmentSettingsButton);

        petsRadioGroup = (RadioGroup)findViewById(R.id.petsApartmentSettingsRadioGroup);
        yesPetsRadioButton = (RadioButton)findViewById(R.id.petYesApartmentSettingsRadioButton);
        noPetsRadioButton = (RadioButton)findViewById(R.id.petNoApartmentSettingsButton);
        allPetsRadioButton = (RadioButton)findViewById(R.id.petALLApartmentSettingsButton);

        occupationSpinner = (Spinner)findViewById(R.id.occupationApartmentSettingsSpinner);

        durationOfStaySpinner = (Spinner)findViewById(R.id.durationOfStayApartmentSettingsSpinner);

        doneButton = (Button)findViewById(R.id.done_1_apartment_settings);
    }

    @Override
    public void changeToMatchingActivity() {
        Log.d(TAG, "success! changed to MatchingActivity!");
        Intent intent = new Intent(this, MatchingActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
