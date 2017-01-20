package com.flatshare.presentation.ui.activities.settings;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.profile.ApartmentSettingsPresenter;
import com.flatshare.presentation.presenters.profile.impl.ApartmentSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfileApartmentSettingsFilterActivity extends AbstractActivity implements ApartmentSettingsPresenter.View {

    private TextView headlineTextView;
    private TextView infoTextView;
    private TextView minAgeTextView;
    private TextView maxAgeTextView;

    private RangeBar priceRangeBar;

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton, transgenderRadioButton;

    private RadioGroup smokerRadioGroup;
    private RadioButton yesSmokerRadioButton, noSmokerRadioButton, neiSmokerRadioButton;

    private RadioGroup petsRadioGroup;
    private RadioButton yesPetsRadioButton, noPetsRadioButton, neiPetsRadioButton;

    private Spinner occupationSpiner;
    private Spinner durationSpinner;

    private Button saveButton;

    private ApartmentSettingsPresenter mPresenter;
    private UserState userState;
    private static final String TAG = "ProfileApartmentSettingsFilterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new ApartmentSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apartment_settings;
    }

    private void sendFilterSettings() {

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

        priceRangeBar = (RangeBar)findViewById(R.id.rangebar_age_range);
        priceRangeBar.setRangePinsByValue(userState.getApartmentProfile().getApartmentFilterSettings().getAgeFrom(), userState.getApartmentProfile().getApartmentFilterSettings().getAgeTo());

        genderRadioGroup = (RadioGroup)findViewById(R.id.genderApartmentSettingsRadioGroup);
        maleRadioButton = (RadioButton)findViewById(R.id.genderMaleApartmentSettingsRadioButton);
        femaleRadioButton = (RadioButton)findViewById(R.id.genderFemaleApartmentSettingsButton);
        transgenderRadioButton = (RadioButton)findViewById(R.id.genderALLApartmentSettingsButton);
        if (userState.getApartmentProfile().getApartmentFilterSettings().getGender() == 0){

        } else if (userState.getApartmentProfile().getApartmentFilterSettings().getGender() == 1){

        } else {

        }


        smokerRadioGroup = (RadioGroup)findViewById(R.id.smokerApartmentSettingsRadioGroup);
        yesSmokerRadioButton = (RadioButton)findViewById(R.id.smokerYesApartmentSettingsRadioButton);
        noSmokerRadioButton = (RadioButton)findViewById(R.id.smokerNoApartmentSettingsButton);
        neiSmokerRadioButton = (RadioButton)findViewById(R.id.smokerALLApartmentSettingsButton);
        if (userState.getApartmentProfile().getApartmentFilterSettings().getSmoker() == 0){

        }

        petsRadioGroup = (RadioGroup)findViewById(R.id.petsApartmentSettingsRadioGroup);
        yesPetsRadioButton = (RadioButton)findViewById(R.id.petYesApartmentSettingsRadioButton);
        noPetsRadioButton = (RadioButton)findViewById(R.id.petNoApartmentSettingsButton);
        neiPetsRadioButton = (RadioButton)findViewById(R.id.petALLApartmentSettingsButton);

        occupationSpiner = (Spinner)findViewById(R.id.occupationApartmentSettingsSpinner);
        durationSpinner = (Spinner)findViewById(R.id.durationOfStayApartmentSettingsSpinner);

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
}
