package com.flatshare.presentation.ui.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.flatshare.R;
import com.flatshare.presentation.presenters.settings.SettingsPresenter;
import com.flatshare.presentation.presenters.settings.impl.SettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.auth.login.LoginActivity;
import com.flatshare.threading.MainThreadImpl;

public class SettingsActivity extends AbstarctFragmentAcivity implements SettingsPresenter.View {

    private Button btnChangeEmail;
    private Button btnChangePassword;
    private Button btnSendResetEmail;
    private Button btnRemoveUser;
    private Button changeEmail;
    private Button changePassword;
    private Button sendEmail;
    private Button remove;
    private Button signOut;

    private EditText oldEmail;
    private EditText newEmail;
    private EditText password;
    private EditText newPassword;

    private ProgressBar progressBar;

    private static final String TAG = "SettingsActivity";

    private SettingsPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        bindView(view);

        Log.d(TAG, "inside onCreate(), creating presenter fr this view");

        mPresenter = new SettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.changeMail();
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.signOut();
            }
        });
        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.deleteAccount();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.changeToNewPassword();
            }
        });
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.resetEmail();
            }
        });

        return view;
    }

    private void bindView(View view){
        btnChangeEmail = (Button) view.findViewById(R.id.change_email_button);
        btnChangePassword = (Button) view.findViewById(R.id.change_password_button);
        btnSendResetEmail = (Button) view.findViewById(R.id.sending_pass_reset_button);
        btnRemoveUser = (Button) view.findViewById(R.id.remove_user_button);
        changeEmail = (Button) view.findViewById(R.id.changeEmail);
        changePassword = (Button) view.findViewById(R.id.changePass);
        sendEmail = (Button) view.findViewById(R.id.send);
        remove = (Button) view.findViewById(R.id.remove);
        signOut = (Button) view.findViewById(R.id.sign_out);

        oldEmail = (EditText) view.findViewById(R.id.old_email);
        newEmail = (EditText) view.findViewById(R.id.new_email);
        password = (EditText) view.findViewById(R.id.password);
        newPassword = (EditText) view.findViewById(R.id.newPassword);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);
    }

    public void signOut() {
        mPresenter.logOut();
    }

    public void changeMail(){
        mPresenter.changeMailAddress(newEmail.getText().toString());
    }

    public void deleteAccount() {
        mPresenter.deleteAccount();
    }

    public void changeToNewPassword() {
        mPresenter.changePassword(newPassword.getText().toString());
    }

    public void resetEmail(){
        mPresenter.resetPasswordMail(oldEmail.getText().toString());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void changeToLoginActivity() {
    }
}
