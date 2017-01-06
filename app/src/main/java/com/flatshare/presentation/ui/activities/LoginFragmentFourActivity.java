package com.flatshare.presentation.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.flatshare.R;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.presentation.presenters.LoginPresenter;
import com.flatshare.presentation.presenters.impl.LoginPresenterImpl;
import com.flatshare.threading.MainThreadImpl;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import dmax.dialog.SpotsDialog;

public class LoginFragmentFourActivity extends Fragment implements LoginPresenter.View {

    private EditText emailEditText;
    private EditText passwordEditText;

    private CheckBox loginCheckBox;

    private Button loginButton;
    private Button registerButton;
    private SharedPreferences sharedPref;

    //Google
    private SignInButton googleSignInButton;

    //Facebook
    private LoginButton facebookSignInButton;
    private CallbackManager facebookCallbackManager;

    // loader
    private AlertDialog progressDialog;

    private LoginPresenter mPresenter;
    private static final String TAG = "LoginActivity";

    public LoginFragmentFourActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login_fragment_four, container, false);

        sharedPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        bind(view);

        mPresenter = new LoginPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        readSharedPreferences();
        facebookCallbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });

        return view;
    }

    public void bind(View view) {
        emailEditText = (EditText) view.findViewById(R.id.email_edittext);
        passwordEditText = (EditText) view.findViewById(R.id.password_edittext);
        loginCheckBox = (CheckBox) view.findViewById(R.id.login_check_box);
        loginButton = (Button) view.findViewById(R.id.login_button);
        registerButton = (Button) view.findViewById(R.id.register_button);
        googleSignInButton = (SignInButton) view.findViewById(R.id.google_sign_in_button);
        facebookSignInButton = (LoginButton) view.findViewById(R.id.facebook_sign_in_button);
        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
    }

    private void readSharedPreferences() {

        boolean rememberLogin = sharedPref.getBoolean(getResources().getString(R.string.login_check_box), false);

        loginCheckBox.setChecked(rememberLogin);

        String email;
        String password;

        if (rememberLogin) {
            email = sharedPref.getString(getResources().getString(R.string.email_login), null);
            password = sharedPref.getString(getResources().getString(R.string.password_login), null);
        } else {
            return;
        }

        if (email != null && password != null) {

            emailEditText.setText(email);
            passwordEditText.setText(password);
            login();
        }
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mPresenter.login(new LoginDataType(email, password));
    }

    private void writeToSharedPreferences(int key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(key), value);
        editor.apply();
    }

    private void writeToSharedPreferences(int key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(key), value);
        editor.apply();
    }

    @Override
    public void changeToProfileActivity() {
        Log.d("LoginActivity", "success! changed to PrimaryProfileActivity!");
        loginSuccess();
        Intent intent = new Intent(getActivity(), PrimaryProfileActivity.class);
        startActivity(intent);
    }

    private void loginSuccess() {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        writeToSharedPreferences(R.string.email_login, email);
        writeToSharedPreferences(R.string.password_login, password);
        writeToSharedPreferences(R.string.login_check_box, loginCheckBox.isChecked());

    }

    @Override
    public void changeToMatchingActivity() {
        Log.d("LoginActivity", "success! changed to MatchingActivity!");
        Intent intent = new Intent(getActivity(), MatchingActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onDestroy() {
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
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        writeToSharedPreferences(R.string.login_check_box, false);
    }
}
