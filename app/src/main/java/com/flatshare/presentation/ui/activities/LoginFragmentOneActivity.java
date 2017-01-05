package com.flatshare.presentation.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatshare.R;
import com.flatshare.presentation.presenters.LoginPresenter;

public class LoginFragmentOneActivity extends Fragment{

    public LoginFragmentOneActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_login_fragment_one, container, false);
    }
}
