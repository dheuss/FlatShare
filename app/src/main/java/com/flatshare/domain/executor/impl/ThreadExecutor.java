package com.flatshare.domain.executor.impl;

import android.util.Log;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.interactors.base.AbstractInteractor;

/**
 *
 * Singleton to run each interactor on a background thread
 *
 * Created by Arber on 06/12/2016.
 *
 */
public class ThreadExecutor implements Executor {

    private static final String TAG = "ThreadExecutor";

    private static volatile ThreadExecutor sThreadExecutor;

    private static final int                     CORE_POOL_SIZE  = 3;
    private static final int                     MAX_POOL_SIZE   = 5;
    private static final int                     KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT       = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE      = new LinkedBlockingQueue<Runnable>();

    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadExecutor() {
        Log.d(TAG, "inside constructor!");
        long keepAlive = KEEP_ALIVE_TIME;
        mThreadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                keepAlive,
                TIME_UNIT,
                WORK_QUEUE);
    }

    @Override
    public void execute(final AbstractInteractor interactor) {
        Log.d(TAG, "inside execute(someInteractor)");
        mThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // run the main logic
                interactor.run();

                interactor.onFinished();
            }
        });
    }

    public static Executor getInstance() {
        if (sThreadExecutor == null) {
            sThreadExecutor = new ThreadExecutor();
        }

        return sThreadExecutor;
    }
}
