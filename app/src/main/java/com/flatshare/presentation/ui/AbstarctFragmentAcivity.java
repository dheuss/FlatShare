package com.flatshare.presentation.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatshare.R;

import dmax.dialog.SpotsDialog;

/**
 * Created by david on 15.01.2017.
 */

public abstract class AbstarctFragmentAcivity extends Fragment {

    private AlertDialog progressDialog;
    protected SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sharedPref = getPreferences(Context.MODE_PRIVATE);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //progressDialog = new SpotsDialog(this, R.style.Custom);
    }

    public void showProgress(){
        progressDialog.show();
    }

    public void hideProgress(){
        progressDialog.hide();
    }

    @Override
    public void onDestroy() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

//    @Override
//    protected void onPause() {
//        try {
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onDestroy();
//    }

    protected void writeToSharedPreferences(int key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(key), value);
        editor.apply();
    }

    protected void writeToSharedPreferences(int key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(key), value);
        editor.apply();
    }

    protected void writeToSharedPreferences(int key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(key), value);
        editor.apply();
    }

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);
}
