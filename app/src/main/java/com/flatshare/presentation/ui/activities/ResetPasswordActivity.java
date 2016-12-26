package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.auth.ResetDataType;
import com.flatshare.presentation.presenters.ResetPasswordPresenter;
import com.flatshare.presentation.presenters.impl.ResetPasswordPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

/**
 * Created by david on 19.12.2016.
 */

public class ResetPasswordActivity extends AbstractActivity implements ResetPasswordPresenter.View {

    private EditText emailResetPasswordEditText;
    private Button resetPasswordButton;
    private Button backResetPasswordButton;

    private ResetPasswordPresenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new ResetPasswordPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        resetPasswordButton.setOnClickListener(view -> reset());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_reset_password;
    }

    private void bindView() {
        emailResetPasswordEditText = (EditText) findViewById(R.id.email_reset_password_edittext);
        resetPasswordButton = (Button) findViewById(R.id.reset_password_button);
        backResetPasswordButton = (Button) findViewById(R.id.back_reset_password_button);
    }

    public void reset() {
        mPresenter.reset(new ResetDataType(emailResetPasswordEditText.getText().toString()));
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }


    @Override
    public void changeToProfileActivity() {
        //TODO change to PrimaryProfileActivity
        Log.d("LoginActivity", "success! changed to PrimaryProfileActivity!");
        Intent intent = new Intent(this, PrimaryProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
