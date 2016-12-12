package com.flatshare.domain.executor;


import com.flatshare.domain.interactors.base.AbstractInteractor;

/**
 * This executor is responsible for running interactors on background threads.
 * <p>
 * Created by Arber on 06/12/2016.
 */
public interface Executor {

    /**
     * This method should call the interactor's run method and thus start the interactor.
     *
     * @param interactor The interactor to run.
     */
    void execute(final AbstractInteractor interactor);
}
