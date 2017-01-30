package com.flatshare.presentation.ui.activities.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.repository.StorageRepository;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.settings.SettingsPresenter;
import com.flatshare.presentation.presenters.settings.impl.SettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractFragmentActivity;
import com.flatshare.presentation.ui.activities.auth.login.LoginActivity;
import com.flatshare.threading.MainThreadImpl;

import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by david on 06/12/2016.
 */
public class SettingsActivity extends AbstractFragmentActivity implements SettingsPresenter.View {

    private CircleImageView settingsCircleImageView;

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

    private UserState userState;
    private ApartmentProfile apartmentProfile;
    private TenantProfile tenantProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        bindView(view);

        Log.d(TAG, "inside onCreate(), creating presenter for this view");

        mPresenter = new SettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        mPresenter.getProfilePicture();

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

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.deleteAccount();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        return view;
    }

    private void bindView(View view){
        settingsCircleImageView = (CircleImageView)view.findViewById(R.id.settings_round_image);

        btnChangeEmail = (Button) view.findViewById(R.id.change_email_button);
        btnChangePassword = (Button) view.findViewById(R.id.change_password_button);
        btnSendResetEmail = (Button) view.findViewById(R.id.sending_pass_reset_button);
        btnRemoveUser = (Button) view.findViewById(R.id.remove_user_button);
        changeEmail = (Button) view.findViewById(R.id.changeEmail);
        changePassword = (Button) view.findViewById(R.id.changePass);
        sendEmail = (Button) view.findViewById(R.id.send);
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
    }

    public void changeMail(){
        popUpView(0);
    }

    public void changeToNewPassword() {
        popUpView(1);
    }

    public void resetEmail(){
        popUpView(2);
    }

    public void deleteAccount() {
        popUpView(3);
    }


    public void signOut() {
        popUpView(4);
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
        Log.v(TAG, ": logout Scucess");
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void showApartmentImage(Bitmap apartmentImage) {
        settingsCircleImageView.setImageBitmap(apartmentImage);
    }

    @Override
    public void showTenantImage(Bitmap tenantImage) {
        settingsCircleImageView.setImageBitmap(tenantImage);
    }

    private void popUpView(final int task){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View mView = layoutInflater.inflate(R.layout.activity_popup, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(mView);

        final TextView popUpTextView = (TextView) mView.findViewById(R.id.popup_TextView);

        if (task == 0){
            popUpTextView.setText("Do you really want to change your emailaddress?");
        } else if (task == 1){
            popUpTextView.setText("Do you really want to change your password?");
        } else if (task == 2){
            popUpTextView.setText("Do you really want to reset your account?");
        } else if (task == 3){
            popUpTextView.setText("Do you really want to delte your account?");
        } else if (task == 4){
            popUpTextView.setText("Do you really want to sign out?");
        } else {
            popUpTextView.setText("Do you really want to sign out?");
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (task == 0){
                            mPresenter.changeMailAddress(newEmail.getText().toString());
                        } else if (task == 1){
                            mPresenter.changePassword(newPassword.getText().toString());
                        } else if (task == 2){
                            mPresenter.resetPasswordMail(oldEmail.getText().toString());
                        } else if (task == 3){
                            mPresenter.deleteAccount();
                        } else if (task == 4){
                            mPresenter.logOut();
                        } else {
                            mPresenter.logOut();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }
}
