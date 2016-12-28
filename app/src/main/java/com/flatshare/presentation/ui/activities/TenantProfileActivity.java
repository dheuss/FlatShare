package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    private EditText shortBioText;

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;

    private RadioGroup smokerRadioGroup;
    private RadioButton smokerYesRadioButton, smokerNoRadioButton;

    private RadioGroup petsRadioGroup;
    private RadioButton petsYesRadioButton, petsNoRadioButton;

    private Spinner occupationSpinner;
    private Spinner durationSpinner;

    private Button profileDoneButton;
    //TODO takeAPicture
    private Button takeAPictureButton;

    //TODO Hobbies

    private int PICK_IMAGE_REQUEST = 1;

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
        takeAPictureButton.setOnClickListener(view -> openGallery());

    }

    private void openGallery() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            mPresenter.uploadImage(uri);
            // Log.d(TAG, String.valueOf(bitmap));
        }
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
        String shortBio = shortBioText.getText().toString();
        int duration = Integer.parseInt(durationSpinner.getSelectedItem().toString());

        TenantUserProfile tenantUserProfile = new TenantUserProfile();
        tenantUserProfile.setFirstName(firstname);
        tenantUserProfile.setAge(age);
        tenantUserProfile.setEmail(email);
        tenantUserProfile.setSmoker(isSmoker);
        tenantUserProfile.setGender(gender);
        tenantUserProfile.setOccupation(occupation);
        tenantUserProfile.setPets(isPets);
        tenantUserProfile.setShortBio(shortBio);
        tenantUserProfile.setDurationOfStay(duration);

        mPresenter.sendProfile(tenantUserProfile);
    }

    private void bindView() {
        firstNameEditText = (EditText) findViewById(R.id.nameText_tenant_profile_editText);
        ageEditText = (EditText) findViewById(R.id.ageText_tenant_profile_editText);
        emailText = (EditText) findViewById(R.id.email_tenant_profile_editText);
        shortBioText = (EditText) findViewById(R.id.short_bio_tenant_profile_editText);

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
        durationSpinner = (Spinner) findViewById(R.id.duration_of_stay_tenant_profile_spinner);

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

    @Override
    public void uploadSucces() {
        Toast.makeText(this, "Upload Successful", Toast.LENGTH_LONG).show();
    }
}
