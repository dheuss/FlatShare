package com.flatshare.presentation.presenters.settings;

import android.net.Uri;
import android.widget.VideoView;

import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by david on 28.12.2016.
 */

public interface ProfileTenantSettingsPresenter extends BasePresenter{

    void changeProfile(TenantProfile tenantProfile);

    void uploadVideo(VideoView videoView);

    void uploadImage(Uri uri);

    interface View extends BaseView {
        void changeToMatchingActivity();
        void uploadSucces();
    }

}
