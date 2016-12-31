package com.flatshare.presentation.ui.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.flatshare.R;
import com.flatshare.presentation.presenters.SettingsPresenter;
import com.flatshare.presentation.presenters.impl.SettingsPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class SettingsActivity extends AbstractActivity implements SettingsPresenter.View {

    private ImageButton profilSettingsBackButton;

    private static final String TAG = "SettingsActivity";

    private SettingsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        Log.d(TAG, "inside onCreate(), creating presenter fr this view");

        mPresenter = new SettingsPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        profilSettingsBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SettingsActivity.this, ProfilSettingsActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settings;    }

    private void bindView(){
        profilSettingsBackButton = (ImageButton)findViewById(R.id.settingsPorilfBackBtn);
    }

    @Override
    public void showError(String message) {

    }
}
