package com.flatshare.domain.interactors;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface MediaInteractor extends Interactor {

    interface DownloadCallback {
        // TODO: CHANGE parameter type?
        void onDownloadSuccess(int dataType, byte[] data);
        void onError(String error);
    }

    interface UploadCallback {
        void onUploadSuccess();
        void onError(String error);
    }

}
