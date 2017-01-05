package com.flatshare.presentation.ui.activities;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.facebook.FacebookSdk;
import com.flatshare.R;
import com.flatshare.presentation.presenters.LoginPresenter;
import com.flatshare.presentation.presenters.impl.LoginPresenterImpl;
import com.flatshare.threading.MainThreadImpl;

/**
 * Created by Arber on 16/12/2016.
 */
public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private LoginPresenter mPresenter;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_login);

        bindView();

        // create a presenter for this view
        mPresenter = new LoginPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );
    }

    private void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToProfileActivity() {
    }

    @Override
    public void changeToMatchingActivity() {
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragmentOneActivity(), "");
        adapter.addFragment(new LoginFragmentTwoActivity(), "");
        adapter.addFragment(new LoginFragmentThreeActivity(), "");
        adapter.addFragment(new LoginFragmentFourActivity(), "");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}