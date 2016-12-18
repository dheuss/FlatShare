package com.flatshare.presentation.presenters;

import android.widget.ImageView;
import android.widget.VideoView;

import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

/**
 * Created by Arber on 08/12/2016.
 */

public interface ApartmentProfilePresenter extends BasePresenter {

    void sendProfile(ApartmentUserProfile apartmentUserProfile);

    void uploadImage(ImageView imageView);
    void uploadVideo(VideoView videoView);

    interface View extends BaseView {
        void changeToApartmentSettings();
    }

}
