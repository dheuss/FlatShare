package com.flatshare.presentation.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.common.ApartmentLocation;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.presentation.presenters.profile.ApartmentProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.ApartmentProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Arber on 16/12/2016.
 */
public class ApartmentProfileActivity extends AbstractActivity implements ApartmentProfilePresenter.View {

    private EditText apartmentPriceEditText;
    private EditText apartmentAreaEditText;
    private EditText apartmentStreetEditText;
    private EditText apartmentHouseNrEditText;
    private EditText apartmentZipCodeEditText;

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

    //    private Button profileDoneButton;
    private Button createApartmentButton;

    private ApartmentProfilePresenter mPresenter;
    private static final String TAG = "ApartmentProfileAct";
    private Map<String, String> emailIdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new ApartmentProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        initMultiAutoComplete();
        createApartmentButton.setOnClickListener(view -> sendProfile());

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apartment_profile;
    }

    private void sendProfile() {

        String[] emails = roommatesEmailsMultiAC.getText().toString().split(",");

        List<String> roommatesId = new ArrayList<>();

        for (int i = 0; i < emails.length; i++) {

            String id = emailIdMap.get(emails[i].trim());

//            System.out.println("email of " + i + ":" + emails[i].trim());
//            System.out.println("ID of " + i + ":" + id);

            if (id != null) {
                roommatesId.add(id);
            }
        }

        int price = Integer.parseInt(apartmentPriceEditText.getText().toString());
        int area = Integer.parseInt(apartmentAreaEditText.getText().toString());

        String street = apartmentStreetEditText.getText().toString();
        String houseNr = apartmentHouseNrEditText.getText().toString();
        int zipCode = Integer.parseInt(apartmentZipCodeEditText.getText().toString());

        boolean hasInternet = internetRadioGroup.getCheckedRadioButtonId() == internetYesRB.getId();
        boolean isSmoker = smokerRadioGroup.getCheckedRadioButtonId() == smokerYesRB.getId();
        boolean hasPets = petsRadioGroup.getCheckedRadioButtonId() == petsYesRB.getId();
        boolean hasWashingMachine = washingMachineRadioGroup.getCheckedRadioButtonId() == washingMachineYesRB.getId();

        ApartmentLocation apartmentLocation = new ApartmentLocation();

        apartmentLocation.setStreet(street);
        apartmentLocation.setHouseNr(houseNr);
        apartmentLocation.setZipCode(zipCode);

        ApartmentUserProfile apartmentUserProfile = new ApartmentUserProfile();
        apartmentUserProfile.setPrice(price);
        apartmentUserProfile.setArea(area);
        apartmentUserProfile.setInternet(hasInternet);
        apartmentUserProfile.setSmokerApartment(isSmoker);
        apartmentUserProfile.setPets(hasPets);
        apartmentUserProfile.setWashingMachine(hasWashingMachine);


        apartmentUserProfile.setRoommateIds(roommatesId);

        mPresenter.sendProfile(apartmentUserProfile);
    }

    private void bindView() {


        apartmentPriceEditText = (EditText) findViewById(R.id.apartment_price_edit_text);
        apartmentAreaEditText = (EditText) findViewById(R.id.apartment_area_editText);
        apartmentStreetEditText = (EditText) findViewById(R.id.apartment_street_edit_text);
        apartmentHouseNrEditText = (EditText) findViewById(R.id.apartment_house_nr_edit_text);
        apartmentZipCodeEditText = (EditText) findViewById(R.id.apartment_zip_code_edit_text);

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
    public void updateAdapter(Map<String, String> emailIdMap) {

        System.out.println("Updating adapter!");

        emailIdMap.clear();
        emailIdMap.put("a@a.de", "1");
        emailIdMap.put("b@b.de", "2");
        emailIdMap.put("c@c.de", "3");

        this.emailIdMap = emailIdMap;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(emailIdMap.keySet()));

//        System.out.println("item 0: " + adapter.getItem(0));
//        System.out.println("item 1: " + adapter.getItem(1));

        roommatesEmailsMultiAC.setAdapter(adapter);

        System.out.println("done updating");

    }

    private void initMultiAutoComplete() {
        roommatesEmailsMultiAC.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        roommatesEmailsMultiAC.setThreshold(2);
        mPresenter.getUserEmails();
    }
}
