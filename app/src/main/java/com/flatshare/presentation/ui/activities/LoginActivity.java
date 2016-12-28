package com.flatshare.presentation.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.flatshare.R;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.presentation.presenters.LoginPresenter;
import com.flatshare.presentation.presenters.impl.LoginPresenterImpl;
import com.flatshare.threading.MainThreadImpl;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    //Google
    private SignInButton googleSignInButton;

    //Facebook
    private LoginButton facebookSignInButton;
    private CallbackManager facebookCallbackManager;

    // loader
    private AlertDialog progressDialog;

    private LoginPresenter mPresenter;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

        bindView();
        facebookCallbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // create a presenter for this view
        mPresenter = new LoginPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        //TODO delete authentication
        emailEditText.setText("david.heuss@web.de");
        passwordEditText.setText("123456");

        loginButton.setOnClickListener(view -> login());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void bindView() {
        emailEditText = (EditText) findViewById(R.id.email_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        googleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        facebookSignInButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
        progressDialog = new SpotsDialog(this, R.style.Custom);
    }

    private void login() {
        mPresenter.login(new LoginDataType(emailEditText.getText().toString(), passwordEditText.getText().toString()));
    }

    @Override
    protected void onDestroy() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
        Toast.makeText(this, "Retrieved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
//        mWelcomeTextView.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToProfileActivity() {
        //TODO change to PrimaryProfileActivity
        Log.d("LoginActivity", "success! changed to PrimaryProfileActivity!");
        Intent intent = new Intent(this, PrimaryProfileActivity.class);
        startActivity(intent);
    }
}