package com.flatshare.presentation.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
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

/**
 * Created by Arber on 16/01/2017.
 */
public class RoommateProfileActivity extends AbstractActivity implements RoommateProfilePresenter.View {

    private static final String TAG = "RoommateProfileActivity";

    private EditText nickNameEditText;
    private EditText ageEditText;

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;

    private PopupWindow popupWindow;
    private Button popupYESButton;
    private Button popupNOButton;
    private TextView popUpTextView;

    private Button createProfileButton;

    private RoommateProfilePresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        // create a presenter for this view
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
        String nickname = nickNameEditText.getText().toString();
        if(nickname == null || nickname.length() < 6){
            onNicknameError("Nickname too short, minimum of 6 characters needed");
            return;
        }

        if(!nickname.matches("^[a-zA-Z0-9]*$")){
            onNicknameError("Nickname contains invalid characters, enter only alphanumeric characters");
            return;
        }

        mPresenter.checkNicknameUnique(nickname);
    }

    @Override
    public void onNicknameError(String error) {
        nickNameEditText.setError(error);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_roommate_profile;
    }

//    private void sendProfile(int classificationId) {
//
//        PrimaryUserProfile primaryUserProfile = new PrimaryUserProfile();
//        primaryUserProfile.setClassificationId(classificationId);
//
//        mPresenter.sendProfile(primaryUserProfile);
//
//    }

    private void bindView() {

        nickNameEditText = (EditText) findViewById(R.id.roommate_nickname_edit_text);
        ageEditText = (EditText) findViewById(R.id.roommate_age_edit_text);

        genderRadioGroup = (RadioGroup) findViewById(R.id.roommate_gender_rg);
        maleRadioButton = (RadioButton) findViewById(R.id.roommate_gender_male);
        femaleRadioButton = (RadioButton) findViewById(R.id.roommate_gender_female);
        createProfileButton =  (Button) findViewById(R.id.roommate_profile_done_button);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void triggerPopup(View view) {
        View popupView = getLayoutInflater().inflate(R.layout.activity_popup, null);

        popupWindow = new PopupWindow(
                popupView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(false);
        int location[] = new int[2];
        view.getLocationOnScreen(location);

        popupYESButton = (Button)popupView.findViewById(R.id.yes_logout_button);
        popupNOButton = (Button)popupView.findViewById(R.id.no_logout_button);
        popUpTextView = (TextView)popupView.findViewById(R.id.logout_popup_TextView);

        popUpTextView.setText("Are you the owner of this apartment?");

        popupYESButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileDone(true);
            }
        });

        popupNOButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDone(false);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
    }

    private void profileDone(boolean isOwner) {

        popupWindow.dismiss();

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
        triggerPopup(this.findViewById(android.R.id.content));
    }

    @Override
    public void changeToRoommateQRActivity(String roommateId) {

        Intent intent = new Intent(this, RoommateQRActivity.class);
        Bundle b = new Bundle();
        b.putString("id", roommateId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void changeToApartmentProfileActivity(){
        Log.d(TAG, "changed to ApartmentProfileActivity");
        Intent intent = new Intent(this, ApartmentProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
