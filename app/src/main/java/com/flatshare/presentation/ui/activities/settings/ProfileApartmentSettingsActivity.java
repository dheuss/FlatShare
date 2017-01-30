package com.flatshare.presentation.ui.activities.settings;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.common.ApartmentLocation;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.profile.ApartmentProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.ApartmentProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractFragmentActivity;
import com.flatshare.threading.MainThreadImpl;
import com.flatshare.utils.location.AppLocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
/**
 * Created by david on 06/12/2016.
 */
public class ProfileApartmentSettingsActivity extends AbstractFragmentActivity implements ApartmentProfilePresenter.View {

    private ApartmentProfilePresenter mPresenter;

    private UserState userState;

    private TextView apartmentProfileHeadline;
    private TextView apartmentProfileInfo;

    private MultiAutoCompleteTextView roommatesTextView;
    private Map<String, String> nicknameIdMap;
    private EditText priceEditText;
    private EditText sizeEditText;
    private EditText streetEditText;
    private EditText zipCodeEditText;
    private EditText cityEditText;
    private EditText stateEditText;
    private EditText countryEditText;
    private EditText infoEditText;

    private RadioGroup internetRadioGroup;
    private RadioButton internetYESRadioButton, internetNORadioButton;

    private RadioGroup smokerRadioGroup;
    private RadioButton smokerYESRadioButton, smokerNORadioButton;

    private RadioGroup petsRadioGroup;
    private RadioButton petsYESRadioButton, petsNORadioButton;

    private RadioGroup washingMashineRadioGroup;
    private RadioButton washingMashineYESRadioButton, washingMashineNORadioButton;

    private Button qrButton;
    private Button getLocationButton;
    private Button changeButton;
    private Button saveButton;

    private AppLocationService appLocationService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);

        appLocationService = new AppLocationService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_apartment_profile, container, false);

        bindView(view);

        mPresenter = new ApartmentProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "QR Code Scanner doesn't work at the moment", Toast.LENGTH_SHORT).show();
            }
        });

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    getCompleteAddressString(latitude, longitude);
                } else {
                    Log.d(TAG, "onClick: LOCATION IS NULL");
                }
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View mView = layoutInflater.inflate(R.layout.activity_popup, null);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);

                final TextView popUpTextView = (TextView) mView.findViewById(R.id.popup_TextView);
                popUpTextView.setText("Have you saved your apartment profile changes?");

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity(), ProfileApartmentSettingsFilterActivity.class));
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProfile();
            }
        });

        return view;
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                streetEditText.setText(returnedAddress.getAddressLine(0));
                zipCodeEditText.setText(returnedAddress.getPostalCode());
                cityEditText.setText(returnedAddress.getLocality());
                stateEditText.setText("Bavaria");
                countryEditText.setText(returnedAddress.getCountryName());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    private void sendProfile() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View mView = layoutInflater.inflate(R.layout.activity_popup, null);

        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(mView);

        final TextView popUpTextView = (TextView) mView.findViewById(R.id.popup_TextView);
        popUpTextView.setText("Do you want to save your profile?");


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String[] emails = roommatesTextView.getText().toString().split(",");

                        List<String> roommatesId = new ArrayList<>();
                        String id = null;
                        for (int i = 0; i < emails.length; i++) {

                            try {
                                id = nicknameIdMap.get(emails[i].trim());
                            } catch (NullPointerException e) {
                                Log.w(TAG, "sendProfile: NullPointer", e);
                            }

                            if (id != null) {
                                roommatesId.add(id);
                            }
                        }

                        int price = Integer.parseInt(priceEditText.getText().toString());
                        int size = Integer.parseInt(sizeEditText.getText().toString());
                        String street = streetEditText.getText().toString();
                        int zip = Integer.parseInt(zipCodeEditText.getText().toString());
                        String city = cityEditText.getText().toString();
                        String state = stateEditText.getText().toString();
                        String country = countryEditText.getText().toString();
                        String info = apartmentProfileInfo.getText().toString();

                        boolean internet = internetRadioGroup.getCheckedRadioButtonId() == internetYESRadioButton.getId();
                        boolean smoker = smokerRadioGroup.getCheckedRadioButtonId() == smokerYESRadioButton.getId();
                        boolean pets = petsRadioGroup.getCheckedRadioButtonId() == petsYESRadioButton.getId();
                        boolean washingMashine = washingMashineRadioGroup.getCheckedRadioButtonId() == washingMashineYESRadioButton.getId();

                        ApartmentLocation apartmentLocation = new ApartmentLocation();

                        apartmentLocation.setStreet(street);
                        apartmentLocation.setZipCode(zip);
                        apartmentLocation.setCity(city);
                        apartmentLocation.setState(state);
                        apartmentLocation.setCountry(country);

                        ApartmentProfile apartmentProfile = new ApartmentProfile();
                        apartmentProfile.setPrice(price);
                        apartmentProfile.setArea(size);
                        apartmentProfile.setInternet(internet);
                        apartmentProfile.setSmokerApartment(smoker);
                        apartmentProfile.setPets(pets);
                        apartmentProfile.setWashingMachine(washingMashine);
                        apartmentProfile.setApartmentInfo(info);

                        apartmentProfile.setApartmentLocation(apartmentLocation);

                        apartmentProfile.setRoommateIds(roommatesId);

                        mPresenter.sendProfile(apartmentProfile);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        roommatesTextView.setText(userState.getApartmentProfile().getRoommateIds().size()+"");
                        priceEditText.setText(userState.getApartmentProfile().getPrice() + "");
                        sizeEditText.setText(userState.getApartmentProfile().getArea() + "");
                        streetEditText.setText(userState.getApartmentProfile().getApartmentLocation().getStreet()+"");
                        zipCodeEditText.setText(userState.getApartmentProfile().getApartmentLocation().getZipCode()+"");
                        infoEditText.setText(userState.getApartmentProfile().getApartmentInfo());

                        if (userState.getApartmentProfile().hasInternet()){
                            internetYESRadioButton.setChecked(true);
                            internetNORadioButton.setChecked(false);
                        } else {
                            internetYESRadioButton.setChecked(false);
                            internetNORadioButton.setChecked(true);
                        }
                        if (userState.getApartmentProfile().isSmokerApartment()){
                            smokerYESRadioButton.setChecked(true);
                            smokerNORadioButton.setChecked(false);
                        } else {
                            smokerYESRadioButton.setChecked(false);
                            smokerNORadioButton.setChecked(true);
                        }
                        if (userState.getApartmentProfile().hasPets()){
                            petsYESRadioButton.setChecked(true);
                            petsNORadioButton.setChecked(false);
                        } else {
                            petsYESRadioButton.setChecked(false);
                            petsNORadioButton.setChecked(true);
                        }
                        if (userState.getApartmentProfile().hasWashingMachine()){
                            washingMashineYESRadioButton.setChecked(true);
                            washingMashineNORadioButton.setChecked(false);
                        } else {
                            washingMashineYESRadioButton.setChecked(false);
                            washingMashineNORadioButton.setChecked(true);
                        }

                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    private void bindView(View view){
        apartmentProfileHeadline = (TextView)view.findViewById(R.id.apartment_profile_inital);
        apartmentProfileHeadline.setText("Apartment Profile");
        apartmentProfileInfo = (TextView)view.findViewById(R.id.apartment_profile_info);
        apartmentProfileInfo.setText("Here your can update all your apartment information! Keep your profile uptodate!");

        roommatesTextView = (MultiAutoCompleteTextView)view.findViewById(R.id.apartment_roommates_edit_text);
        roommatesTextView.setText(userState.getApartmentProfile().getRoommateIds().size()+"");

        priceEditText = (EditText)view.findViewById(R.id.apartment_price_edit_text);
        priceEditText.setText(userState.getApartmentProfile().getPrice() + "");

        sizeEditText = (EditText)view.findViewById(R.id.apartment_area_editText);
        sizeEditText.setText(userState.getApartmentProfile().getArea() + "");

        streetEditText = (EditText)view.findViewById(R.id.apartment_street_edit_text);
        streetEditText.setText(userState.getApartmentProfile().getApartmentLocation().getStreet()+"");

        zipCodeEditText = (EditText)view.findViewById(R.id.apartment_zip_code_edit_text);
        zipCodeEditText.setText(userState.getApartmentProfile().getApartmentLocation().getZipCode()+"");

        cityEditText = (EditText)view.findViewById(R.id.apartment_city_edit_text);
        cityEditText.setText(userState.getApartmentProfile().getApartmentLocation().getCity());

        stateEditText =(EditText) view.findViewById(R.id.apartment_state_edit_text);
        stateEditText.setText(userState.getApartmentProfile().getApartmentLocation().getState());

        countryEditText = (EditText)view.findViewById(R.id.apartment_country_edit_text);
        countryEditText.setText(userState.getApartmentProfile().getApartmentLocation().getCountry());

        internetRadioGroup = (RadioGroup)view.findViewById(R.id.internet_apartment_rg);
        internetYESRadioButton = (RadioButton)view.findViewById(R.id.internet_yes_rb);
        internetNORadioButton = (RadioButton)view.findViewById(R.id.internet_no_rb);
        if (userState.getApartmentProfile().hasInternet()){
            internetYESRadioButton.setChecked(true);
            internetNORadioButton.setChecked(false);
        } else {
            internetYESRadioButton.setChecked(false);
            internetNORadioButton.setChecked(true);
        }

        smokerRadioGroup = (RadioGroup)view.findViewById(R.id.smoker_apartment_rg);
        smokerYESRadioButton = (RadioButton)view.findViewById(R.id.smoker_yes_rb);
        smokerNORadioButton = (RadioButton)view.findViewById(R.id.smoker_no_rb);
        if (userState.getApartmentProfile().isSmokerApartment()){
            smokerYESRadioButton.setChecked(true);
            smokerNORadioButton.setChecked(false);
        } else {
            smokerYESRadioButton.setChecked(false);
            smokerNORadioButton.setChecked(true);
        }

        petsRadioGroup = (RadioGroup)view.findViewById(R.id.pets_apartment_rg);
        petsYESRadioButton = (RadioButton)view.findViewById(R.id.pets_yes_rb);
        petsNORadioButton = (RadioButton)view.findViewById(R.id.pets_no_rb);
        if (userState.getApartmentProfile().hasPets()){
            petsYESRadioButton.setChecked(true);
            petsNORadioButton.setChecked(false);
        } else {
            petsYESRadioButton.setChecked(false);
            petsNORadioButton.setChecked(true);
        }

        washingMashineRadioGroup = (RadioGroup)view.findViewById(R.id.washing_machine_apartment_rg);
        washingMashineYESRadioButton = (RadioButton)view.findViewById(R.id.washing_machine_yes_rb);
        washingMashineNORadioButton = (RadioButton)view.findViewById(R.id.washing_machine_no_rb);
        if (userState.getApartmentProfile().hasWashingMachine()){
            washingMashineYESRadioButton.setChecked(true);
            washingMashineNORadioButton.setChecked(false);
        } else {
            washingMashineYESRadioButton.setChecked(false);
            washingMashineNORadioButton.setChecked(true);
        }

        infoEditText = (EditText)view.findViewById(R.id.infoApartmentEditText);
        infoEditText.setText(userState.getApartmentProfile().getApartmentInfo());

        qrButton = (Button) view.findViewById(R.id.scan_roommate_QR_button);
        qrButton.setClickable(false);
        getLocationButton = (Button) view.findViewById(R.id.getLocation_button);
        changeButton = (Button) view.findViewById(R.id.upload_picture_apartment);
        changeButton.setText("CHANGE YOUR SETTINGS!");
        saveButton = (Button) view.findViewById(R.id.create_apartment_profile_button);
        saveButton.setText("SAVE!");
    }

    @Override
    public void changeToApartmentSettings() {
        Toast.makeText(getActivity(), "Changes saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAdapter(Map<String, String> idEmailsMap) {

    }

    @Override
    public void uploadSuccess() {

    }

    @Override
    public void showError(String message) {

    }
}
