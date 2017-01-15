package com.flatshare.presentation.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.presentation.ui.activities.matchingoverview.MatchingOverviewActivity;
import com.flatshare.presentation.ui.activities.settings.ProfileTenantSettingsActivity;
import com.flatshare.presentation.ui.activities.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbstractActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.home_icon,
            R.drawable.settings_icon,
            R.drawable.profile_icon,
//            R.drawable.calendar_icon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
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
        tabThree.setText("ProfileTenantSettingsActivity");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.profile_icon, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("MatchingOverviewActivity");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bell, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SettingsActivity(), "SettingsActivity");
        adapter.addFragment(new ProfileTenantSettingsActivity(), "ProfileTenantSettingsActivity");
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
}
