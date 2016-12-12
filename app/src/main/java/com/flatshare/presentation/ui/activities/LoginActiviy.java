package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.auth.LoginDataType;
import com.flatshare.domain.executor.impl.ThreadExecutor;
import com.flatshare.presentation.presenters.LoginPresenter;
import com.flatshare.presentation.presenters.impl.LoginPresenterImpl;
import com.flatshare.threading.MainThreadImpl;


public class LoginActiviy extends AppCompatActivity implements LoginPresenter.View {


//    @Bind(R.id.email_edittext)
    private EditText emailEditText;

//    @Bind(R.id.password_edittext)
    private EditText passwordEditText;

//    @Bind(R.id.login_button)
    private Button loginButton;

//    @Bind(R.id.register_textview)
    private TextView registerTextView;

    private LoginPresenter mPresenter;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        ButterKnife.bind(this);

        bindView();

        // create a presenter for this view
        mPresenter = new LoginPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );


        loginButton.setOnClickListener(view -> login());


    }

    private void bindView() {
        //    @Bind(R.id.email_edittext)
        emailEditText = (EditText) findViewById(R.id.email_edittext);

//    @Bind(R.id.password_edittext)
        passwordEditText = (EditText) findViewById(R.id.password_edittext);

//    @Bind(R.id.login_button)
        loginButton = (Button) findViewById(R.id.login_button);

//    @Bind(R.id.register_textview)
        registerTextView = (TextView) findViewById(R.id.register_textview);
    }

    private void login() {
        mPresenter.login(new LoginDataType(emailEditText.getText().toString(), passwordEditText.getText().toString()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showProgress() {
        loginButton.setText("Retrieving...");
    }

    @Override
    public void hideProgress() {
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
