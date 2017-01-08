package com.flatshare.presentation.ui.activities.matching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flatshare.R;
import com.flatshare.presentation.presenters.matching.ShowDetailProfilTenantPresenter;
import com.flatshare.presentation.presenters.matching.impl.ShowDetailProfilTenantPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.threading.MainThreadImpl;

public class ShowDetailProfilTenantActivity extends AbstractActivity implements ShowDetailProfilTenantPresenter.View {

    private static final String TAG = "ShowDetailProfilTenantActivity";

    private ShowDetailProfilTenantPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new ShowDetailProfilTenantPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_show_detail_profil_tenant;
    }

    public void bindView(){

    }
}
