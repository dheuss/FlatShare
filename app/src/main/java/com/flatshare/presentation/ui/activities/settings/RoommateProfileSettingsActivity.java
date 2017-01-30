package com.flatshare.presentation.ui.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.profile.RoommateProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.RoommateProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractFragmentActivity;
import com.flatshare.threading.MainThreadImpl;

public class RoommateProfileSettingsActivity extends AbstractFragmentActivity implements RoommateProfilePresenter.View {

    private static final String TAG = "RoommateProfileSettingsActivity";
    private RoommateProfilePresenter mPresenter;

    private UserState userState;

    private TextView apartmentProfileHeadline;
    private TextView apartmentProfileInfo;

    private MultiAutoCompleteTextView roommatesTextView;
    private EditText priceEditText;
    private EditText emailEditText;
    private EditText sizeEditText;
    private EditText streetEditText;
    private EditText zipCodeEditText;
    private EditText cityEditText;
    private EditText stateEditText;
    private EditText countryEditText;
    private EditText infoEditText;

    private RadioButton internetYESRadioButton, internetNORadioButton;

    private RadioButton smokerYESRadioButton, smokerNORadioButton;

    private RadioButton petsYESRadioButton, petsNORadioButton;

    private RadioButton washingMashineYESRadioButton, washingMashineNORadioButton;

    private Button qrButton;
    private Button getLocationButton;
    private Button changeButton;
    private Button saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_apartment_profile, container, false);

        bindView(view);

        mPresenter = new RoommateProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You're not allowed to click this button ,sorry!", Toast.LENGTH_SHORT).show();
            }
        });

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You're not allowed to click this button ,sorry!", Toast.LENGTH_SHORT).show();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RoommateProfileSettingsFilterActivity.class));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You're not allowed to click this button ,sorry!", Toast.LENGTH_SHORT).show();
            }
        });

        bindValues();

        return view;
    }

    private void bindView(View view) {
        apartmentProfileHeadline = (TextView) view.findViewById(R.id.apartment_profile_inital);
        apartmentProfileHeadline.setText("Roommate Profile");
        apartmentProfileInfo = (TextView) view.findViewById(R.id.apartment_profile_info);
        apartmentProfileInfo.setText("Here your can see all your apartment information! Sorry, you are not allowed to change anything!");

        roommatesTextView = (MultiAutoCompleteTextView) view.findViewById(R.id.apartment_roommates_edit_text);
        priceEditText = (EditText) view.findViewById(R.id.apartment_price_edit_text);
        sizeEditText = (EditText) view.findViewById(R.id.apartment_area_editText);
        streetEditText = (EditText) view.findViewById(R.id.apartment_street_edit_text);
        emailEditText = (EditText) view.findViewById(R.id.apartment_email_editText);
        zipCodeEditText = (EditText) view.findViewById(R.id.apartment_zip_code_edit_text);
        cityEditText = (EditText) view.findViewById(R.id.apartment_city_edit_text);
        stateEditText = (EditText) view.findViewById(R.id.apartment_state_edit_text);
        countryEditText = (EditText) view.findViewById(R.id.apartment_country_edit_text);
        infoEditText = (EditText) view.findViewById(R.id.infoApartmentEditText);
        internetYESRadioButton = (RadioButton) view.findViewById(R.id.internet_yes_rb);
        internetNORadioButton = (RadioButton) view.findViewById(R.id.internet_no_rb);
        smokerYESRadioButton = (RadioButton) view.findViewById(R.id.smoker_yes_rb);
        smokerNORadioButton = (RadioButton) view.findViewById(R.id.smoker_no_rb);
        petsYESRadioButton = (RadioButton) view.findViewById(R.id.pets_yes_rb);
        petsNORadioButton = (RadioButton) view.findViewById(R.id.pets_no_rb);
        washingMashineYESRadioButton = (RadioButton) view.findViewById(R.id.washing_machine_yes_rb);
        washingMashineNORadioButton = (RadioButton) view.findViewById(R.id.washing_machine_no_rb);


        qrButton = (Button) view.findViewById(R.id.scan_roommate_QR_button);
        qrButton.setClickable(false);
        getLocationButton = (Button) view.findViewById(R.id.getLocation_button);
        getLocationButton.setClickable(false);
        changeButton = (Button) view.findViewById(R.id.upload_picture_apartment);
        changeButton.setText("CHANGE YOUR SETTINGS!");
        saveButton = (Button) view.findViewById(R.id.create_apartment_profile_button);
        saveButton.setText("SAVE!");
    }

    private void bindValues() {
        if (userState.getApartmentProfile() == null) {
            userState.getRoommateProfile().getApartmentId();
        } else {
            emailEditText.setText(userState.getApartmentProfile().getEmail()+"");
            emailEditText.setEnabled(false);
            roommatesTextView.setText(userState.getApartmentProfile().getRoommateIds().size() + "");
            roommatesTextView.setEnabled(false);
            priceEditText.setText(userState.getApartmentProfile().getPrice() + "");
            priceEditText.setEnabled(false);
            sizeEditText.setText(userState.getApartmentProfile().getArea() + "");
            sizeEditText.setEnabled(false);
            streetEditText.setText(userState.getApartmentProfile().getApartmentLocation().getStreet() + "");
            streetEditText.setEnabled(false);
            zipCodeEditText.setText(userState.getApartmentProfile().getApartmentLocation().getZipCode() + "");
            zipCodeEditText.setEnabled(false);
            cityEditText.setText(userState.getApartmentProfile().getApartmentLocation().getCity() + "");
            cityEditText.setEnabled(false);
            stateEditText.setText(userState.getApartmentProfile().getApartmentLocation().getState());
            stateEditText.setEnabled(false);
            countryEditText.setText(userState.getApartmentProfile().getApartmentLocation().getCountry());
            countryEditText.setEnabled(false);
            infoEditText.setText(userState.getApartmentProfile().getApartmentInfo());
            infoEditText.setEnabled(false);
            if (userState.getApartmentProfile().hasInternet()) {
                internetYESRadioButton.setChecked(true);
                internetNORadioButton.setChecked(false);
            } else {
                internetYESRadioButton.setChecked(false);
                internetNORadioButton.setChecked(true);
            }
            internetYESRadioButton.setClickable(false);
            internetNORadioButton.setClickable(false);
            if (userState.getApartmentProfile().isSmokerApartment()) {
                smokerYESRadioButton.setChecked(true);
                smokerNORadioButton.setChecked(false);
            } else {
                smokerYESRadioButton.setChecked(false);
                smokerNORadioButton.setChecked(true);
            }
            smokerYESRadioButton.setClickable(false);
            smokerNORadioButton.setClickable(false);
            if (userState.getApartmentProfile().hasPets()) {
                petsYESRadioButton.setChecked(true);
                petsNORadioButton.setChecked(false);
            } else {
                petsYESRadioButton.setChecked(false);
                petsNORadioButton.setChecked(true);
            }
            petsYESRadioButton.setClickable(false);
            petsNORadioButton.setClickable(false);
            if (userState.getApartmentProfile().hasWashingMachine()) {
                washingMashineYESRadioButton.setChecked(true);
                washingMashineNORadioButton.setChecked(false);
            } else {
                washingMashineYESRadioButton.setChecked(false);
                washingMashineNORadioButton.setChecked(true);
            }
            washingMashineYESRadioButton.setClickable(false);
            washingMashineNORadioButton.setClickable(false);
        }
    }

    @Override
    public void changeToApartmentProfileActivity() {

    }

    @Override
    public void onNicknameError(String error) {

    }

    @Override
    public void changeToRoommateQRActivity(String id) {

    }

    @Override
    public void onNicknameUnique() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
