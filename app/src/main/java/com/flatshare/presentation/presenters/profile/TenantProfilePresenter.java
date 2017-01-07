package com.flatshare.presentation.presenters.profile;

import android.net.Uri;
import android.widget.VideoView;

import com.flatshare.domain.datatypes.db.profiles.TenantUserProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 08/12/2016.
 */

public interface TenantProfilePresenter extends BasePresenter {

    void sendProfile(TenantUserProfile tenantUserProfile);

    void uploadVideo(VideoView videoView);

    void uploadImage(Uri uri);

    interface View extends BaseView {
        void changeToTenantSettings();
        void uploadSuccess();
    }

}