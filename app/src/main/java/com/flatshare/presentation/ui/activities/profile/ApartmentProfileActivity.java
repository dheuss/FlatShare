package com.flatshare.presentation.ui.activities.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.common.ApartmentLocation;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.pair.ParcelablePair;
import com.flatshare.presentation.presenters.profile.ApartmentProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.ApartmentProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.QRCodeReaderActivity;
import com.flatshare.threading.MainThreadImpl;
import com.flatshare.utils.location.AppLocationService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ApartmentProfileActivity extends AbstractActivity implements ApartmentProfilePresenter.View {

    private static final int STATIC_VALUE = 2;
    private int PICK_IMAGE_REQUEST = 1;

    public static final String ROOMMATE_ID_NICKNAME_PAIR = "idNicknamePair";
    public static final String ROOMMATE_ID = "roommateId";
    public static final String APARTMENT_ID = "apartmentId";

    private EditText apartmentPriceEditText;
    private EditText apartmentAreaEditText;
    private EditText apartmentStreetEditText;
    private EditText apartmentZipCodeEditText;
    private EditText apartmentCityEditText;
    private EditText apartmentStateEditText;
    private EditText apartmentCountryEditText;
    private EditText apartmentInfoEditText;

    private RadioGroup internetRadioGroup;
    private RadioButton internetYesRB, internetNoRB;

    private RadioGroup smokerRadioGroup;
    private RadioButton smokerYesRB, smokerNoRB;

    private RadioGroup petsRadioGroup;
    private RadioButton petsYesRB, petsNoRB;

    private RadioGroup washingMachineRadioGroup;
    private RadioButton washingMachineYesRB, washingMachineNoRB;

    private MultiAutoCompleteTextView roommatesEmailsMultiAC;
    private ArrayAdapter<String> adapter;

    private Button scanRoommateQRButton;
    private Button getLocationButton;
    private Button uploadPictureButton;
    private boolean profilePicUploaded;

    private Button createApartmentButton;

    private AppLocationService appLocationService;
    private static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int PERMISSIONS_REQUEST_STORAGE = 101;

    private ApartmentProfilePresenter mPresenter;
    private static final String TAG = "ApartmentProfileAct";
    private Map<String, String> nicknameIdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appLocationService = new AppLocationService(ApartmentProfileActivity.this);

        bindView();

        mPresenter = new ApartmentProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        initMultiAutoComplete();

        scanRoommateQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApartmentProfileActivity.this.getLocation();
            }
        });

        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        createApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApartmentProfileActivity.this.sendProfile();
            }
        });

    }

    private void getLocation(){
        boolean allowed = false;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                checkForLocationPermission();
            } else {
                allowed = true;
            }
        } else {
            allowed = true;
        }

        if (allowed){
            Toast.makeText(this, "you have access", Toast.LENGTH_SHORT).show();
                Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    getCompleteAddressString(latitude, longitude);
                } else {
                    Log.d(TAG, "onClick: LOCATION IS NULL");
                }
        }
    }

    private void checkForLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST_LOCATION);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                apartmentStreetEditText.setText(returnedAddress.getAddressLine(0));
                apartmentZipCodeEditText.setText(returnedAddress.getPostalCode());
                apartmentCityEditText.setText(returnedAddress.getLocality());
                apartmentStateEditText.setText("Bavaria");
                apartmentCountryEditText.setText(returnedAddress.getCountryName());
            } else {
                Log.w("My Current location address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current location address", "Cannot get Address!");
        }
        return strAdd;
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
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
        }
    }

    private void checkForStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_STORAGE);
    }

    // need it in order to get value from destroyed activity (QR Scanner)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STATIC_VALUE) {
            if (resultCode == RESULT_OK) {

                ParcelablePair idNicknamePair = data.getParcelableExtra(ROOMMATE_ID_NICKNAME_PAIR);

                if (nicknameIdMap == null) {
                    nicknameIdMap = new HashMap<>();
                }
                nicknameIdMap.put(idNicknamePair.getValue(), idNicknamePair.getId());
                adapter.add(idNicknamePair.getValue());
                roommatesEmailsMultiAC.getText().append(idNicknamePair.getValue() + "; ");
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            String[] projection = {MediaStore.MediaColumns.DATA};
            CursorLoader cursorLoader = new CursorLoader(ApartmentProfileActivity.this, uri, projection, null, null, null);
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

    private void scanQR() {
        boolean allowed = false;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                checkForCameraPermission();
            } else {
                allowed = true;
            }
        } else {
            allowed = true;
        }

        if (allowed) {
            Intent intent = new Intent(this, QRCodeReaderActivity.class);
            startActivityForResult(intent, STATIC_VALUE);
        }

    }

    private void checkForCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSIONS_REQUEST_CAMERA);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {

            if (grantResults.length <= 0) {
                return;
            }

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, QRCodeReaderActivity.class);
                startActivityForResult(intent, STATIC_VALUE);
            } else {
                Toast.makeText(this, "Access rejected, please allow access and try again.", Toast.LENGTH_LONG);
            }

        } else if (requestCode == PERMISSIONS_REQUEST_STORAGE) {

            if (grantResults.length <= 0) {
                return;
            }

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            } else {
                Toast.makeText(this, "Access rejected, please allow access and try again.", Toast.LENGTH_LONG);
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apartment_profile;
    }

    private void sendProfile() {

        if (inputValid()) {

            String[] nicknames = roommatesEmailsMultiAC.getText().toString().split(";");

            List<String> roommatesId = new ArrayList<>();
            String id = null;
            for (int i = 0; i < nicknames.length; i++) {

                try {
                    id = nicknameIdMap.get(nicknames[i].trim());
                } catch (NullPointerException e) {
                    Log.w(TAG, "sendProfile: NullPointer", e);
                }

                if (id != null) {
                    roommatesId.add(id);
                }
            }

            int price = Integer.parseInt(apartmentPriceEditText.getText().toString());
            int area = Integer.parseInt(apartmentAreaEditText.getText().toString());

            String street = apartmentStreetEditText.getText().toString();
            int zipCode = Integer.parseInt(apartmentZipCodeEditText.getText().toString());
            String city = apartmentCityEditText.getText().toString();
            String state = apartmentStateEditText.getText().toString();
            String country = apartmentCountryEditText.getText().toString();
            String info = apartmentInfoEditText.getText().toString();

            boolean hasInternet = internetRadioGroup.getCheckedRadioButtonId() == internetYesRB.getId();
            boolean isSmoker = smokerRadioGroup.getCheckedRadioButtonId() == smokerYesRB.getId();
            boolean hasPets = petsRadioGroup.getCheckedRadioButtonId() == petsYesRB.getId();
            boolean hasWashingMachine = washingMachineRadioGroup.getCheckedRadioButtonId() == washingMachineYesRB.getId();

            ApartmentLocation apartmentLocation = new ApartmentLocation();

            apartmentLocation.setStreet(street);
            apartmentLocation.setZipCode(zipCode);
            apartmentLocation.setCity(city);
            apartmentLocation.setState(state);
            apartmentLocation.setCountry(country);

            ApartmentProfile apartmentProfile = new ApartmentProfile();
            apartmentProfile.setPrice(price);
            apartmentProfile.setArea(area);
            apartmentProfile.setInternet(hasInternet);
            apartmentProfile.setSmokerApartment(isSmoker);
            apartmentProfile.setPets(hasPets);
            apartmentProfile.setWashingMachine(hasWashingMachine);
            apartmentProfile.setApartmentInfo(info);

            apartmentProfile.setApartmentLocation(apartmentLocation);

            apartmentProfile.setRoommateIds(roommatesId);

            apartmentProfile.setDone(true);

            mPresenter.sendProfile(apartmentProfile);
        }
    }

    private boolean inputValid() {
        boolean result = true;

        if (apartmentPriceEditText.getText().toString().trim().equals("")) {
            apartmentPriceEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (apartmentAreaEditText.getText().toString().trim().equals("")) {
            apartmentAreaEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (apartmentStreetEditText.getText().toString().trim().equals("")) {
            apartmentStreetEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (apartmentZipCodeEditText.getText().toString().trim().equals("")) {
            apartmentZipCodeEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (apartmentCityEditText.getText().toString().trim().equals("")) {
            apartmentCityEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (apartmentStateEditText.getText().toString().trim().equals("")) {
            apartmentStateEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (apartmentCountryEditText.getText().toString().trim().equals("")) {
            apartmentCountryEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (apartmentInfoEditText.getText().toString().trim().equals("")) {
            apartmentInfoEditText.setError(getString(R.string.field_cannot_be_empty));
            result = false;
        }

        if (!internetYesRB.isChecked() && !internetNoRB.isChecked()) {
            internetYesRB.setError(getString(R.string.check_rb_error));
            result = false;
        }

        if (!smokerYesRB.isChecked() && !smokerNoRB.isChecked()) {
            smokerYesRB.setError(getString(R.string.check_rb_error));
            result = false;
        }

        if (!petsYesRB.isChecked() && !petsNoRB.isChecked()) {
            petsYesRB.setError(getString(R.string.check_rb_error));
            result = false;
        }

        if (!washingMachineYesRB.isChecked() && !washingMachineNoRB.isChecked()) {
            washingMachineYesRB.setError(getString(R.string.check_rb_error));
            result = false;
        }

        return result;
    }

    private void bindView() {
        apartmentPriceEditText = (EditText) findViewById(R.id.apartment_price_edit_text);
        apartmentAreaEditText = (EditText) findViewById(R.id.apartment_area_editText);
        apartmentStreetEditText = (EditText) findViewById(R.id.apartment_street_edit_text);
        apartmentZipCodeEditText = (EditText) findViewById(R.id.apartment_zip_code_edit_text);
        apartmentCityEditText = (EditText) findViewById(R.id.apartment_city_edit_text);
        apartmentStateEditText = (EditText) findViewById(R.id.apartment_state_edit_text);
        apartmentCountryEditText = (EditText) findViewById(R.id.apartment_country_edit_text);
        apartmentInfoEditText = (EditText) findViewById(R.id.infoApartmentEditText);

        internetRadioGroup = (RadioGroup) findViewById(R.id.internet_apartment_rg);
        internetYesRB = (RadioButton) findViewById(R.id.internet_yes_rb);
        internetNoRB = (RadioButton) findViewById(R.id.internet_no_rb);

        smokerRadioGroup = (RadioGroup) findViewById(R.id.smoker_apartment_rg);
        smokerYesRB = (RadioButton) findViewById(R.id.smoker_yes_rb);
        smokerNoRB = (RadioButton) findViewById(R.id.smoker_no_rb);

        petsRadioGroup = (RadioGroup) findViewById(R.id.pets_apartment_rg);
        petsYesRB = (RadioButton) findViewById(R.id.pets_yes_rb);
        petsNoRB = (RadioButton) findViewById(R.id.pets_no_rb);

        washingMachineRadioGroup = (RadioGroup) findViewById(R.id.washing_machine_apartment_rg);
        washingMachineYesRB = (RadioButton) findViewById(R.id.washing_machine_yes_rb);
        washingMachineNoRB = (RadioButton) findViewById(R.id.washing_machine_no_rb);

        scanRoommateQRButton = (Button) findViewById(R.id.scan_roommate_QR_button);
        getLocationButton = (Button) findViewById(R.id.getLocation_button);
        uploadPictureButton = (Button) findViewById(R.id.upload_picture_apartment);
        createApartmentButton = (Button) findViewById(R.id.create_apartment_profile_button);

        roommatesEmailsMultiAC = (MultiAutoCompleteTextView) findViewById(R.id.apartment_roommates_edit_text);
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
    public void changeToApartmentSettings() {
        Log.d(TAG, "success! changed to ApartmentSettings!");
        Intent intent = new Intent(this, ApartmentSettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateAdapter(Map<String, String> nicknameIdMap) {
        this.nicknameIdMap = nicknameIdMap;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(nicknameIdMap.keySet()));

        roommatesEmailsMultiAC.setAdapter(adapter);
    }

    private void initMultiAutoComplete() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        roommatesEmailsMultiAC.setTokenizer(new SemicolonTokenizer());
        roommatesEmailsMultiAC.setThreshold(1);

        mPresenter.getUserEmails();
    }

//    private void deleteLastWord(String oldText, String newText) {
//
//        String result = newText;
//        for (int i = newText.length(); i < oldText.length(); i++) {
//            if(oldText.charAt(i) == ';'){
//                int lastIndex = Math.max(oldText.lastIndexOf(' '), oldText.lastIndexOf(';'));
//
//                if(lastIndex < 0){ // if no space found
//                    result =  "";
//                } else {
//                    result = oldText.substring(0, lastIndex);
//                }
//            }
//        }
//
//        this.oldText = result;
//        roommatesEmailsMultiAC.setText(result);
//        roommatesEmailsMultiAC.moveCursorToVisibleOffset();
//    }

    private int countOccur(String s, char c) {
        int counter = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public void uploadSuccess() {
        profilePicUploaded = true;
        Toast.makeText(this, "Upload Successful", Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return getContext();
    }

    /**
     * This simple Tokenizer can be used for lists where the items are
     * separated by a semicolon and one or more spaces.
     */
    private class SemicolonTokenizer implements MultiAutoCompleteTextView.Tokenizer {
        public int findTokenStart(CharSequence text, int cursor) {
            int i = cursor;

            while (i > 0 && text.charAt(i - 1) != ';') {
                i--;
            }
            while (i < cursor && text.charAt(i) == ' ') {
                i++;
            }

            return i;
        }

        public int findTokenEnd(CharSequence text, int cursor) {
            int i = cursor;
            int len = text.length();

            while (i < len) {
                if (text.charAt(i) == ';') {
                    return i;
                } else {
                    i++;
                }
            }

            return len;
        }

        public CharSequence terminateToken(CharSequence text) {
            int i = text.length();

            while (i > 0 && text.charAt(i - 1) == ' ') {
                i--;
            }

            if (i > 0 && text.charAt(i - 1) == ';') {
                return text;
            } else {
                if (text instanceof Spanned) {
                    SpannableString sp = new SpannableString(text + "; ");
                    TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                            Object.class, sp, 0);
                    return sp;
                } else {
                    return text + "; ";
                }
            }
        }
    }
}
