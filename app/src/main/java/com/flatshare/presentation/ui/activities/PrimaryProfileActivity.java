package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.PrimaryUserProfile;
import com.flatshare.presentation.presenters.PrimaryProfilePresenter;
import com.flatshare.presentation.presenters.impl.PrimaryProfilePresenterImpl;
import com.flatshare.threading.MainThreadImpl;

public class PrimaryProfileActivity extends AppCompatActivity implements PrimaryProfilePresenter.View {


    private Button createTenantProfileButton;
    private Button createApartmentProfileButton;

    private PrimaryProfilePresenter mPresenter;
    private static final String TAG = "PrimaryProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_profile);

        bindView();

        // create a presenter for this view
        mPresenter = new PrimaryProfilePresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        createTenantProfileButton.setOnClickListener(view -> sendProfile(0));
        createApartmentProfileButton.setOnClickListener(view -> sendProfile(1));

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
    public void showProgress() {
        Log.d(TAG, "showing Progress");
    }

    @Override
    public void hideProgress() {
        Toast.makeText(this, "done!", Toast.LENGTH_LONG).show();
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
