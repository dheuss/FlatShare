package com.flatshare.presentation.ui.activities.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.profile.RoommateProfilePresenter;
import com.flatshare.presentation.ui.AbstarctFragmentAcivity;

/**
 * Created by david on 18.01.2017.
 */

public class RoommateProfileSettingsActivity extends AbstarctFragmentAcivity implements RoommateProfilePresenter.View {

    private static final String TAG = "ProfileTenantSettingsFilterActivity";
    private RoommateProfilePresenter mPresenter;
    private UserState userState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void changeToApartmentProfileActivity() {

    }

    @Override
    public void onNicknameError(String error) {

    }

    @Override
    public void changeToRoommateQRActivity(String id) {

    }

    @Override
    public void onNicknameUnique() {

    }

    @Override
    public void showError(String message) {

    }
}
