package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.flatshare.R;
import com.flatshare.presentation.presenters.ProfilSettingsPresenter;
import com.flatshare.presentation.presenters.SettingsPresenter;
import com.flatshare.presentation.presenters.impl.ProfilSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfilSettingsActivity extends AbstractActivity implements ProfilSettingsPresenter.View {

    private ImageButton settingsButton;
    private ImageButton matchingActivityButton;

    private static final String TAG = "ProfilSettingsActivity";

    private ProfilSettingsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        Log.d(TAG, "inside onCreate(), creating presenter fr this view");

        mPresenter = new ProfilSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ProfilSettingsActivity.this, SettingsActivity.class));
            }
        });

        matchingActivityButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ProfilSettingsActivity.this, MatchingActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profil_settings;
    }

    private void bindView(){
        settingsButton = (ImageButton)findViewById(R.id.settingsBtn);
        matchingActivityButton = (ImageButton)findViewById(R.id.couchBtn);
    }


    @Override
    public void changeToTenantSettings() {

    }

    @Override
    public void uploadSucces() {

    }

    @Override
    public void showError(String message) {

    }
}
