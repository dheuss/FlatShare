package com.flatshare.presentation.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.presentation.presenters.profile.PrimaryProfilePresenter;
import com.flatshare.presentation.presenters.profile.impl.PrimaryProfilePresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

/**
 * Created by Arber on 16/12/2016.
 */
public class PrimaryProfileActivity extends AbstractActivity implements PrimaryProfilePresenter.View {


    private Button createTenantProfileButton;
    private Button createApartmentProfileButton;

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

        createTenantProfileButton.setOnClickListener(view -> sendProfile(0));
        createApartmentProfileButton.setOnClickListener(view -> sendProfile(1));

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_primary_profile;
    }

    private void sendProfile(int classificationId) {

        mPresenter.sendProfile(new PrimaryUserProfile(classificationId));

    }

    private void bindView() {
        createTenantProfileButton = (Button) findViewById(R.id.create_tenant_profile_button);
        createApartmentProfileButton = (Button) findViewById(R.id.create_apartment_profile_button);
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
}
