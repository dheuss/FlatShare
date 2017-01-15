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
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.settings.ProfileSettingsPresenter;
import com.flatshare.presentation.presenters.settings.impl.ProfileSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfileTenantSettingsActivity extends AbstractActivity implements ProfileSettingsPresenter.View {

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

    private Button changeFilterSettingsButton;
    private Button saveChangesButton;

    private static final String TAG = "ProfileTenantSettingsActivity";

    private ProfileSettingsPresenter mPresenter;

    private UserState userState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);

        bindView();


        System.out.println("ClassificationID: " + userState.getPrimaryUserProfile().getClassificationId());

        Log.d(TAG, "inside onCreate(), creating presenter fr this view");

        mPresenter = new ProfileSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ProfileTenantSettingsActivity.this, SettingsActivity.class));
            }
        });

        changeFilterSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileTenantSettingsActivity.this, ProfileTenantSettingsFilterActivity.class));
            }
        });

        matchingActivityButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ProfileTenantSettingsActivity.this, MatchingActivity.class));
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileTenantSettingsActivity.this.sendProfile();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
            return R.layout.activity_profile_tenant_settings;
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
        changeNameEditText.setText(userState.getTenantProfile().getFirstName());
        changeEmailEditText = (EditText)findViewById(R.id.changeEmailProfileSettingsEditText);
        changeEmailEditText.setText(userState.getTenantProfile().getEmail());
        changeAgeEditText = (EditText)findViewById(R.id.changeAgeProfileSettingsEditText);
        changeAgeEditText.setText(userState.getTenantProfile().getAge()+"");

        changeGenderRadioGroup = (RadioGroup)findViewById(R.id.changeGenderProfileSettingsEditTextRadioGroup);
        changeGenderMaleRadioButton = (RadioButton)findViewById(R.id.changeGenderMaleProfileSettingsRadioButton);
        changeGenderFemaleRadioButton = (RadioButton)findViewById(R.id.changeGenderFemaleProlfileSettingsButton);
        if (userState.getTenantProfile().getGender() == 0) {
            changeGenderMaleRadioButton.setChecked(true);
            changeGenderFemaleRadioButton.setChecked(false);
        } else {
            changeGenderMaleRadioButton.setChecked(false);
            changeGenderFemaleRadioButton.setChecked(true);
        }

        changeSmokerRadioGroup = (RadioGroup)findViewById(R.id.changeSmokerProfileSettingsRadioGroup);
        changeSmokerYesRadioButton = (RadioButton)findViewById(R.id.changeSmokerYesProfileSettingsRadioButton);
        changeSmokerNoRadioButton = (RadioButton)findViewById(R.id.changeSmokerNoProfileSettingsRadioButton);
        if (userState.getTenantProfile().isSmoker()){
            changeSmokerYesRadioButton.setChecked(true);
            changeSmokerNoRadioButton.setChecked(false);
        } else {
            changeSmokerYesRadioButton.setChecked(false);
            changeSmokerNoRadioButton.setChecked(true);
        }

        changePetsRadioGroup = (RadioGroup)findViewById(R.id.changePetsProfileSettingsRadioGroup);
        changePetsYesRadioButton = (RadioButton)findViewById(R.id.changePetsYesProfileSettingsRadioButton);
        changePetsNoRadioButton = (RadioButton)findViewById(R.id.changePetsNOProfileSettingsRadioButton);
        if (userState.getTenantProfile().hasPets()){
            changePetsYesRadioButton.setChecked(true);
            changePetsNoRadioButton.setChecked(false);
        } else {
            changePetsYesRadioButton.setChecked(false);
            changePetsNoRadioButton.setChecked(true);
        }

        changeOccupationSpinner = (Spinner)findViewById(R.id.changeOccupationProfileSettingsSpinner);
        if (userState.getTenantProfile().getOccupation().equals("Student")){
            changeOccupationSpinner.setSelection(0);
        } else if (userState.getTenantProfile().getOccupation().equals("Employee")){
            changeOccupationSpinner.setSelection(1);
        } else if (userState.getTenantProfile().getOccupation().equals("Lowly emplyed")){
            changeOccupationSpinner.setSelection(2);
        } else if (userState.getTenantProfile().getOccupation().equals("Official")) {
            changeOccupationSpinner.setSelection(3);
        } else if (userState.getTenantProfile().getOccupation().equals("No Job")) {
            changeOccupationSpinner.setSelection(4);
        } else {
            changeOccupationSpinner.setSelection(0);
        }

        changeInfoEditText = (EditText)findViewById(R.id.changeInfoProfileSettingsEditText);
        changeInfoEditText.setText(userState.getTenantProfile().getShortBio());

        changeDurationOfStaySpinner = (Spinner)findViewById(R.id.changeDurationOfStayProfileSettingsSpinner);
        if (userState.getTenantProfile().getDurationOfStay() == 0){
            changeOccupationSpinner.setSelection(0);
        } else if (userState.getTenantProfile().getDurationOfStay() == 1){
            changeOccupationSpinner.setSelection(1);
        } else if (userState.getTenantProfile().getDurationOfStay() == 2){
            changeOccupationSpinner.setSelection(2);
        } else if (userState.getTenantProfile().getDurationOfStay() == 3) {
            changeOccupationSpinner.setSelection(3);
        } else if (userState.getTenantProfile().getDurationOfStay() == 4) {
            changeOccupationSpinner.setSelection(4);
        } else if (userState.getTenantProfile().getDurationOfStay() == 5){
            changeOccupationSpinner.setSelection(5);
        } else {
            changeOccupationSpinner.setSelection(0);
        }

        changeFilterSettingsButton = (Button)findViewById(R.id.changeFilterProfileSettingsButton);
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
