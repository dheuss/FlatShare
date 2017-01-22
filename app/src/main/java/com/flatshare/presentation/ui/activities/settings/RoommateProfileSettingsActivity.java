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
    private EditText sizeEditText;
    private EditText streetEditText;
    private EditText zipCodeEditText;

    private RadioButton internetYESRadioButton, internetNORadioButton;

    private RadioButton smokerYESRadioButton, smokerNORadioButton;

    private RadioButton petsYESRadioButton, petsNORadioButton;

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

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileApartmentSettingsFilterActivity.class));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You're not allowed to click this button ,sorry!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void bindView(View view){
        apartmentProfileHeadline = (TextView)view.findViewById(R.id.apartment_profile_inital);
        apartmentProfileHeadline.setText("Roommate Profile");
        apartmentProfileInfo = (TextView)view.findViewById(R.id.apartment_profile_info);
        apartmentProfileInfo.setText("Here your can see all your apartment information! Sorry, you are not allowed to change anything!");

        roommatesTextView = (MultiAutoCompleteTextView)view.findViewById(R.id.apartment_roommates_edit_text);
        roommatesTextView.setText(userState.getApartmentProfile().getRoommateIds().size()+"");
        roommatesTextView.setEnabled(false);

        priceEditText = (EditText)view.findViewById(R.id.apartment_price_edit_text);
        priceEditText.setText(userState.getApartmentProfile().getPrice() + "");
        priceEditText.setEnabled(false);

        sizeEditText = (EditText)view.findViewById(R.id.apartment_area_editText);
        sizeEditText.setText(userState.getApartmentProfile().getArea() + "");
        sizeEditText.setEnabled(false);

        streetEditText = (EditText)view.findViewById(R.id.apartment_street_edit_text);
        streetEditText.setText(userState.getApartmentProfile().getApartmentLocation().getStreet()+"");
        streetEditText.setEnabled(false);

        zipCodeEditText = (EditText)view.findViewById(R.id.apartment_zip_code_edit_text);
        zipCodeEditText.setText(userState.getApartmentProfile().getApartmentLocation().getZipCode()+"");
        zipCodeEditText.setEnabled(false);

        internetYESRadioButton = (RadioButton)view.findViewById(R.id.internet_yes_rb);
        internetNORadioButton = (RadioButton)view.findViewById(R.id.internet_no_rb);
        if (userState.getApartmentProfile().hasInternet()){
            internetYESRadioButton.setChecked(true);
            internetNORadioButton.setChecked(false);
        } else {
            internetYESRadioButton.setChecked(false);
            internetNORadioButton.setChecked(true);
        }
        internetYESRadioButton.setClickable(false);
        internetNORadioButton.setClickable(false);

        smokerYESRadioButton = (RadioButton)view.findViewById(R.id.smoker_yes_rb);
        smokerNORadioButton = (RadioButton)view.findViewById(R.id.smoker_no_rb);
        if (userState.getApartmentProfile().isSmokerApartment()){
            smokerYESRadioButton.setChecked(true);
            smokerNORadioButton.setChecked(false);
        } else {
            smokerYESRadioButton.setChecked(false);
            smokerNORadioButton.setChecked(true);
        }
        smokerYESRadioButton.setClickable(false);
        smokerNORadioButton.setClickable(false);

        petsYESRadioButton = (RadioButton)view.findViewById(R.id.pets_yes_rb);
        petsNORadioButton = (RadioButton)view.findViewById(R.id.pets_no_rb);
        if (userState.getApartmentProfile().hasPets()){
            petsYESRadioButton.setChecked(true);
            petsNORadioButton.setChecked(false);
        } else {
            petsYESRadioButton.setChecked(false);
            petsNORadioButton.setChecked(true);
        }
        petsYESRadioButton.setClickable(false);
        petsNORadioButton.setClickable(false);

        washingMashineYESRadioButton = (RadioButton)view.findViewById(R.id.washing_machine_yes_rb);
        washingMashineNORadioButton = (RadioButton)view.findViewById(R.id.washing_machine_no_rb);
        if (userState.getApartmentProfile().hasWashingMachine()){
            washingMashineYESRadioButton.setChecked(true);
            washingMashineNORadioButton.setChecked(false);
        } else {
            washingMashineYESRadioButton.setChecked(false);
            washingMashineNORadioButton.setChecked(true);
        }
        washingMashineYESRadioButton.setClickable(false);
        washingMashineNORadioButton.setClickable(false);

        qrButton = (Button) view.findViewById(R.id.scan_roommate_QR_button);
        qrButton.setClickable(false);
        changeButton = (Button) view.findViewById(R.id.upload_picture_apartment);
        changeButton.setText("CHANGE YOUR SETTINGS!");
        saveButton = (Button) view.findViewById(R.id.create_apartment_profile_button);
        saveButton.setText("SAVE!");
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
