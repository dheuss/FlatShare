package com.flatshare.presentation.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flatshare.R;

import dmax.dialog.SpotsDialog;

public abstract class AbstractActivity extends AppCompatActivity {


    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
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
}
