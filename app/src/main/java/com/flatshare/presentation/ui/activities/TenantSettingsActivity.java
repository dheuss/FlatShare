package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.filters.TenantFilterSettings;
import com.flatshare.presentation.presenters.TenantSettingsPresenter;
import com.flatshare.presentation.presenters.impl.TenantSettingsPresenterImpl;
import com.flatshare.threading.MainThreadImpl;

public class TenantSettingsActivity extends AppCompatActivity implements TenantSettingsPresenter.View {

    private TextView internetTextView;

    private RadioGroup internetRadioGroup;
    private RadioButton internetYesRadioButton, internetNoRadioButton;

    private TextView wMachineTextView;
    private RadioGroup wMachineRadioGroup;
    private RadioButton wMachineYesRadioButton, wMachineNoRadioButton;

    private Button profileDoneButton;

    private TenantSettingsPresenter mPresenter;
    private static final String TAG = "TenantProfileAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_settings);

        bindView();

        // create a presenter for this view
        mPresenter = new TenantSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );


        profileDoneButton.setOnClickListener(view -> sendFilterSettings());


    }

    private void sendFilterSettings() {

        //TODO: read from view components, fill the settings object

        TenantFilterSettings tenantFilterSettings = new TenantFilterSettings();
        mPresenter.sendFilterSettings(tenantFilterSettings);
    }

    private void bindView() {
        internetTextView = (TextView) findViewById(R.id.internet_textview);

        internetRadioGroup = (RadioGroup) findViewById(R.id.internet_radiogroup);

        internetYesRadioButton = (RadioButton) findViewById(R.id.internet_yes);
        internetNoRadioButton = (RadioButton) findViewById(R.id.internet_no);

        wMachineTextView = (TextView) findViewById(R.id.washing_machine_textview);
        wMachineRadioGroup = (RadioGroup) findViewById(R.id.washing_machine_radiogroup);
        wMachineYesRadioButton = (RadioButton) findViewById(R.id.washing_machine_yes);
        wMachineNoRadioButton = (RadioButton) findViewById(R.id.washing_machine_no);

        profileDoneButton = (Button) findViewById(R.id.tenant_settings_done_button);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showProgress() {
        //TODO: find some loading bar
    }

    @Override
    public void hideProgress() {
        Toast.makeText(this, "Retrieved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToMatchingActivity() {
        //TODO change to MatchingActivity
        Log.d(TAG, "success! changed to TenantSettings!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
