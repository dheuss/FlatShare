package com.flatshare.presentation.ui.activities.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
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

import java.io.ByteArrayOutputStream;

/**
 * Created by Arber on 16/12/2016.
 */
public class TenantProfileActivity extends AbstractActivity implements TenantProfilePresenter.View {

    private static final int PERMISSIONS_REQUEST_STORAGE = 101;
    private EditText firstNameEditText;
    private EditText ageEditText;
    private EditText shortBioText;
    private EditText emailEditText;

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;

    private RadioGroup smokerRadioGroup;
    private RadioButton smokerYesRadioButton, smokerNoRadioButton;

    private RadioGroup petsRadioGroup;
    private RadioButton petsYesRadioButton, petsNoRadioButton;

    private Spinner occupationSpinner;

    private Button profileDoneButton;
    private Button uploadPictureButton;

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

        boolean allowed = false;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                checkForStoragePermission();
            } else {
                allowed = true;
            }
        } else {
            allowed = true;
        }

        if (allowed) {
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
        }

        // startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


//        Intent intent = new Intent();
//        // Show only images, no videos or anything else
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        // Always show the chooser (if there are multiple options available)
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void checkForStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST_STORAGE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_STORAGE) {

            if (grantResults.length <= 0) {
                return;
            }

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            } else {
                Toast.makeText(this, "Access rejected, please allow access and try again.", Toast.LENGTH_LONG);
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // get the string from params, which is an array
            Uri uri = data.getData();

            String[] projection = {MediaStore.MediaColumns.DATA};
            CursorLoader cursorLoader = new CursorLoader(TenantProfileActivity.this, uri, projection, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);

            new CompressTask().execute(selectedImagePath);

        }
    }

    private class CompressTask extends AsyncTask<String, Integer, byte[]> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // This is run in a background thread
        @Override
        protected byte[] doInBackground(String... params) {

            final int REQUIRED_SIZE = 200;

            String selectedImagePath = params[0];
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, o);

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath, o2);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            return byteArray;
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(byte[] result) {
            super.onPostExecute(result);
            mPresenter.uploadImage(result);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tenant_profile;
    }

    private void sendProfile() {

        if (inputValid()) {
            String firstname = firstNameEditText.getText().toString();
            int age = Integer.parseInt(ageEditText.getText().toString());
            boolean isSmoker = smokerRadioGroup.getCheckedRadioButtonId() == smokerYesRadioButton.getId();
            int gender = genderRadioGroup.getCheckedRadioButtonId() == maleRadioButton.getId() ? 0 : 1;
            boolean isPets = petsRadioGroup.getCheckedRadioButtonId() == petsYesRadioButton.getId();
            String occupation = occupationSpinner.getSelectedItem().toString();
            String shortBio = shortBioText.getText().toString();
            String email = emailEditText.getText().toString();

            TenantProfile tenantProfile = new TenantProfile();
            tenantProfile.setFirstName(firstname);
            tenantProfile.setAge(age);
            tenantProfile.setSmoker(isSmoker);
            tenantProfile.setGender(gender);
            tenantProfile.setOccupation(occupation);
            tenantProfile.setPets(isPets);
            tenantProfile.setShortBio(shortBio);
            tenantProfile.setEmail(email);
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

        if (emailEditText.getText().toString().trim().equals("")) {
            emailEditText.setText(getString(R.string.field_cannot_be_empty));
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

        return result;
    }


    private void bindView() {
        firstNameEditText = (EditText) findViewById(R.id.nameProfileEditText);
        ageEditText = (EditText) findViewById(R.id.ageProfileEditText);
        shortBioText = (EditText) findViewById(R.id.infoProfileEditText);
        emailEditText = (EditText) findViewById(R.id.emailProfileEditText);

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
