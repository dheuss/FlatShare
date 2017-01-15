package com.flatshare.presentation.presenters.settings;

import android.widget.ImageView;
import android.widget.VideoView;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.util.Map;

/**
 * Created by david on 15.01.2017.
 */

public interface ProfileApartmentSettingsPresenter extends BasePresenter {

    void changeApartmentProfile(ApartmentProfile apartmentProfile);

    void uploadImage(ImageView imageView);
    void uploadVideo(VideoView videoView);

    void getUserEmails();

    interface View extends BaseView {
        void changeToMatchingActivity();

        void updateAdapter(Map<String, String> idEmailsMap);
    }
}
