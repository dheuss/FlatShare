package com.flatshare.presentation.ui.activities.auth.login.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.flatshare.R;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.presentation.presenters.auth.LoginPresenter;
import com.flatshare.presentation.presenters.auth.impl.LoginPresenterImpl;
import com.flatshare.presentation.ui.activities.auth.RegisterActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.presentation.ui.activities.matching.RoommateQRActivity;
import com.flatshare.presentation.ui.activities.profile.ApartmentSettingsActivity;
import com.flatshare.presentation.ui.activities.profile.PrimaryProfileActivity;
import com.flatshare.presentation.ui.activities.profile.TenantSettingsActivity;
import com.flatshare.threading.MainThreadImpl;
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
    private ImageButton googleCustomButton;

    //Facebook
    private LoginButton facebookSignInButton;
    private ImageButton facebookCustomButton;
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

        facebookCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookSignInButton.performClick(); //TODO
                System.out.println("FACEBOOK");
            }
        });

        googleCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInButton.performClick(); //TODO
                googleLogin();
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
        googleCustomButton = (ImageButton) view.findViewById(R.id.custom_google_button);
        facebookCustomButton = (ImageButton) view.findViewById(R.id.custom_fb_button);
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
//            login();
        }
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mPresenter.login(new LoginDataType(email, password));
    }

    private void googleLogin(){

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
        getActivity().finish();
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
        getActivity().finish();
    }

    @Override
    public void changeToTenantSettingsActivity() {
        Log.d("LoginActivity", "success! changed to TenantSettingsActivity!");
        Intent intent = new Intent(getActivity(), TenantSettingsActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void changeToApartmentSettingsActivity() {
        Log.d("LoginActivity", "success! changed to ApartmentSettingsActivity!");
        Intent intent = new Intent(getActivity(), ApartmentSettingsActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void notifyRoommateGenerateQR(String roommateId) {

            Intent intent = new Intent(getActivity(), RoommateQRActivity.class);
            Bundle b = new Bundle();
            b.putString("id", roommateId);
            intent.putExtras(b);
            startActivity(intent);
//            getActivity().finish();
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
