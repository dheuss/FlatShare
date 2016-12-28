package com.flatshare.domain.interactors;

import com.flatshare.domain.interactors.base.Interactor;

/**
 * Created by Arber on 10/12/2016.
 */

public interface MediaInteractor extends Interactor {

    interface ApartmentDownloadCallback {
        // TODO: CHANGE parameter type?
        void onDownloadSuccess(byte[] data);
        void onError(String error);
    }

    interface TenantDownloadCallback {
        // TODO: CHANGE parameter type?
        void onDownloadSuccess(boolean isImage, byte[] data);
        void onError(String error);
    }

    interface UploadCallback {
        void onUploadSuccess();
        void onError(String error);
    }

}
