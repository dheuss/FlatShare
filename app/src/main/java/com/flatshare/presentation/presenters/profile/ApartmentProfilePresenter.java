package com.flatshare.presentation.presenters.profile;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.VideoView;

import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.presentation.presenters.base.BasePresenter;
import com.flatshare.presentation.ui.BaseView;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by Arber on 08/12/2016.
 */

public interface ApartmentProfilePresenter extends BasePresenter {

    void sendProfile(ApartmentProfile apartmentProfile);

    void uploadImage(byte[] uri);

    void uploadVideo(VideoView videoView);

    void getNicknames();

    interface View extends BaseView {
        void changeToApartmentSettings();

        void updateAdapter(Map<String, String> idEmailsMap);

        void uploadSuccess();

        Context getContext();
    }

}
