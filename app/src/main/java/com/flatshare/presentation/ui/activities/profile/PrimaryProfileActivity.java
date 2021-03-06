package com.flatshare.presentation.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.presentation.presenters.profile.PrimaryProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.PrimaryProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;
/**
 * Created by david on 06/12/2016.
 */

public class PrimaryProfileActivity extends AbstractActivity implements PrimaryProfilePresenter.View {

    private Button createTenantProfileButton;
    private Button createRoommateProfileButton;

    private PrimaryProfilePresenter mPresenter;
    private static final String TAG = "PrimaryProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new PrimaryProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        createTenantProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendProfile(ProfileType.TENANT.getValue());
            }
        });

        createRoommateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProfile(ProfileType.ROOMMATE.getValue());
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_primary_profile;
    }

    private void sendProfile(int classificationId) {

        PrimaryUserProfile primaryUserProfile = new PrimaryUserProfile();
        primaryUserProfile.setClassificationId(classificationId);

        mPresenter.sendProfile(primaryUserProfile);

    }

    private void bindView() {
        createTenantProfileButton = (Button) findViewById(R.id.create_tenant_profile_button);
        createRoommateProfileButton = (Button) findViewById(R.id.create_roommate_profile_button);
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
    public void changeToTenantProfile() {
        Intent intent = new Intent(this, TenantProfileActivity.class);
        startActivity(intent);
        Log.d("LoginActivity", "success! changed to TenantProfileActivity!");
    }

    @Override
    public void changeToRoommateActivity() {
        Intent intent = new Intent(this, RoommateProfileActivity.class);
        startActivity(intent);
        Log.d("LoginActivity", "success! changed to ApartmentProfileActivity!");
    }

}
