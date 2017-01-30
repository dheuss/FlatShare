package com.flatshare;

import android.app.Application;
import android.util.Log;

import timber.log.Timber;
/**
 * Created by Arber on 06/12/2016.
 */
public class AndroidApplication extends Application {

    private static final String TAG = "AndroidApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "inside onCreate(), Timber.plant(new DebugTree());");
        Timber.plant(new Timber.DebugTree());
    }
}
