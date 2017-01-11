package com.flatshare.presentation.ui.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.presentation.presenters.settings.ProfileSettingsPresenter;
import com.flatshare.presentation.presenters.settings.impl.ProfileSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfilSettingsActivity extends AbstractActivity implements ProfileSettingsPresenter.View {

    private ImageButton settingsButton;
    private ImageButton matchingActivityButton;

    private EditText changeNameEditText;
    private EditText changeEmailEditText;
    private EditText changeAgeEditText;

    private RadioGroup changeGenderRadioGroup;
    private RadioButton changeGenderMaleRadioButton;
    private RadioButton changeGenderFemaleRadioButton;

    private RadioGroup changeSmokerRadioGroup;
    private RadioButton changeSmokerYesRadioButton;
    private RadioButton changeSmokerNoRadioButton;

    private RadioGroup changePetsRadioGroup;
    private RadioButton changePetsYesRadioButton;
    private RadioButton changePetsNoRadioButton;

    private Spinner changeOccupationSpinner;

    private EditText changeInfoEditText;

    private Spinner changeDurationOfStaySpinner;

    private Button saveChangesButton;

    private static final String TAG = "ProfilSettingsActivity";

    private ProfileSettingsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        Log.d(TAG, "inside onCreate(), creating presenter fr this view");

        mPresenter = new ProfileSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ProfilSettingsActivity.this, SettingsActivity.class));
            }
        });

        matchingActivityButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ProfilSettingsActivity.this, MatchingActivity.class));
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfilSettingsActivity.this.sendProfile();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profil_settings;
    }

    private void sendProfile() {

        String changeName = changeNameEditText.getText().toString();
//        String changeEmail = changeEmailEditText.getText().toString();
//        int changeAge = Integer.parseInt(changeAgeEditText.getText().toString());;
//        int changGender = changeGenderRadioGroup.getCheckedRadioButtonId() == changeGenderMaleRadioButton.getId() ? 0 : 1;
//        boolean changeSmoker = changeSmokerRadioGroup.getCheckedRadioButtonId() == changeSmokerYesRadioButton.getId();;
//        boolean changePets = changePetsRadioGroup.getCheckedRadioButtonId() == changePetsYesRadioButton.getId();;
//        String changeOccupation = changeOccupationSpinner.getSelectedItem().toString();;
//        String changeInfo = changeInfoEditText.getText().toString();;
//        int changeDuration = Integer.parseInt(changeDurationOfStaySpinner.getSelectedItem().toString());;

        TenantProfile tenantProfile = new TenantProfile();
        tenantProfile.setFirstName(changeName);
//        tenantProfile.setEmail(changeEmail);
//        tenantProfile.setAge(changeAge);
//        tenantProfile.setGender(changGender);
//        tenantProfile.setSmoker(changeSmoker);
//        tenantProfile.setPets(changePets);
//        tenantProfile.setOccupation(changeOccupation);
//        tenantProfile.setShortBio(changeInfo);
//        tenantProfile.setDurationOfStay(changeDuration);
        mPresenter.changeProfile(tenantProfile);
    }

    private void bindView(){
        settingsButton = (ImageButton)findViewById(R.id.settingsBtn);
        matchingActivityButton = (ImageButton)findViewById(R.id.couchBtn);

        changeNameEditText = (EditText)findViewById(R.id.changeNameProfileSettingsEditText);
        changeEmailEditText = (EditText)findViewById(R.id.changeEmailProfileSettingsEditText);
        changeAgeEditText = (EditText)findViewById(R.id.changeAgeProfileSettingsEditText);

        changeGenderRadioGroup = (RadioGroup)findViewById(R.id.changeGenderProfileSettingsEditTextRadioGroup);
        changeGenderMaleRadioButton = (RadioButton)findViewById(R.id.changeGenderMaleProfileSettingsRadioButton);
        changeGenderFemaleRadioButton = (RadioButton)findViewById(R.id.changeGenderFemaleProlfileSettingsButton);

        changeSmokerRadioGroup = (RadioGroup)findViewById(R.id.changeSmokerProfileSettingsRadioGroup);
        changeSmokerYesRadioButton = (RadioButton)findViewById(R.id.changeSmokerYesProfileSettingsRadioButton);
        changeSmokerNoRadioButton = (RadioButton)findViewById(R.id.changeSmokerNoProfileSettingsRadioButton);

        changePetsRadioGroup = (RadioGroup)findViewById(R.id.changePetsProfileSettingsRadioGroup);
        changePetsYesRadioButton = (RadioButton)findViewById(R.id.changePetsYesProfileSettingsRadioButton);
        changePetsNoRadioButton = (RadioButton)findViewById(R.id.changePetsNOProfileSettingsRadioButton);

        changeOccupationSpinner = (Spinner)findViewById(R.id.changeOccupationProfileSettingsSpinner);

        changeInfoEditText = (EditText)findViewById(R.id.changeInfoProfileSettingsEditText);

        changeDurationOfStaySpinner = (Spinner)findViewById(R.id.changeDurationOfStayProfileSettingsSpinner);

        saveChangesButton = (Button)findViewById(R.id.saveChangesProfileSettingsButton);
    }

    @Override
    public void changeToMatchingActivity() {
        Log.d(TAG, "success! changed to MatchingActivity!");
        Intent intent = new Intent(this, MatchingActivity.class);
        startActivity(intent);
    }

    @Override
    public void uploadSucces() {

    }

    @Override
    public void showError(String message) {

    }
}
