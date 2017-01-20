package com.flatshare.presentation.ui.activities.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.profile.TenantProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.TenantProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;
import com.flatshare.presentation.ui.activities.MainActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfileTenantSettingsActivity extends AbstarctFragmentAcivity implements TenantProfilePresenter.View {

    private TextView tenantProfileHeadline;
    private TextView tenantProfileInfo;

    private EditText changeNameEditText;
    private EditText changeAgeEditText;

    private RadioGroup changeGenderRadioGroup;
    private RadioButton changeGenderMaleRadioButton;
    private RadioButton changeGenderFemaleRadioButton;

    private RadioGroup changeSmokerRadioGroup;
    private RadioButton changeSmokerYESRadioButton;
    private RadioButton changeSmokerNORadioButton;

    private RadioGroup changePetsRadioGroup;
    private RadioButton changePetsYESRadioButton;
    private RadioButton changePetsNORadioButton;

    private Spinner changeOccupationSpinner;

    private EditText changeInfoEditText;

    private Spinner changeDurationOfStaySpinner;

    private Button changeFilterSettingsButton;
    private Button saveChangesButton;

    private static final String TAG = "ProfileTenantSettingsActivity";

    private TenantProfilePresenter mPresenter;

    private UserState userState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_tenant_profile, container, false);
        bindView(view);

        mPresenter = new TenantProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        changeFilterSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View mView = layoutInflater.inflate(R.layout.activity_popup, null);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);

                final TextView popUpTextView = (TextView) mView.findViewById(R.id.popup_TextView);
                popUpTextView.setText("Have you saved your user profile changes?");

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity(), ProfileTenantSettingsFilterActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
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

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View mView = layoutInflater.inflate(R.layout.activity_popup, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(mView);

        final TextView popUpTextView = (TextView) mView.findViewById(R.id.popup_TextView);
        popUpTextView.setText("Do you want to save your profile?");


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String changeName = changeNameEditText.getText().toString();
                        int changeAge = Integer.parseInt(changeAgeEditText.getText().toString());
                        int changGender = changeGenderRadioGroup.getCheckedRadioButtonId() == changeGenderMaleRadioButton.getId() ? 0 : 1;
                        boolean changeSmoker = changeSmokerRadioGroup.getCheckedRadioButtonId() == changeSmokerYESRadioButton.getId();
                        boolean changePets = changePetsRadioGroup.getCheckedRadioButtonId() == changePetsYESRadioButton.getId();
                        String changeOccupation = changeOccupationSpinner.getSelectedItem().toString();
                        String changeInfo = changeInfoEditText.getText().toString();
                        int changeDuration = Integer.parseInt(changeDurationOfStaySpinner.getSelectedItem().toString());

                        TenantProfile tenantProfile = new TenantProfile();

                        tenantProfile.setFirstName(changeName);
                        tenantProfile.setAge(changeAge);
                        tenantProfile.setGender(changGender);
                        tenantProfile.setSmoker(changeSmoker);
                        tenantProfile.setPets(changePets);
                        tenantProfile.setOccupation(changeOccupation);
                        tenantProfile.setShortBio(changeInfo);
                        tenantProfile.setDurationOfStay(changeDuration);

                        mPresenter.sendProfile(tenantProfile);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeNameEditText.setText(userState.getTenantProfile().getFirstName());
                        changeAgeEditText.setText(userState.getTenantProfile().getAge()+"");

                        if (userState.getTenantProfile().getGender() == 0) {
                            changeGenderMaleRadioButton.setChecked(true);
                            changeGenderFemaleRadioButton.setChecked(false);
                        } else if (userState.getTenantProfile().getGender() == 1){
                            changeGenderMaleRadioButton.setChecked(false);
                            changeGenderFemaleRadioButton.setChecked(true);
                        }

                        if (userState.getTenantProfile().isSmoker()){
                            changeSmokerYESRadioButton.setChecked(true);
                            changeSmokerNORadioButton.setChecked(false);
                        } else {
                            changeSmokerYESRadioButton.setChecked(false);
                            changeSmokerNORadioButton.setChecked(true);
                        }

                        if (userState.getTenantProfile().hasPets()){
                            changePetsYESRadioButton.setChecked(true);
                            changePetsNORadioButton.setChecked(false);
                        } else {
                            changePetsYESRadioButton.setChecked(false);
                            changePetsNORadioButton.setChecked(true);
                        }

                        changeInfoEditText.setText(userState.getTenantProfile().getShortBio());
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    private void bindView(View view){

        tenantProfileHeadline = (TextView)view.findViewById(R.id.tenant_profile_inital);
        tenantProfileHeadline.setText("Tenant Profile");
        tenantProfileInfo = (TextView)view.findViewById(R.id.tenant_settings_info);
        tenantProfileInfo.setText("Here your can update all your personal information! Keep your profile uptodate!");

        changeNameEditText = (EditText)view.findViewById(R.id.nameProfileEditText);
        changeNameEditText.setText(userState.getTenantProfile().getFirstName());
        changeAgeEditText = (EditText)view.findViewById(R.id.ageProfileEditText);
        changeAgeEditText.setText(userState.getTenantProfile().getAge()+"");

        changeGenderRadioGroup = (RadioGroup)view.findViewById(R.id.genderProfileEditTextRadioGroup);
        changeGenderMaleRadioButton = (RadioButton)view.findViewById(R.id.genderMaleProfileRadioButton);
        changeGenderFemaleRadioButton = (RadioButton)view.findViewById(R.id.genderFemaleProlfileButton);
        if (userState.getTenantProfile().getGender() == 0) {
            changeGenderMaleRadioButton.setChecked(true);
            changeGenderFemaleRadioButton.setChecked(false);
        } else if (userState.getTenantProfile().getGender() == 1){
            changeGenderMaleRadioButton.setChecked(false);
            changeGenderFemaleRadioButton.setChecked(true);
        }

        changeSmokerRadioGroup = (RadioGroup)view.findViewById(R.id.smokerProfileRadioGroup);
        changeSmokerYESRadioButton = (RadioButton)view.findViewById(R.id.smokerYesProfileRadioButton);
        changeSmokerNORadioButton = (RadioButton)view.findViewById(R.id.smokerNoProfileRadioButton);
        if (userState.getTenantProfile().isSmoker()){
            changeSmokerYESRadioButton.setChecked(true);
            changeSmokerNORadioButton.setChecked(false);
        } else {
            changeSmokerYESRadioButton.setChecked(false);
            changeSmokerNORadioButton.setChecked(true);
        }

        changePetsRadioGroup = (RadioGroup)view.findViewById(R.id.petsProfileRadioGroup);
        changePetsYESRadioButton = (RadioButton)view.findViewById(R.id.petsYesProfileRadioButton);
        changePetsNORadioButton = (RadioButton)view.findViewById(R.id.petsNOProfileRadioButton);
        if (userState.getTenantProfile().hasPets()){
            changePetsYESRadioButton.setChecked(true);
            changePetsNORadioButton.setChecked(false);
        } else {
            changePetsYESRadioButton.setChecked(false);
            changePetsNORadioButton.setChecked(true);
        }

        changeOccupationSpinner = (Spinner)view.findViewById(R.id.occupationProfileSpinner);

        changeInfoEditText = (EditText)view.findViewById(R.id.infoProfileEditText);
        changeInfoEditText.setText(userState.getTenantProfile().getShortBio());

        changeDurationOfStaySpinner = (Spinner)view.findViewById(R.id.durationOfStayProfileSpinner);

        changeFilterSettingsButton = (Button)view.findViewById(R.id.saveChangesProfileSettingsButton);
        changeFilterSettingsButton.setText("CHANGE YOUR SETTINGS!");

        saveChangesButton = (Button)view.findViewById(R.id.done_1_tenant_profile);
        saveChangesButton.setText("SAVE!");
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

    @Override
    public void changeToTenantSettings() {
        Toast.makeText(getActivity(), "Changes saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadSuccess() {

    }
}
