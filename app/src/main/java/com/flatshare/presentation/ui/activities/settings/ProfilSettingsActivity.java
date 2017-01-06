package com.flatshare.presentation.ui.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.flatshare.R;
import com.flatshare.presentation.presenters.settings.ProfileSettingsPresenter;
import com.flatshare.presentation.presenters.settings.impl.ProfileSettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

public class ProfilSettingsActivity extends AbstractActivity implements ProfileSettingsPresenter.View {

    private ImageButton settingsButton;
    private ImageButton matchingActivityButton;

    private static final String TAG = "ProfilSettingsActivity";

    private ProfileSettingsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        Log.d(TAG, "inside onCreate(), creating presenter fr this view");

        mPresenter = new ProfileSettingsPresenterImpl(
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
