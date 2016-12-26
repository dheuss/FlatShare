package com.flatshare.presentation.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

import dmax.dialog.SpotsDialog;

public class TenantSettingsActivity extends AbstractActivity implements TenantSettingsPresenter.View {

    private TextView internetTextView;

    private RadioGroup internetRadioGroup;
    private RadioButton internetYesRadioButton, internetNoRadioButton;

    private TextView wMachineTextView;
    private RadioGroup wMachineRadioGroup;
    private RadioButton wMachineYesRadioButton, wMachineNoRadioButton;

    private Button profileDoneButton;

    private TenantSettingsPresenter mPresenter;
    private static final String TAG = "TenantProfileAct";
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        bindView();

        // create a presenter for this view
        mPresenter = new TenantSettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );


        profileDoneButton.setOnClickListener(view -> sendFilterSettings());


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tenant_settings;
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

        progressDialog = new SpotsDialog(this, R.style.Custom);

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
    public void changeToMatchingActivity() {
        //TODO change to MatchingActivity
        Log.d(TAG, "success! changed to TenantSettings!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
