package com.flatshare.presentation.ui.activities.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.presentation.presenters.profile.ApartmentSettingsPresenter;
import com.flatshare.presentation.ui.AbstractActivity;

public class ProfileApartmentSettingsFilterActivity extends AbstractActivity implements ApartmentSettingsPresenter.View {

    @Override
    public void changeToMatchingActivity() {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apartment_settings;
    }

    @Override
    public void showError(String message) {

    }
}
