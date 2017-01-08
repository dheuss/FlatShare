package com.flatshare.presentation.ui.activities.matching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flatshare.R;
import com.flatshare.presentation.presenters.matching.ShowDetailProfilApartmentPresenter;
import com.flatshare.presentation.presenters.matching.impl.ShowDetailProfilApartmentPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class ShowDetailProfilApartmentActivity extends AbstractActivity implements ShowDetailProfilApartmentPresenter.View {

    private static final String TAG = "ShowDetailProfilApartmentActivity";

    private ShowDetailProfilApartmentPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new ShowDetailProfilApartmentPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_show_detail_profil_apartment;
    }

    public void bindView(){

    }
}
