package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.flatshare.presentation.ui.activities.MainActivity;

/**
 * Created by Arber on 21/01/2017.
 */
public class FlatShareViewPager extends ViewPager {

    private static final String TAG = "ViewPager";
    private boolean enabled;
    private MainActivity callback;

    public FlatShareViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 2) { // if not matching activity
                    setPagingEnabled(true);
                } else {
                    setPagingEnabled(false);
                }

                if(position == 3) {// if matching overview
                    callback.resetBadgeCounter();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCallback(MainActivity callback) {
        this.callback = callback;
    }

    public MainActivity getCallback() {
        return callback;
    }
}