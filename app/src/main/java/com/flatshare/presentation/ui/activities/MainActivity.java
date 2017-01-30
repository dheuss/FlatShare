package com.flatshare.presentation.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.enums.ProfileType;
import com.flatshare.domain.state.UserState;
import com.flatshare.presentation.presenters.matching.MatchesPresenter;
import com.flatshare.presentation.presenters.matching.impl.MatchesPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.FlatShareViewPager;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.MatchingOverviewActivity;
import com.flatshare.presentation.ui.activities.profile.RoommateProfileActivity;
import com.flatshare.presentation.ui.activities.settings.ProfileApartmentSettingsActivity;
import com.flatshare.presentation.ui.activities.settings.ProfileTenantSettingsActivity;
import com.flatshare.presentation.ui.activities.settings.RoommateProfileSettingsActivity;
import com.flatshare.presentation.ui.activities.settings.SettingsActivity;
import com.flatshare.threading.MainThreadImpl;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by david on 06/12/2016.
 */
public class MainActivity extends AbstractActivity implements MatchesPresenter.View {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private FlatShareViewPager viewPager;
    private BadgeView badge;
    private int[] tabIcons = {
            R.drawable.home_icon,
            R.drawable.settings_icon,
            R.drawable.profile_icon,
            R.drawable.notifications_icon
    };

    private UserState userState;
    private int classificationId;
    private MatchesPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userState = UserState.getInstance();

        classificationId = userState.getPrimaryUserProfile().getClassificationId();

        Log.d(TAG, "onCreate: " + classificationId);

        bindView();

        mPresenter = new MatchesPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );
        mPresenter.setPotentialMatchesListener();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setCurrentItem(2);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void bindView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (FlatShareViewPager) findViewById(R.id.viewpager);
        viewPager.setCallback(this);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("MatchingActivity");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_icon, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("SettingsActivity");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.settings_icon, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("ProfileApartmentSettingsActivity");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.profile_icon, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("MatchingOverviewActivity");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_icon, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        badge = new BadgeView(this, tabFour);
        badge.hide();
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new SettingsActivity(), "SettingsActivity");

        if (classificationId == ProfileType.TENANT.getValue()){
            adapter.addFragment(new ProfileTenantSettingsActivity(), "ProfileTenantSettingsActivity");

        } else if (classificationId == ProfileType.APARTMENT.getValue()){
            Log.d(TAG, "setupViewPager: APPPPART");
            adapter.addFragment(new ProfileApartmentSettingsActivity(), "ProfileApartmentSettingsActivity");

        } else if (classificationId == ProfileType.ROOMMATE.getValue()) {
            Log.d(TAG, "setupViewPager: ROMMMM");
            adapter.addFragment(new RoommateProfileSettingsActivity(), "RoommateProfileSettingsActivity");

        } else {

        }
        adapter.addFragment(new MatchingActivity(), "MatchingActivity");
        adapter.addFragment(new MatchingOverviewActivity(), "MatchingOverviewActivity");
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
            return null;
        }

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Sorry, you can't close this app!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void incrementBadgeCounter(){
        String text = badge.getText().toString();
        if(text==null || text.trim().equals("")){
            badge.setText("1");
        } else {
            badge.setText((Integer.parseInt(text) + 1) + "");
        }
        badge.show();
    }

    public void resetBadgeCounter() {
        badge.setText("0");
        badge.hide();
    }
}
