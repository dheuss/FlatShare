package com.flatshare.presentation.ui.activities.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.flatshare.R;
import com.flatshare.presentation.ui.AbstractActivity;

public class ProfileApartmentSettingsFilterActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apartment_settings;
    }
}
