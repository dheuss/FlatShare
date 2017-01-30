package com.flatshare.presentation.ui.activities.auth.login.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatshare.R;
/**
 * Created by david on 06/12/2016.
 */
public class LoginFragmentThreeActivity extends Fragment {

    public LoginFragmentThreeActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_login_fragment_three, container, false);
    }
}
