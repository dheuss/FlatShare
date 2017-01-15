package com.flatshare.presentation.ui.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.settings.ProfileTenantSettingsPresenter;
import com.flatshare.presentation.presenters.settings.impl.ProfileTenantSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfileTenantSettingsActivity extends AbstarctFragmentAcivity implements ProfileTenantSettingsPresenter.View {

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

    private ProfileTenantSettingsPresenter mPresenter;

    private UserState userState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_profile_tenant_settings, container, false);
        bindView(view);

        mPresenter = new ProfileTenantSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        changeFilterSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileTenantSettingsFilterActivity.class));
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileTenantSettingsActivity.this.sendProfile();
            }
        });
        return view;
    }

    private void sendProfile() {

        String changeName = changeNameEditText.getText().toString();
        String changeEmail = changeEmailEditText.getText().toString();
        int changeAge = Integer.parseInt(changeAgeEditText.getText().toString());
        int changGender = changeGenderRadioGroup.getCheckedRadioButtonId() == changeGenderMaleRadioButton.getId() ? 0 : 1;
        boolean changeSmoker = changeSmokerRadioGroup.getCheckedRadioButtonId() == changeSmokerYesRadioButton.getId();
        boolean changePets = changePetsRadioGroup.getCheckedRadioButtonId() == changePetsYesRadioButton.getId();
        String changeOccupation = changeOccupationSpinner.getSelectedItem().toString();
        String changeInfo = changeInfoEditText.getText().toString();
        int changeDuration = Integer.parseInt(changeDurationOfStaySpinner.getSelectedItem().toString());

        TenantProfile tenantProfile = new TenantProfile();
        tenantProfile.setFirstName(changeName);
        tenantProfile.setEmail(changeEmail);
        tenantProfile.setAge(changeAge);
        tenantProfile.setGender(changGender);
        tenantProfile.setSmoker(changeSmoker);
        tenantProfile.setPets(changePets);
        tenantProfile.setOccupation(changeOccupation);
        tenantProfile.setShortBio(changeInfo);
        tenantProfile.setDurationOfStay(changeDuration);
        mPresenter.changeProfile(tenantProfile);
    }

    private void bindView(View view){

        changeNameEditText = (EditText)view.findViewById(R.id.changeNameProfileSettingsEditText);
        changeNameEditText.setText(userState.getTenantProfile().getFirstName());
        changeEmailEditText = (EditText)view.findViewById(R.id.changeEmailProfileSettingsEditText);
        changeEmailEditText.setText(userState.getTenantProfile().getEmail());
        changeAgeEditText = (EditText)view.findViewById(R.id.changeAgeProfileSettingsEditText);
        changeAgeEditText.setText(userState.getTenantProfile().getAge()+"");

        changeGenderRadioGroup = (RadioGroup)view.findViewById(R.id.changeGenderProfileSettingsEditTextRadioGroup);
        changeGenderMaleRadioButton = (RadioButton)view.findViewById(R.id.changeGenderMaleProfileSettingsRadioButton);
        changeGenderFemaleRadioButton = (RadioButton)view.findViewById(R.id.changeGenderFemaleProlfileSettingsButton);
        if (userState.getTenantProfile().getGender() == 0) {
            changeGenderMaleRadioButton.setChecked(true);
            changeGenderFemaleRadioButton.setChecked(false);
        } else {
            changeGenderMaleRadioButton.setChecked(false);
            changeGenderFemaleRadioButton.setChecked(true);
        }

        changeSmokerRadioGroup = (RadioGroup)view.findViewById(R.id.changeSmokerProfileSettingsRadioGroup);
        changeSmokerYesRadioButton = (RadioButton)view.findViewById(R.id.changeSmokerYesProfileSettingsRadioButton);
        changeSmokerNoRadioButton = (RadioButton)view.findViewById(R.id.changeSmokerNoProfileSettingsRadioButton);
        if (userState.getTenantProfile().isSmoker()){
            changeSmokerYesRadioButton.setChecked(true);
            changeSmokerNoRadioButton.setChecked(false);
        } else {
            changeSmokerYesRadioButton.setChecked(false);
            changeSmokerNoRadioButton.setChecked(true);
        }

        changePetsRadioGroup = (RadioGroup)view.findViewById(R.id.changePetsProfileSettingsRadioGroup);
        changePetsYesRadioButton = (RadioButton)view.findViewById(R.id.changePetsYesProfileSettingsRadioButton);
        changePetsNoRadioButton = (RadioButton)view.findViewById(R.id.changePetsNOProfileSettingsRadioButton);
        if (userState.getTenantProfile().hasPets()){
            changePetsYesRadioButton.setChecked(true);
            changePetsNoRadioButton.setChecked(false);
        } else {
            changePetsYesRadioButton.setChecked(false);
            changePetsNoRadioButton.setChecked(true);
        }

        changeOccupationSpinner = (Spinner)view.findViewById(R.id.changeOccupationProfileSettingsSpinner);

        changeInfoEditText = (EditText)view.findViewById(R.id.changeInfoProfileSettingsEditText);
        changeInfoEditText.setText(userState.getTenantProfile().getShortBio());

        changeDurationOfStaySpinner = (Spinner)view.findViewById(R.id.changeDurationOfStayProfileSettingsSpinner);

        changeFilterSettingsButton = (Button)view.findViewById(R.id.changeFilterProfileSettingsButton);
        saveChangesButton = (Button)view.findViewById(R.id.saveChangesProfileSettingsButton);
    }

    @Override
    public void changeToMatchingActivity() {
    }

    @Override
    public void uploadSucces() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
