package com.flatshare.presentation.ui.activities.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;
import com.flatshare.presentation.ui.activities.MainActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class ProfileApartmentSettingsActivity extends AbstarctFragmentAcivity implements ApartmentProfilePresenter.View {

    private ApartmentProfilePresenter mPresenter;

    private UserState userState;

    private TextView apartmentProfileHeadline;
    private TextView apartmentProfileInfo;

    private MultiAutoCompleteTextView roommatesTextView;
    private Map<String, String> nicknameIdMap;
    private EditText priceEditText;
    private EditText sizeEditText;
    private EditText streetEditText;
    private EditText houseNumberEditText;
    private EditText zipCodeEditText;

    private RadioGroup internetRadioGroup;
    private RadioButton internetYESRadioButton, internetNORadioButton;

    private RadioGroup smokerRadioGroup;
    private RadioButton smokerYESRadioButton, smokerNORadioButton;

    private RadioGroup petsRadioGroup;
    private RadioButton petsYESRadioButton, petsNORadioButton;

    private RadioGroup washingMashineRadioGroup;
    private RadioButton washingMashineYESRadioButton, washingMashineNORadioButton;

    private Button qrButton;
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

        mPresenter = new ApartmentProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileApartmentSettingsFilterActivity.class));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileApartmentSettingsActivity.this.sendProfile();
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
                        String houseNr = houseNumberEditText.getText().toString();
                        int zip = Integer.parseInt(zipCodeEditText.getText().toString());

                        boolean internet = internetRadioGroup.getCheckedRadioButtonId() == internetYESRadioButton.getId();
                        boolean smoker = smokerRadioGroup.getCheckedRadioButtonId() == smokerYESRadioButton.getId();
                        boolean pets = petsRadioGroup.getCheckedRadioButtonId() == petsYESRadioButton.getId();
                        boolean washingMashine = washingMashineRadioGroup.getCheckedRadioButtonId() == washingMashineYESRadioButton.getId();

                        ApartmentLocation apartmentLocation = new ApartmentLocation();

                        apartmentLocation.setStreet(street);
                        apartmentLocation.setHouseNr(houseNr);
                        apartmentLocation.setZipCode(zip);

                        ApartmentProfile apartmentProfile = new ApartmentProfile();
                        apartmentProfile.setPrice(price);
                        apartmentProfile.setArea(size);
                        apartmentProfile.setInternet(internet);
                        apartmentProfile.setSmokerApartment(smoker);
                        apartmentProfile.setPets(pets);
                        apartmentProfile.setWashingMachine(washingMashine);

                        apartmentProfile.setApartmentLocation(apartmentLocation);

                        apartmentProfile.setRoommateIds(roommatesId);

                        mPresenter.sendProfile(apartmentProfile);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
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

        houseNumberEditText = (EditText)view.findViewById(R.id.apartment_house_nr_edit_text);
        houseNumberEditText.setText(userState.getApartmentProfile().getApartmentLocation().getHouseNr()+"");

        zipCodeEditText = (EditText)view.findViewById(R.id.apartment_zip_code_edit_text);
        zipCodeEditText.setText(userState.getApartmentProfile().getApartmentLocation().getZipCode()+"");

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

        qrButton = (Button) view.findViewById(R.id.scan_roommate_QR_button);
        qrButton.setClickable(false);
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
