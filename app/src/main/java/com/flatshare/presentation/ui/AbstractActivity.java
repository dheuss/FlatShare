package com.flatshare.presentation.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flatshare.R;

import dmax.dialog.SpotsDialog;

/**
 * Created by Arber on 26/12/2016.
 */
public abstract class AbstractActivity extends AppCompatActivity {


    private AlertDialog progressDialog;
    protected SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        progressDialog = new SpotsDialog(this,R.style.Custom);
    }

    protected  abstract int getLayoutResourceId();


    public void showProgress(){
        progressDialog.show();
    }

    public void hideProgress(){
        progressDialog.hide();
    }

    @Override
    protected void onDestroy() {
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

    private void writeToSharedPreferences(int key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(key), value);
        editor.apply();
    }

    private void writeToSharedPreferences(int key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(key), value);
        editor.apply();
    }

    private void writeToSharedPreferences(int key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(key), value);
        editor.apply();
    }
}
