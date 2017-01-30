package com.flatshare.presentation.ui.activities.auth.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flatshare.R;
import com.flatshare.presentation.presenters.auth.LoginPresenter;
/**
 * Created by david on 06/12/2016.
 */
public class LoginFragmentOneActivity extends Fragment { //implements LoginPresenter.View{

//    private Button login_1_Button;

    public LoginFragmentOneActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login_fragment_one, container, false);

        bind(view);

//        login_1_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), LoginFragmentFourActivity.class));
//                Fragment fragment = new Dashboard();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.contentFragment, fragment);
//                transaction.commit();
//            }
//        });

        return view;
    }

    public void bind(View view){
//        login_1_Button = (Button)view.findViewById(R.id.login_button_fragment_1);
    }

//    @Override
//    public void changeToProfileActivity() {
//
//    }
//
//    @Override
//    public void changeToMatchingActivity() {
//
//    }
//
//    @Override
//    public void showProgress() {
//
//    }
//
//    @Override
//    public void hideProgress() {
//
//    }
//
//    @Override
//    public void showError(String message) {
//
//    }
}
