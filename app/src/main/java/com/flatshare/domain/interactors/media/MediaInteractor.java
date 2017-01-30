package com.flatshare.domain.interactors.media;

import android.graphics.Bitmap;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.domain.datatypes.db.profiles.UserProfile;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.interactors.base.Interactor;

import java.util.List;
import java.util.Map;

/**
 * Created by Arber on 10/12/2016.
 */

public interface MediaInteractor extends Interactor {

    interface UploadCallback {
        void onUploadSuccess();
        void onError(String error);
    }

    interface DownloadCallback {
        void onTenantDownloadSuccess(List<Pair<TenantProfile, Bitmap>> tenantsImageList);
        void onApartmentDownloadSuccess(List<Pair<ApartmentProfile, Bitmap>> apartmentImageList);
        void onDownloadTenantImageSuccess(Bitmap tenantImage);
        void onDownloadApartmentImageSucess(Bitmap apartmentImage);

        void onDownloadError(String error);
    }
}
