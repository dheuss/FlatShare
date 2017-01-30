package com.flatshare.presentation.ui.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.presentation.presenters.auth.ResetPasswordPresenter;
import com.flatshare.presentation.presenters.auth.impl.ResetPasswordPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.auth.login.LoginActivity;
import com.flatshare.threading.MainThreadImpl;
/**
 * Created by david on 06/12/2016.
 */
public class ResetPasswordActivity extends AbstractActivity implements ResetPasswordPresenter.View {

    private EditText emailResetPasswordEditText;
    private Button resetPasswordButton;

    private ResetPasswordPresenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new ResetPasswordPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPasswordActivity.this.reset();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_reset_password;
    }

    private void bindView() {
        emailResetPasswordEditText = (EditText) findViewById(R.id.email_reset_password_edittext);
        resetPasswordButton = (Button) findViewById(R.id.reset_password_button);
    }

    public void reset() {
        mPresenter.reset(emailResetPasswordEditText.getText().toString());
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToLoginActivity() {
        Toast.makeText(this, "Please check your emails!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
