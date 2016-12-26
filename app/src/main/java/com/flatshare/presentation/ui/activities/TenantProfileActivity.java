package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.presentation.presenters.TenantProfilePresenter;
import com.flatshare.presentation.presenters.impl.TenantProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class TenantProfileActivity extends AbstractActivity implements TenantProfilePresenter.View {

    private EditText firstNameEditText;
    private EditText ageEditText;
    private EditText emailText;

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;

    private RadioGroup smokerRadioGroup;
    private RadioButton smokerYesRadioButton, smokerNoRadioButton;

    private RadioGroup petsRadioGroup;
    private RadioButton petsYesRadioButton, petsNoRadioButton;

    private Spinner occupationSpinner;

    private Button profileDoneButton;
    private Button takeAPictureButton;

    private TenantProfilePresenter mPresenter;
    private static final String TAG = "TenantProfileAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new TenantProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        profileDoneButton.setOnClickListener(view -> sendProfile());

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tenant_profile;
    }

    private void sendProfile() {

        String firstname = firstNameEditText.getText().toString();
        int age = Integer.parseInt(ageEditText.getText().toString());
        String email = emailText.getText().toString();

        boolean isSmoker = smokerRadioGroup.getCheckedRadioButtonId() == smokerYesRadioButton.getId();

        int gender = genderRadioGroup.getCheckedRadioButtonId() == maleRadioButton.getId() ? 0 : 1;

        boolean isPets = petsRadioGroup.getCheckedRadioButtonId() == petsYesRadioButton.getId();

        String occupation = occupationSpinner.getSelectedItem().toString();

        TenantUserProfile tenantUserProfile = new TenantUserProfile();
        tenantUserProfile.setFirstName(firstname);
        tenantUserProfile.setAge(age);
        tenantUserProfile.setEmail(email);
        tenantUserProfile.setSmoker(isSmoker);
        tenantUserProfile.setGender(gender);
        tenantUserProfile.setPets(isPets);
        tenantUserProfile.setOccupation(occupation);

        mPresenter.sendProfile(tenantUserProfile);
    }

    private void bindView() {
        firstNameEditText = (EditText) findViewById(R.id.nameText_tenant_profile_editText);
        ageEditText = (EditText) findViewById(R.id.ageText_tenant_profile_editText);
        emailText = (EditText) findViewById(R.id.email_tenant_profile_editText);

        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioButton);

        smokerRadioGroup = (RadioGroup) findViewById(R.id.smokerRadioGroup);
        smokerYesRadioButton = (RadioButton) findViewById(R.id.yesSmokerRadioButton);
        smokerNoRadioButton = (RadioButton) findViewById(R.id.noSmokerRadioButton);

        petsRadioGroup = (RadioGroup) findViewById(R.id.petsRadioGroup);
        petsYesRadioButton = (RadioButton) findViewById(R.id.yesPetsRadioButton);
        petsNoRadioButton = (RadioButton) findViewById(R.id.noPetsRadioButton);

        occupationSpinner = (Spinner) findViewById(R.id.occupation_tenant_profile_spinner);

        takeAPictureButton = (Button) findViewById(R.id.take_a_picture_tenant_profile_button);
        profileDoneButton = (Button) findViewById(R.id.done_1_tenant_profile);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToTenantSettings() {
        //TODO change to PrimaryProfileActivity
        Log.d(TAG, "success! changed to TenantSettings!");
        Intent intent = new Intent(this, TenantSettingsActivity.class);
        startActivity(intent);
    }
}
