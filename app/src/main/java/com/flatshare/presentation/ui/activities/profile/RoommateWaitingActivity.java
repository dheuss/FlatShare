package com.flatshare.presentation.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.flatshare.R;
import com.flatshare.presentation.presenters.matching.RoommateWaitingPresenter;
import com.flatshare.presentation.presenters.matching.impl.RoommateWaitingPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.MainActivity;
import com.flatshare.presentation.ui.activities.matching.QRCodeReaderActivity;
import com.flatshare.threading.MainThreadImpl;

public class RoommateWaitingActivity extends AbstractActivity implements RoommateWaitingPresenter.View {

    private static final String TAG = "RoommateWaitingActivity";
    private RoommateWaitingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addWaitingImage();

        mPresenter = new RoommateWaitingPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        Bundle b = getIntent().getExtras();
        String apartmentId;
        if (b != null) {
            apartmentId = b.getString("apartmentId");
            mPresenter.listenToDB(apartmentId);
        }
    }

    private void addWaitingImage() {
        //TODO: add some waiting image
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_roommate_waiting;
    }

    @Override
    public void showError(String message) {
        //TODO:
    }

    @Override
    public void changeToMatchingActivity() {
        Log.d(TAG, "changeToMatchingActivity: ");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
