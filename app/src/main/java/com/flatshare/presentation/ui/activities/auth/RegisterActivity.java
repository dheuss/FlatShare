package com.flatshare.presentation.ui.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.auth.RegisterDataType;
import com.flatshare.presentation.presenters.auth.RegisterPresenter;
import com.flatshare.presentation.presenters.auth.impl.RegisterPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.auth.login.LoginActivity;
import com.flatshare.presentation.ui.activities.profile.PrimaryProfileActivity;
import com.flatshare.threading.MainThreadImpl;
/**
 * Created by david on 06/12/2016.
 */
public class RegisterActivity extends AbstractActivity implements RegisterPresenter.View {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;

    private Button registerButton;
    private Button alreadyRegisteredButton;

    private RegisterPresenter mPresenter;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new RegisterPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.register();
            }
        });


        alreadyRegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    private void bindView() {
        emailEditText = (EditText) findViewById(R.id.email_register_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_register_edittest);
        nameEditText = (EditText) findViewById(R.id.name_register_edittext);
        registerButton = (Button) findViewById(R.id.sign_up_register_button);
        alreadyRegisteredButton = (Button) findViewById(R.id.sign_in_register_button);
    }

    public void register() {
        mPresenter.register(new RegisterDataType(nameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void changeToProfileActivity() {
        Log.d(TAG, "success! changed to PrimaryProfileActivity!");
        Intent intent = new Intent(this, PrimaryProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
