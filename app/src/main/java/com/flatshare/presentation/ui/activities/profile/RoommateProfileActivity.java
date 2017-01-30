package com.flatshare.presentation.ui.activities.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.RoommateProfile;
import com.flatshare.presentation.presenters.profile.RoommateProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.RoommateProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.RoommateQRActivity;
import com.flatshare.threading.MainThreadImpl;

import static com.flatshare.presentation.ui.activities.profile.ApartmentProfileActivity.ROOMMATE_ID;
/**
 * Created by david on 06/12/2016.
 */
public class RoommateProfileActivity extends AbstractActivity implements RoommateProfilePresenter.View {

    private static final String TAG = "RoommateProfileActivity";
    private static final int NICKNAME_LENGTH = 6;

    private EditText nickNameEditText;
    private EditText ageEditText;

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;

    private Button createProfileButton;

    private RoommateProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new RoommateProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNicknameUnique();
            }
        });
    }

    private void checkNicknameUnique() {
        String nickname = nickNameEditText.getText().toString().trim();
        boolean validationFlag = true;

        if (nickname == null || nickname.length() < NICKNAME_LENGTH) {
            onNicknameError("Nickname too short, minimum of " + NICKNAME_LENGTH + " characters needed");
            validationFlag = false;
        }

        if (!nickname.matches("^[a-zA-Z0-9]*$")) {
            onNicknameError("Nickname contains invalid characters, enter only alphanumeric characters");
            validationFlag = false;
        }

        if (ageEditText.getText().toString().trim().equals("")) {
            ageEditText.setError(getString(R.string.field_cannot_be_empty));
            validationFlag = false;
        }

        if (!maleRadioButton.isChecked() && !femaleRadioButton.isChecked()) {
            maleRadioButton.setError(getString(R.string.check_rb_error));
            validationFlag = false;
        }

        if (validationFlag) {
            mPresenter.checkNicknameUnique(nickname);
        }
    }

    @Override
    public void onNicknameError(String error) {
        nickNameEditText.setError(error);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_roommate_profile;
    }

    private void bindView() {

        nickNameEditText = (EditText) findViewById(R.id.roommate_nickname_edit_text);
        ageEditText = (EditText) findViewById(R.id.roommate_age_edit_text);

        genderRadioGroup = (RadioGroup) findViewById(R.id.roommate_gender_rg);
        maleRadioButton = (RadioButton) findViewById(R.id.roommate_gender_male);
        femaleRadioButton = (RadioButton) findViewById(R.id.roommate_gender_female);
        createProfileButton = (Button) findViewById(R.id.roommate_profile_done_button);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void popUpView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View mView = layoutInflater.inflate(R.layout.activity_popup, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final TextView popUpTextView = (TextView) mView.findViewById(R.id.popup_TextView);

        popUpTextView.setText("Are you the owner of this apartment?");


        alertDialogBuilderUserInput
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        profileDone(true);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        profileDone(false);
                    }
                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    private void profileDone(boolean isOwner) {

        RoommateProfile roommateProfile = new RoommateProfile();
        String nickname = nickNameEditText.getText().toString();
        roommateProfile.setNickname(nickname);

        int gender = genderRadioGroup.getCheckedRadioButtonId() == maleRadioButton.getId() ? 0 : 1;
        roommateProfile.setGender(gender);

        int age = Integer.parseInt(ageEditText.getText().toString());
        roommateProfile.setAge(age);

        roommateProfile.setOwner(isOwner);
        mPresenter.sendProfile(roommateProfile);
    }

    @Override
    public void onNicknameUnique() {
        popUpView();
    }

    @Override
    public void changeToRoommateQRActivity(String roommateId) {

        Intent intent = new Intent(this, RoommateQRActivity.class);
        Bundle b = new Bundle();
        b.putString(ROOMMATE_ID, roommateId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void changeToApartmentProfileActivity() {
        Log.d(TAG, "changed to ApartmentProfileActivity");
        Intent intent = new Intent(this, ApartmentProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
