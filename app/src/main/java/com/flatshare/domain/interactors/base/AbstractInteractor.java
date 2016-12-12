package com.flatshare.domain.interactors.base;

import android.util.Log;

import com.flatshare.domain.executor.Executor;
import com.flatshare.domain.executor.MainThread;

/**
 * This abstract class implements some common methods for all interactors. Cancelling an interactor, check if its running
 * and finishing an interactor.
 * Field methods are declared volatile as we might use these methods from different threads (mainly from UI).
 *
 * For example, when an activity is getting destroyed then we should probably cancel an interactor
 * but the request will come from the UI thread unless the request was specifically assigned to a background thread.
 *
 * Created by Arber on 06/12/2016.
 */
public abstract class AbstractInteractor implements Interactor {

    private static final String TAG = "AbstractInteractor";

    protected Executor mThreadExecutor;
    protected MainThread mMainThread;

    protected volatile boolean mIsCanceled;
    protected volatile boolean mIsRunning;

    public AbstractInteractor(Executor threadExecutor, MainThread mainThread) {

        Log.d(TAG, "inside constructor");

        mThreadExecutor = threadExecutor;
        mMainThread = mainThread;
    }

    /**
     * This method contains the actual business logic of the interactor. It SHOULD NOT BE USED DIRECTLY but, instead
     * the execute() method of an interactor should be called to make sure the operation is done on a background thread.
     * This method should only be called directly while doing unit/integration tests. That is the only reason it is declared
     * public as to help with easier testing.
     */
    public abstract void run();

    public void cancel() {

        Log.d(TAG, "inside cancel()");

        mIsCanceled = true;
        mIsRunning = false;
    }

    public boolean isRunning() {

        Log.d(TAG, "inside isRunning");

        return mIsRunning;
    }

    public void onFinished() {
        Log.d(TAG, "inside onFinished()");

        mIsRunning = false;
        mIsCanceled = false;
    }

    public void execute() {

        Log.d(TAG, "inside execute()");

        // mark this interactor as running
        this.mIsRunning = true;

        // start running this interactor in a background thread
        mThreadExecutor.execute(this);
    }

}
