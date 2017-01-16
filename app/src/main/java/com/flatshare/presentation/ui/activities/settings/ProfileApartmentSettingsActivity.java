package com.flatshare.presentation.ui.activities.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.domain.interactors.settings.ProfileApartmentSettingsInteractor;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.settings.ProfileApartmentSettingsPresenter;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;

import java.util.Map;

public class ProfileApartmentSettingsActivity extends AbstarctFragmentAcivity implements ProfileApartmentSettingsPresenter.View {

    private TextView apartmentInitialTextView;
    private TextView apartmentInfoTextView;
    private Button qrButton;

    private UserState userState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userState = UserState.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_apartment_profile, container, false);
        return view;
    }

    @Override
    public void changeToMatchingActivity() {

    }

    @Override
    public void updateAdapter(Map<String, String> idEmailsMap) {

    }

    @Override
    public void showError(String message) {

    }
}
