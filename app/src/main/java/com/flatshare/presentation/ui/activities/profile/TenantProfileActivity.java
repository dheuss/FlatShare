package com.flatshare.presentation.ui.activities.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.presentation.presenters.profile.TenantProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.TenantProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

/**
 * Created by Arber on 16/12/2016.
 */
public class TenantProfileActivity extends AbstractActivity implements TenantProfilePresenter.View {

    private EditText firstNameEditText;
    private EditText ageEditText;
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
    private Button uploadPictureButton;

    //TODO Hobbies

    private int PICK_IMAGE_REQUEST = 1;

    private TenantProfilePresenter mPresenter;
    private static final String TAG = "TenantProfileAct";
    private boolean profilePicUploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new TenantProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        profileDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TenantProfileActivity.this.sendProfile();
            }
        });
        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TenantProfileActivity.this.openGallery();
            }
        });

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

        if(inputValid()) {
            String firstname = firstNameEditText.getText().toString();
            int age = Integer.parseInt(ageEditText.getText().toString());
            boolean isSmoker = smokerRadioGroup.getCheckedRadioButtonId() == smokerYesRadioButton.getId();
            int gender = genderRadioGroup.getCheckedRadioButtonId() == maleRadioButton.getId() ? 0 : 1;
            boolean isPets = petsRadioGroup.getCheckedRadioButtonId() == petsYesRadioButton.getId();
            String occupation = occupationSpinner.getSelectedItem().toString();
            String shortBio = shortBioText.getText().toString();
            int duration = Integer.parseInt(durationSpinner.getSelectedItem().toString());

            TenantProfile tenantProfile = new TenantProfile();
            tenantProfile.setFirstName(firstname);
            tenantProfile.setAge(age);
            tenantProfile.setSmoker(isSmoker);
            tenantProfile.setGender(gender);
            tenantProfile.setOccupation(occupation);
            tenantProfile.setPets(isPets);
            tenantProfile.setShortBio(shortBio);
            tenantProfile.setDurationOfStay(duration);
            tenantProfile.setDone(true);

            mPresenter.sendProfile(tenantProfile);
        }
    }

    private boolean inputValid() {
        boolean result = true;

        if (firstNameEditText.getText().toString().trim().equals("")) {
            firstNameEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (ageEditText.getText().toString().trim().equals("")) {
            ageEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (!maleRadioButton.isChecked() && !femaleRadioButton.isChecked()) {
            maleRadioButton.setError(getString(R.string.check_rb_error));
            result = false;
        }

        if (!smokerYesRadioButton.isChecked() && !smokerNoRadioButton.isChecked()) {
            smokerYesRadioButton.setError(getString(R.string.check_rb_error));
            result = false;
        }

        if (!petsYesRadioButton.isChecked() && !petsNoRadioButton.isChecked()) {
            petsYesRadioButton.setError(getString(R.string.check_rb_error));
            result = false;
        }

        if (shortBioText.getText().toString().trim().equals("")) {
            shortBioText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if(profilePicUploaded){
            uploadPictureButton.setError(getString(R.string.picture_required_error));
            result = false;
        }

        return result;
    }


    private void bindView() {
        firstNameEditText = (EditText) findViewById(R.id.nameProfileEditText);
        ageEditText = (EditText) findViewById(R.id.ageProfileEditText);
        shortBioText = (EditText) findViewById(R.id.infoProfileEditText);

        genderRadioGroup = (RadioGroup) findViewById(R.id.genderProfileEditTextRadioGroup);
        maleRadioButton = (RadioButton) findViewById(R.id.genderMaleProfileRadioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.genderFemaleProlfileButton);

        smokerRadioGroup = (RadioGroup) findViewById(R.id.smokerProfileRadioGroup);
        smokerYesRadioButton = (RadioButton) findViewById(R.id.smokerYesProfileRadioButton);
        smokerNoRadioButton = (RadioButton) findViewById(R.id.smokerNoProfileRadioButton);

        petsRadioGroup = (RadioGroup) findViewById(R.id.petsProfileRadioGroup);
        petsYesRadioButton = (RadioButton) findViewById(R.id.petsYesProfileRadioButton);
        petsNoRadioButton = (RadioButton) findViewById(R.id.petsNOProfileRadioButton);

        occupationSpinner = (Spinner) findViewById(R.id.occupationProfileSpinner);
        durationSpinner = (Spinner) findViewById(R.id.durationOfStayProfileSpinner);

        uploadPictureButton = (Button) findViewById(R.id.saveChangesProfileSettingsButton);
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
    public void uploadSuccess() {
        profilePicUploaded = true;
        Toast.makeText(this, "Upload Successful", Toast.LENGTH_LONG).show();
    }
}
