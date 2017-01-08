package com.flatshare.presentation.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.presentation.presenters.profile.PrimaryProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.PrimaryProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.RoommateQRActivity;
import com.flatshare.threading.MainThreadImpl;

/**
 * Created by Arber on 16/12/2016.
 */
public class PrimaryProfileActivity extends AbstractActivity implements PrimaryProfilePresenter.View {


    private Button createTenantProfileButton;
    private Button createApartmentProfileButton;
    private Button createRoommateQRCodeButton;

    private PrimaryProfilePresenter mPresenter;
    private static final String TAG = "PrimaryProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        // create a presenter for this view
        mPresenter = new PrimaryProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        createTenantProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrimaryProfileActivity.this.sendProfile(0);
            }
        });
        createApartmentProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrimaryProfileActivity.this.sendProfile(1);
            }
        });

        createRoommateQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrimaryProfileActivity.this.createQRCode();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_primary_profile;
    }

    private void createQRCode() {
        mPresenter.createQRCode();
    }

    private void sendProfile(int classificationId) {

        PrimaryUserProfile primaryUserProfile = new PrimaryUserProfile();
        primaryUserProfile.setClassificationId(classificationId);

        mPresenter.sendProfile(primaryUserProfile);

    }

    private void bindView() {
        createTenantProfileButton = (Button) findViewById(R.id.create_tenant_profile_button);
        createApartmentProfileButton = (Button) findViewById(R.id.create_apartment_profile_button);
        createRoommateQRCodeButton = (Button) findViewById(R.id.create_roommate_qr_code_button);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // let's start welcome message retrieval when the app resumes
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
    public void changeToApartmentProfile() {
        Intent intent = new Intent(this, ApartmentProfileActivity.class);
        startActivity(intent);
        Log.d("LoginActivity", "success! changed to ApartmentProfileActivity!");
    }

    @Override
    public void changeToRoomateQR(String roommateId) {

        Intent intent = new Intent(this, RoommateQRActivity.class);
        Bundle b = new Bundle();
        b.putString("id", roommateId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}
