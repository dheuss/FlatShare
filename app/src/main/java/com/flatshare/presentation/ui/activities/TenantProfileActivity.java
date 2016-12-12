package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.domain.executor.impl.ThreadExecutor;
import com.flatshare.presentation.presenters.TenantProfilePresenter;
import com.flatshare.presentation.presenters.impl.TenantProfilePresenterImpl;
import com.flatshare.threading.MainThreadImpl;

public class TenantProfileActivity extends AppCompatActivity implements TenantProfilePresenter.View {

    private EditText firstNameEditText;
    private EditText ageEditText;

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;

    private RadioGroup smokerRadioGroup;
    private RadioButton smokerYesRadioButton, smokerNoRadioButton;

    private Button profileDoneButton;

    private TenantProfilePresenter mPresenter;
    private static final String TAG = "TenantProfileAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile);

        bindView();

        // create a presenter for this view
        mPresenter = new TenantProfilePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );

        profileDoneButton.setOnClickListener(view -> sendProfile());


    }

    private void sendProfile() {

        String firstname = firstNameEditText.getText().toString();
        int age = Integer.parseInt(ageEditText.getText().toString());

        boolean isSmoker = smokerRadioGroup.getCheckedRadioButtonId() == smokerYesRadioButton.getId();

        int gender = genderRadioGroup.getCheckedRadioButtonId() == maleRadioButton.getId() ? 0 : 1;

        // TODO: better work with setters...constructor too long
        TenantUserProfile tenantUserProfile = new TenantUserProfile(firstname, age, gender, isSmoker, "occupation", "shortBio", 10, null, null);
        mPresenter.sendProfile(tenantUserProfile);
    }

    private void bindView() {
        //    @Bind(R.id.email_edittext)
        firstNameEditText = (EditText) findViewById(R.id.first_name_edittext);
        ageEditText = (EditText) findViewById(R.id.age_edittext);

        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        maleRadioButton = (RadioButton) findViewById(R.id.male_radiobutton);
        femaleRadioButton = (RadioButton) findViewById(R.id.female_radiobutton);

        smokerRadioGroup = (RadioGroup) findViewById(R.id.smoker_radio_group);
        smokerYesRadioButton = (RadioButton) findViewById(R.id.smoker_yes);
        smokerNoRadioButton = (RadioButton) findViewById(R.id.smoker_no);

        profileDoneButton = (Button) findViewById(R.id.tenant_profile_done_button);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showProgress() {
        //TODO: find some loading bar
    }

    @Override
    public void hideProgress() {
        Toast.makeText(this, "Retrieved!", Toast.LENGTH_LONG).show();
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
