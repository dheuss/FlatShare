package com.flatshare.domain.executor;

/**
 * This interface defines a method that enables interactors run operations on the main UI thread.
 * It is normally called after finishing lengthy calculations in order to use the presenter (Callback)
 * to update the GUI
 *
 * Created by Arber on 06/12/2016.
 */
public interface MainThread {

    /**
     * Make runnable operation run in the main thread.
     *
     * @param runnable The runnable to run.
     */
    void post(final Runnable runnable);
}
